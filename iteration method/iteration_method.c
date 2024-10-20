#include <stdio.h>
#include <math.h>

int x_0 = 2;

double function(double x) {
    return pow(x, 2) - 5.0;
}

double func_diff(double x) {
    return 2 * x;
}

void newtons_method(FILE *file) {
    fprintf(file, "\t\tМетод Ньютона\n");
    double x = x_0;
    for (int i = 0; i < 5; i++) {
        fprintf(file, "i = \t\t\t%d\n", i);
        fprintf(file, "x_i = \t\t\t%.16lf\n", x);
        fprintf(file, "|x_i - sqrt(5)| = \t%.16lf\n", fabs(x - sqrt(5)));
        double tg = func_diff(x);
        x = x - (function(x) / tg);
        fprintf(file, "\n");
    }
    // fprintf(file, "Метод Ньютона: %lf\n", fabs(x - sqrt(5)));
}

void one_tangent_method(FILE *file) {
    fprintf(file, "\n\n\t\tМетод касательных\n");
    double x = x_0;
    double tg = func_diff(x);
    for (int i = 0; i < 5; i++) {
        fprintf(file, "i = \t\t\t%d\n", i);
        fprintf(file, "x_i = \t\t\t%.16lf\n", x);
        fprintf(file, "|x_i - sqrt(5)| = \t%.16lf\n", fabs(x - sqrt(5)));
        fprintf(file, "\n");
        x = x - (function(x) / tg);
    }
    // fprintf(file, "Метод касательных: %lf\n", fabs(x - sqrt(5)));
}

void secant_method(FILE *file) {
    fprintf(file, "\n\n\t\tМетод секущих\n");
    double x1 = x_0;
    double x2 = x_0 - (function(x_0) / func_diff(x_0));
    for (int i = 0; i < 5; i++) {
        fprintf(file, "i = \t\t\t%d\n", i);
        fprintf(file, "x_i = \t\t\t%.16lf\n", x1);
        fprintf(file, "|x_i - sqrt(5)| = \t%.16lf\n", fabs(x1 - sqrt(5)));
        fprintf(file, "\n");

        double tg = (function(x1) - function(x2)) / (x1 - x2);
        double tmp_x = x2 - (function(x2) / tg);
        x1 = x2;
        x2 = tmp_x;
    }
    // fprintf(file, "Метод секущих: %lf\n", fabs(x2 - sqrt(5)));
}

int main() {
    FILE *file = fopen("results.txt", "w");
    if (file == NULL) {
        printf("Ошибка открытия файла.\n");
    }

    fprintf(file, "x_0 = 2\n\n");
    newtons_method(file);
    one_tangent_method(file);
    secant_method(file);

    fclose(file);

    return 0;
}