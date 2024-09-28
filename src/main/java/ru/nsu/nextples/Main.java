package ru.nsu.nextples;

import org.apache.commons.cli.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("h", "help", false, "print help");

        Option coefA = new Option("a", true, "coefficient A");
        coefA.setRequired(true);
        coefA.setArgs(1);
        options.addOption(coefA);

        Option coefB = new Option("b", true, "coefficient B");
        coefB.setRequired(true);
        coefB.setArgs(1);
        options.addOption(coefB);

        Option coefC = new Option("c", true, "coefficient C");
        coefC.setRequired(true);
        coefC.setRequired(true);
        options.addOption(coefC);

        Option valDelta = new Option("d", true, "value Delta");
        valDelta.setRequired(true);
        valDelta.setArgs(1);
        options.addOption(valDelta);

        Option valEpsilon = new Option("e", true, "value Epsilon");
        valEpsilon.setRequired(true);
        valEpsilon.setArgs(1);
        options.addOption(valEpsilon);

        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Invalid command line options were passed");
            printHelp(options);
            System.exit(1);
        }

        double a = 0, b = 0, c = 0, delta = 0, epsilon = 0;
        if (cmd.hasOption("a") && cmd.hasOption("b") && cmd.hasOption("c") && cmd.hasOption("d") && cmd.hasOption("e")) {
            a = Double.parseDouble(cmd.getOptionValue("a"));
            b = Double.parseDouble(cmd.getOptionValue("b"));
            c = Double.parseDouble(cmd.getOptionValue("c"));
            delta = Double.parseDouble(cmd.getOptionValue("d"));
            epsilon = Double.parseDouble(cmd.getOptionValue("e"));
        }
        else {
            printHelp(options);
            System.exit(1);
        }

        CubicEquation equation = new CubicEquation(a, b, c, delta, epsilon);
        equation.solve();
        writeAnswer("./output/output.txt", equation);
        System.out.println("Roots: " + equation.getRoots());
    }

    public static void writeAnswer(String filePath, CubicEquation equation) {
        try (FileWriter writer = new FileWriter(filePath, true)) {  // true - для добавления данных в конец файла
            double a = equation.getA();
            double b = equation.getB();
            double c = equation.getC();
            double d = equation.getDelta();
            double epsilon = equation.getEpsilon();

            writer.write(String.format("Input:\na=%.5f b=%.5f c=%.5f delta=%.5f epsilon=%.5f\n", a, b, c, d, epsilon));

            writer.write("Output:\nNumOfRoots=" + equation.getRoots().size() + " ");

            writer.write("Roots=[");
            for (int i = 0; i < equation.getRoots().size(); i++) {
                writer.write(String.format("%.5f", equation.getRoots().get(i)));
                if (i < equation.getRoots().size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("] ");

            writer.write("AbsFuncValInRoots=[");
            for (int i = 0; i < equation.getRoots().size(); i++) {
                double absVal = Math.abs(equation.f(equation.getRoots().get(i)));
                writer.write(String.format("%.10f", absVal));
                if (i < equation.getRoots().size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("]\n\n");

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Usage: java -jar <path/to/file>.jar", options);

    }
}
