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
                    op1.updateMan();
                    break;
                case "5":
                    op1.removeFromBoard();
                    break;
                case "6":
                    op1.addDepartment();
                    break;
                case "7":
                    System.out.println("The avg is: " +op1.getSalaryForAll(null));
                    break;
                case "8":
                    System.out.println("Enter name of department:");
                    String name = input.nextLine();
                    Department department = op1.findDepartment(name);
                    if(department!= null) {
                        System.out.println("The avg is: " +op1.getSalaryForAll(department));
                    }
                    else {
                        System.out.println("Department not found");
                    }
                    break;
                case "9":
                    op1.lectureDetails();
                    break;
                case "10":
                    op1.printAllBoards();
                    break;
                case "11":
                    op1.addLectureToDepartment();
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
        System.out.println("11 - Add Lecture to Department");
    }

}


