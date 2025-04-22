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

                    System.out.println("Enter Lecture name: ");
                    String nameNewLecture = input.nextLine();

                    System.out.println("Enter Lecture ID: ");
                    String id = input.nextLine();
                    if (op1.existLecture(id)) {
                        System.out.println("Lecture with this ID already exists.");
                        break;
                    }

                    System.out.println("Enter Lecture degree (1-4):");
                    for (Degree d: Degree.values()) {
                        System.out.println(d.getNumber() + " - " + d);
                    }

                    int num = input.nextInt();
                    Degree degree = Degree.fromNumber(num);

                    if (degree == null) {
                        System.out.println("Invalid degree choice.");
                        break;
                    }

                    input.nextLine();
                    System.out.println("Enter name of the degree: ");
                    String nameDegree = input.nextLine();

                    System.out.println("Enter Lecture salary: ");
                    double salary = input.nextDouble();
                    input.nextLine();

                    if (salary < 0) {
                        System.out.println("Salary cannot be negative.");
                        break;
                    }

                    Lecture NewLecture = new Lecture(nameNewLecture, id, degree, nameDegree, salary);
                    op1.addLecture(NewLecture);
                    System.out.println("Lecture " + nameNewLecture + " added successfully");

                    System.out.println("Do you want to assign this lecture to a department now? (yes/no)");
                    String choiceDep = input.nextLine();
                    if (choiceDep.equals("yes")) {
                        System.out.println("Enter department name:");
                        String depName = input.nextLine();

                        Department department = op1.findDepartment(depName);
                        if (department == null) {
                            System.out.println("Department does not exist.");
                        } else {
                            boolean added = department.addLecturer(NewLecture);
                            if (added) {
                                System.out.println("Lecture successfully assigned to department: " + depName);
                            } else {
                                System.out.println("Failed to assign lecture to department.");
                            }
                        }
                    } else {
                        System.out.println("Lecture remains without a department.");
                    }
                    break;

                case "2":
                    op1.insertBoard();
                    break;

                case "3":

                    System.out.println("Enter name of the board: ");
                    String boardName = input.nextLine();

                    Board board = op1.findBoardByName(boardName);

                    if (board == null) {
                        System.out.println("Board " + boardName + " does not found.");
                        break;
                    }

                    System.out.println("Enter name of the lecture to be added: ");
                    String lectureName = input.nextLine();

                    Lecture lecture = op1.findLectureByName(lectureName);

                    if (lecture == null) {
                        System.out.println("Lecture " + lectureName + " does not exist.");
                        break;
                    }
                    board.addLecture(lecture);
                    break;

                case "4":

                    System.out.println("Enter name of the board: ");
                    String boardName1 = input.nextLine();

                    Board board1 = op1.findBoardByName(boardName1);

                    if (board1 == null) {
                        System.out.println("Board " + boardName1 + " does not exist.");
                        break;
                    }

                    System.out.println("Enter name of the lecture to be updated: ");
                    String lectureName1 = input.nextLine();
                    Lecture toUpdateManager = op1.findLectureByName(lectureName1);

                    if (toUpdateManager == null) {
                        System.out.println("Lecture " + lectureName1 + " does not exist.");
                    } else if (board1.getManagerBoard().getName().equals(lectureName1)) {
                        System.out.println(lectureName1 + " is already the manager of this board.");
                    } else {
                        board1.setManagerBoard(toUpdateManager);
                    }
                    break;

                case "5":
                    System.out.println("Enter name of the board: ");
                    String boardName2 = input.nextLine();

                    Board board2 = op1.findBoardByName(boardName2);

                    if (board2 == null) {
                        System.out.println("Board " + boardName2 + " does not exist.");
                        break;
                    }
                    System.out.println("Enter the name you want to remove: ");
                    String memberName = input.nextLine();

                    boolean found = op1.removeFromBoard(board2, memberName);
                    if (found) {
                        System.out.println(memberName + " deleted successfully");
                    } else {
                        System.out.println("Lecture " + memberName + " is not found in the board");
                    }
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


