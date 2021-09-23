/*
 * Program inspirowany tutorialem na YT z kanału "Coding with John" - https://www.youtube.com/watch?v=dzcsCZXmYzo
 * Rozszerzony o system metryczny.
 *
 *
 * uwagi i notatki:
 * - litery zamiast cyfr w wadze i wzroście - zabezpieczyć
 * - pętla by wpisać jeszcze raz dobrą wartość
 *
 */
package bmicalculator;

import java.text.DecimalFormat;
import java.util.Scanner;

public class BMICalculator {

    private static final double MAX_HEIGHT = 10;
    private static final double MIN_HEIGHT = 0;
    private static final double MAX_WEIGHT = 1350;
    private static final double MIN_WEIGHT = 0;
    private static final DecimalFormat DF = new DecimalFormat("0.0");
    private static double healthyW_min = 0;
    private static double healthyW_max = 0;
    private static double weight_d = 0;
    private static final double BMI_MIN = 18.5;
    private static final double BMI_MAX = 25;
    private static boolean impUnits = true;

    public static void main(String[] args) {
        boolean wybor = false;
        do {
            System.out.println("Please, choose measurement system: [i]mperial or [m]etric.");
            Scanner sc = new Scanner(System.in);
            String unitSys = sc.nextLine();

            if (unitSys.equalsIgnoreCase("i")) { //IMPERIAL
                impUnits = true;

                System.out.println("You chose imperaial units.");
                double weight = setWeightImp(sc);

                System.out.println("Please, enter your height in feets [ex. 5,9].");
                double height = sc.nextDouble();
                System.out.println("Your height is: " + setImperialHeight(height));

                double height_d = convertToInch(height);
                resolveBMIforAdultsImp(weight, height_d);

            } else if (unitSys.equalsIgnoreCase("m")) { // METRIC
                impUnits = false;
                System.out.println("You chose metric units.");
                System.out.println("Please, enter your weight [ex. 86].");
                String weight = sc.nextLine();
                System.out.println("Your weight is: " + weight + " kg.");

                System.out.println("Please, enter your height in meters [ex. 1,76].");
                double height = sc.nextDouble();
                System.out.println("Your height is: " + height + " m.");

                weight_d = Double.parseDouble(weight);

                resolveBMIforAdultsMet(weight_d, height);

            } else
                wybor = true;
        } while (wybor);
    }

    private static double setWeightImp(Scanner sc) {
        //double weight = 0;
        System.out.println("Please, enter your weight [ex. 190].");
        double weight = sc.nextDouble();
            System.out.println("Your weight is: " + weight + " lbs.");

        return weight;

    }

    private static void resolveBMIforAdultsImp(double weight, double height_d) {
        double rBMI = (weight / (height_d * height_d)) * 703;

        healthyW_min = ((height_d * height_d) * BMI_MIN) / 703;
        healthyW_max = ((height_d * height_d) * BMI_MAX) / 703;

        resultBMI(rBMI, weight);

        finalResult();
    }

    private static void resolveBMIforAdultsMet(double weight, double height) {
        double rBMI = weight / (height * height);

        healthyW_min = ((height * height) * BMI_MIN);
        healthyW_max = ((height * height) * BMI_MAX);

        resultBMI(rBMI, weight);

        finalResult();
    }

    private static void finalResult() {
        System.out.println("Healthy weight for your height is in range between " + DF.format(healthyW_min) + " kg/m^2 - " + DF.format(healthyW_max) + " kg/m^2.");
        System.out.println();
        System.out.println("Remember that BMI is just a tool limited for height and weight. \nIt does not take into account muscle mass, water amount and other parts of body composition.");
    }

    private static String resultBMI(double rBMI, double weight) {
        System.out.println();
        System.out.print("Your BMI is: " + DF.format(rBMI) + " kg/m^2, which means: ");
        double gain = healthyW_min - weight;
        double lose = weight - healthyW_max;
        double stay = 0;
        String result = "";

        if (rBMI < 16) {
            System.out.println("Severe Thinness");
            result = resultUnit(1, gain, lose);

        }
        if (16 <= rBMI && rBMI < 17) {
            System.out.println("Moderate Thinness");
            result = resultUnit(1, gain, lose);
        }
        if (17 <= rBMI && rBMI < 18.5) {
            System.out.println("Mild Thinness");
            result = resultUnit(1, gain, lose);
        }
        if (18.5 <= rBMI && rBMI < 25) {
            System.out.println("Normal");
            result = resultUnit(0, gain, lose);
        }
        if (25 <= rBMI && rBMI < 30) {
            System.out.println("Overweight");
            result = resultUnit(-1, gain, lose);
        }
        if (30 <= rBMI && rBMI < 35) {
            System.out.println("Obese Class I");
            result = resultUnit(-1, gain, lose);
        }
        if (35 <= rBMI && rBMI < 40) {
            System.out.println("Obese Class II");
            result = resultUnit(-1, gain, lose);
        }
        if (40 <= rBMI) {
            System.out.println("Obess Class III");
            result = resultUnit(-1, gain, lose);
        }
        return result;
    }

    private static String resultUnit(double stateDouble, double gain, double lose) {
        String unit = "";
        if (stateDouble == 0)
            System.out.println("Your BMI is in healthy range 18.5 kg/m2 - 25 kg/m2. Keep in that way :)");
        else {
            System.out.print("To healthy BMI range 18.5 kg/m2 - 25 kg/m2 you have to ");
            if (stateDouble == 1) {
                System.out.print("gain " + DF.format(gain));
            } else if (stateDouble == -1) {
                System.out.print("lose " + DF.format(lose));
            }
            unit = (impUnits == true) ? " lbs." : " kg.";
            System.out.println(unit);
        }
        return unit;
    }

    private static String setImperialHeight(double height) {
        double inp_d = height;

        if (inp_d >= MAX_HEIGHT || inp_d <= MIN_HEIGHT) {
            System.out.println("Did you enter correct height?");
        }
        String inp_s = Double.toString(inp_d);
        int coma = inp_s.indexOf('.');
        int r;
        String postComa = inp_s.substring(coma+1);
        if (Integer.parseInt(postComa) >= 12) {
            r = Integer.parseInt(postComa) / 12;
            inp_d += r;

            postComa = Integer.toString(Integer.parseInt(postComa) - r*12);
        }
        String preComa = Integer.toString((int)inp_d);

        return preComa + "\' " + inchScope(postComa);
    }

    public static String inchScope(String n) {
        switch(n) {
            case "0":
                return "0\"";
            case "1":
                return "1\"";
            case "2":
                return "2\"";
            case "3":
                return "3\"";
            case "4":
                return "4\"";
            case "5":
                return "5\"";
            case "6":
                return "6\"";
            case "7":
                return "7\"";
            case "8":
                return "8\"";
            case "9":
                return "9\"";
            case "10":
                return "10\"";
            case "11":
                return "11\"";
            case "12":
                return "0\""; //setHeight() przelicza to na stopy
        }
        return "Enter correct height"; //lub przliczamy na stopy
    }

    public static double convertToInch(double height) {
        double inp_d = height;

        String inp_s = Double.toString(inp_d);
        int coma = inp_s.indexOf('.');

        String postComa = inp_s.substring(coma+1); // po przecinku
        if (postComa.equals("12"))
            inp_d += 1;
        String preComa = Integer.toString((int)inp_d * 12);

        Double postComa_d = Double.parseDouble(postComa);
        Double preComa_d = Double.parseDouble(preComa);

        return postComa_d + preComa_d;
    }
}

