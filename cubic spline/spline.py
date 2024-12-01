import numpy as np
import matplotlib.pyplot as plt

class Sweep:
    def __init__(self, a, b, n):
        self.a = a
        self.b = b
        self.n = n
        self.h = (b - a) / (n - 1)
        self.g = np.zeros(n + 1)
        self.alpha = np.zeros(n + 1)
        self.beta = np.zeros(n + 1)
        self.a_spline = np.zeros(n + 1)
        self.b_spline = np.zeros(n + 1)
        self.c_spline = np.zeros(n + 1)
        self.d_spline = np.zeros(n + 1)
        self.c_i = 4 * self.h
        self.a_i = -self.h
        self.b_i = -self.h

    def func(self, i):
        x = self.a + (i - 1) * self.h
        return abs(x)

    def init_g(self):
        for i in range(2, self.n):
            self.g[i] = 6 * (((self.func(i + 1) - self.func(i)) / self.h) - ((self.func(i) - self.func(i - 1)) / self.h))

    def first_step(self):
        self.alpha[2] = self.b_i / self.c_i
        self.beta[2] = self.g[2] / self.c_i

    def second_step(self):
        for i in range(3, self.n - 1):
            self.alpha[i] = self.b_i / (self.c_i - self.a_i * self.alpha[i - 1])
            self.beta[i] = (self.g[i] + self.a_i * self.beta[i - 1]) / (self.c_i - self.a_i * self.alpha[i - 1])

    def third_step(self):
        self.c_spline[self.n - 1] = (self.g[self.n - 1] + self.a_i * self.beta[self.n - 2]) / (self.c_i - self.a_i * self.alpha[self.n - 2])

    def fourth_step(self):
        for i in range(self.n - 2, 1, -1):
            self.c_spline[i] = self.alpha[i] * self.c_spline[i + 1] + self.beta[i]

    def evaluate_d_spline(self):
        for i in range(1, self.n):
            self.d_spline[i] = (self.c_spline[i + 1] - self.c_spline[i]) / self.h

    def evaluate_b_spline(self):
        for i in range(1, self.n):
            self.b_spline[i] = (self.func(i + 1) - self.func(i) - self.h ** 3 * self.d_spline[i] / 6 - self.h ** 2 * self.c_spline[i] / 2) / self.h

    def evaluate_a_spline(self):
        for i in range(1, self.n):
            self.a_spline[i] = self.func(i)

    def calculate(self):
        self.init_g()
        self.first_step()
        self.second_step()
        self.third_step()
        self.fourth_step()
        self.evaluate_d_spline()
        self.evaluate_b_spline()
        self.evaluate_a_spline()

# Создание объекта Sweep и расчет сплайнов
data = Sweep(a=-1, b=1, n=11)  # 101 узлов => 100 сплайнов
data.calculate()

# Построение графиков
segments = [(data.a + i * data.h, data.a + (i + 1) * data.h) for i in range(data.n - 1)]

def cubic_spline(x_i, x, a, b, c, d):
    return a + b * (x - x_i) + c * (x - x_i)**2 / 2 + d * (x - x_i)**3 / 6

plt.figure(figsize=(20, 10))  # Увеличение масштаба окна

# Узловые точки
x_nodes = [data.a + i * data.h for i in range(data.n)]
y_nodes_real = [abs(x) for x in x_nodes]
y_nodes_spline = [data.a_spline[i] for i in range(1, data.n)]

# Отображение сплайнов (все одним цветом)
for i, (start, end) in enumerate(segments):
    x_vals = np.linspace(start, end, 100)
    y_vals = cubic_spline(start, x_vals, data.a_spline[i + 1], data.b_spline[i + 1], data.c_spline[i + 1], data.d_spline[i + 1])
    if i == 0:
        plt.plot(x_vals, y_vals, color='blue', label='Сплайны')
    else:
        plt.plot(x_vals, y_vals, color='blue')

# Отображение реального графика |x|
x_abs = np.linspace(data.a, data.b, 1000)  # Больше точек для лучшего разрешения
y_abs = np.abs(x_abs)
plt.plot(x_abs, y_abs, label='|x|', color='r', linestyle='--')

# Добавление точек пересечения
plt.scatter(x_nodes, y_nodes_real, color='red', label='Точки |x|', zorder=5)
plt.scatter(x_nodes[:-1], y_nodes_spline, color='blue', label='Точки сплайнов', zorder=5)

plt.title('Кубические сплайны и график функции |x|')
plt.xlabel('x')
plt.ylabel('y')
plt.axhline(0, color='black', linewidth=0.5, ls='--')
plt.axvline(0, color='black', linewidth=0.5, ls='--')
plt.grid()
plt.legend()
plt.xlim(-1.2, 1.2)  # Более крупный диапазон для всех 100 сплайнов
plt.ylim(-0.2, 1.2)

plt.show()
