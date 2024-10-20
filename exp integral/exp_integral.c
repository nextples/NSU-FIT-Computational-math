#include <stdio.h>
#include <math.h>

double function(double x) {
    return exp(x);
}

double integral() {
    return exp(1) - 1;
}

double trapezoidal_rule(int n) {
    double a = 0.0, b = 1.0, s = 0.0;
    double h = (b - a) / n;

    for (int i = 0; i < n; i++) {
        s += (function(i * h) + function((i + 1) * h)) * h / 2;
    }
    return s;
}

double log_2(double I, double S_h1, double S_h2) {
    return log2(fabs(I - S_h1) / fabs(I - S_h2));
}

void print_result(FILE* file, int n1, int n2) {
    double S_h1 = trapezoidal_rule(n1);
    double S_h2 = trapezoidal_rule(n2);
    double k = log_2(integral(), S_h1, S_h2);


    fprintf(file, "Точное значение I: %lf\n\n", integral());
    fprintf(file, "Приближенное значение S_h при n = %d: \t\t%lf\n", n1, S_h1);
    fprintf(file, "Приближенное значение S_(h/2) при n = %d: \t%lf\n", n2, S_h2);
    fprintf(file, "log_2(|I- Sh|/|I-Sh|): \t%lf\n", k);
    fprintf(file, "\n");
}

int main() {
    FILE *file = fopen("result.txt", "w");
    if (file == NULL) {
        printf("Can't open file.\n");
        return -1;
    }

    print_result(file, 10, 20);
    print_result(file, 100, 200);

    fclose(file);

    return 0;
}
