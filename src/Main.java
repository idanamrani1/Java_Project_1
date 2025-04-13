//students name: idan amrani and shaked hakim

import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args){
        OperatingSystem op1 = new OperatingSystem();
        op1.printWelcome();
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            String choice = input.nextLine();
            switch (choice) {
                case "0":
                    isRunning = false;
                    break;
                case "1":
                    op1.insertLectureDetails();
                    break;
                case "2":
                    op1.insertBoard();
                    break;
                case "3":
                    op1.addLectureToBoard();
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    break;
                case "10":
                    op1.printAllBoards();
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public static void printMenu(){
        System.out.println("0 - Exit");
        System.out.println("1 - Add Lecture");
        System.out.println("2 - Add board to College");
        System.out.println("3 - Add to the board ");
        System.out.println("4 - Update board manager");
        System.out.println("5 - Remove from board");
        System.out.println("6 - Add study department");
        System.out.println("7 - Lecture avg salary");
        System.out.println("8 - Avg salary per department");
        System.out.println("9 - Show details about Lectures");
        System.out.println("10 - Show details about boards");
    }

}


