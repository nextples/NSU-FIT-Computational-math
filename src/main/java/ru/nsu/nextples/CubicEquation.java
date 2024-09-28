package ru.nsu.nextples;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class CubicEquation {
    private final double a;
    private final double b;
    private final double c;
    private final double delta;
    private final double epsilon;

    private final ArrayList<Double> roots;

    private double extreme1;
    private double extreme2;

    public enum Direction{
        LEFT, RIGHT
    }

    public CubicEquation(double a, double b, double c, double delta, double epsilon) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.delta = delta;
        this.epsilon = epsilon;
        roots = new ArrayList<>();
    }

    public ArrayList<Double> getRoots() {
        return roots;
    }

    public int solve() {
        int extremesNumb = findExtremes();

        if (extremesNumb == 0) {
            double border1 = 0;
            double f0 = f(border1);

            if (abs(f0) < epsilon) {
                roots.add(border1);
                return 1;
            }
            else if (abs(f0) < -epsilon) {
                double border2 = findBorder(0, Direction.RIGHT);
                roots.add(dichotomyMethod(border1, border2));
                return 1;
            }
            else {
                double border2 = findBorder(0, Direction.LEFT);
                roots.add(dichotomyMethod(border1, border2));
                return 1;
            }
        }

        else {
            double f1 = f(extreme1);
            double f2 = f(extreme2);

            if (f1 >= epsilon && f2 >= epsilon) {
                double border1 = findBorder(extreme1, Direction.LEFT);
                roots.add(dichotomyMethod(border1, extreme1));
                return 1;
            }

            else if (f1 <= -epsilon && f2 <= -epsilon) {
                double border2 = findBorder(extreme2, Direction.RIGHT);
                roots.add(dichotomyMethod(extreme1, border2));
                return 1;
            }

            else if (abs(f1) < epsilon && abs(f2) < epsilon) {
                roots.add((extreme1 + extreme2) / 2);
                return 1;
            }

            else if (f1 >= epsilon && abs(f2) < epsilon) {
                double border1 = findBorder(extreme1, Direction.LEFT);
                roots.add(dichotomyMethod(border1, extreme1));
                roots.add(extreme2);
                return 2;
            }

            else if (abs(f1) < epsilon && f2 <= -epsilon) {
                double border2 = findBorder(extreme2, Direction.RIGHT);
                roots.add(extreme1);
                roots.add(dichotomyMethod(extreme2, border2));
                return 2;
            }

            else if (f1 >= epsilon && f2 <= -epsilon) {
                double border1 = findBorder(extreme1, Direction.LEFT);
                double border2 = findBorder(extreme2, Direction.RIGHT);
                roots.add(dichotomyMethod(border1, extreme1));
                roots.add(dichotomyMethod(extreme1, extreme2));
                roots.add(dichotomyMethod(extreme2, border2));
                return 3;
            }
        }
        return -1;
    }

    public double f(double x) {
        return x * x * x + a * x * x + b * x + c;
    }

    public int findExtremes() {
        double discriminant = a * a - 3 * b;
        if ((-discriminant / 3) >= epsilon) {
            return 0;
        }
        else {
            extreme1 = (-a - sqrt(discriminant)) / 3;
            extreme2 = (-a + sqrt(discriminant)) / 3;
            return 2;
        }
    }

    public double findBorder(double border1, Direction direction) {
        double val1 = f(border1);
        double step;
        if (direction == Direction.LEFT) {
            step = -delta;
        }
        else {
            step = delta;
        }
        double border2 = border1 + step;

        while (val1 * f(border2) > 0) {
            border2 += step;
        }

        return border2;
    }

    public double dichotomyMethod(double border1, double border2) {
        double f1 = f(border1);
        double f2 = f(border2);

        if (abs(f1) < epsilon) {
            return border1;
        }

        if (abs(f2) < epsilon) {
            return border2;
        }

        while (true) {
            double mid = (border1 + border2) / 2;
            double fMid = f(mid);

            if (abs(fMid) < epsilon) {
                return mid;
            }
            if (f1 * fMid < 0) {
                border2 = mid;
            }
            else {
                border1 = mid;
                f1 = fMid;
            }
        }
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getDelta() {
        return delta;
    }

    public double getEpsilon() {
        return epsilon;
    }
}