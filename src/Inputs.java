import java.util.Scanner;

public class Inputs {
    private static Scanner scan = new Scanner(System.in);
    private static int getIntFromUser(String type) {
        System.out.print("Enter " + type + ": ");
        int val = scan.nextInt();
        scan.nextLine();
        return val;
    }

    public static String getStrFromUser(String type) {
        System.out.print("Enter " + type + ": ");
        return scan.nextLine();
    }

    public static boolean getBoolFromUser(String type) {
        if (scan.hasNextLine()) {
            scan.nextLine();
        }
        System.out.print(type + ": ");
        return scan.nextLine().equalsIgnoreCase("yes");
    }

    public static double getDoubleFromUser(String type) {
        System.out.print("Enter " + type + ": ");
        return scan.nextDouble();
    }
}
