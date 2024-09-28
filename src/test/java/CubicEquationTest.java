import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.nsu.nextples.CubicEquation;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CubicEquationSolverTest {

    /**
     * Функция для проверки результата кубического уравнения f(x) = x^3 + ax^2 + bx + c
     */
    private boolean isRootCorrect(double a, double b, double c, double root, double epsilon) {
        double result = Math.pow(root, 3) + a * Math.pow(root, 2) + b * root + c;
        return Math.abs(result) < epsilon; // результат должен быть близок к нулю
    }

    /**
     * Функция для тестирования решения кубического уравнения
     */
    private void testEquation(double a, double b, double c, double delta, double epsilon) {
        CubicEquation testEquation = new CubicEquation(a, b, c, delta, epsilon);
        testEquation.solve();

        for (double root : testEquation.getRoots()) {
            assertTrue(isRootCorrect(a, b, c, root, epsilon),
                    "Корень " + root + " не удовлетворяет уравнению с коэффициентами a="
                            + a + ", b=" + b + ", c=" + c);
        }
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation1() {
        testEquation(103.27, 326.901, -9.9, 7, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation2() {
        testEquation(10664.99, -1076606.65, 10765, 9, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation3() {
        testEquation(-103.33, 333.099, -9.9, 7, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation4() {
        testEquation(10010, 100025, 250000, 7.77, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation5() {
        testEquation(3319, -46613, 163317, 7.77, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation6() {
        testEquation(-10010, 100025, -250000, 1.111, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation7() {
        testEquation(-99997.78, -221998.7679, -123210, 101, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation8() {
        testEquation(-111000, 0, 0, 101, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation9() {
        testEquation(17.6, 77.44, 0, 0.23, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation10() {
        testEquation(-17.6, 77.44, 0, 0.23, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation11() {
        testEquation(29.7, 294.03, 970.299, 200, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation12() {
        testEquation(-21.3, 151.23, -357.911, 200, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation13() {
        testEquation(0, 12345, 0, 200, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation14() {
        testEquation(1.12, 5, 5.6, 1000, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation15() {
        testEquation(0, 0, 0, 1000, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation16() {
        testEquation(-0.000001, 1, -0.000001, 1000, 1e-5);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation17() {
        testEquation(-0.000001, 1, -0.000001, 1e-6, 1e-4);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation18() {
        testEquation(-0.000001, 1, -0.000001, 1000, 0.1);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation19() {
        testEquation(-0.000001, 1, -0.000001, 1000, 1);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation20() {
        testEquation(0, -2, 0, 3, 1e-8);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation21() {
        testEquation(0, 2, 0, 3, 1e-4);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation22() {
        testEquation(0, -2, 0, 3, 0.1);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testCubicEquation23() {
        testEquation(0, -2, 0, 3, 1);
    }
}
