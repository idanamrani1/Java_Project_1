// students name: idan amrani and shaked hakim
// students ID: 322205808, 325117653

import Exceptions.AlreadyManagerException;
import Exceptions.AlreadyMemberException;
import Exceptions.LectureNotFoundException;

import java.util.Scanner;

public class Main {
    private Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Main mainObj = new Main();
        OperatingSystem op1 = new OperatingSystem();
        mainObj.printWelcome();

        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            String choice = mainObj.input.nextLine();

            switch (choice) {
                case "0":
                    isRunning = false;
                    break;

                case "1":
                    System.out.print("Enter Lecture name: ");
                    String nameNewLecture = mainObj.getStrFromUser();
                    while (op1.existLecture(nameNewLecture)) {
                        System.out.println("Lecture with this name already exists.");
                        System.out.println("Enter different name:");
                        nameNewLecture = mainObj.getStrFromUser();
                    }

                    System.out.print("Enter Lecture ID: ");
                    String id = mainObj.getStrFromUser();

                    System.out.println("Enter Lecture degree (1-4):");
                    for (Degree d : Degree.values()) {
                        System.out.println(d.getNumber() + " - " + d);
                    }
                    int num = mainObj.getIntFromUser();
                    Degree degree = Degree.fromNumber(num);

                    if (degree == null) {
                        System.out.println("Invalid degree choice.");
                        break;
                    }
                    mainObj.input.nextLine();

                    System.out.print("Enter name of the degree: ");
                    String nameDegree = mainObj.getStrFromUser();

                    System.out.print("Enter Lecture salary: ");
                    double salary = mainObj.getDoubleFromUser();
                    mainObj.input.nextLine();

                    if (salary < 0) {
                        System.out.println("Salary cannot be negative.");
                        break;
                    }

                    Lecture NewLecture = new Lecture(nameNewLecture, id, degree, nameDegree, salary);
                    op1.addLecture(NewLecture);
                    System.out.println("Lecture " + nameNewLecture + " added successfully");

                    System.out.print("Do you want to assign this lecture to a department now? (yes/no): ");
                    if (mainObj.getBoolFromUser()) {
                        System.out.print("Enter department name: ");
                        String depName = mainObj.getStrFromUser();

                        Department department = op1.findDepartment(depName);
                        if (department == null) {
                            System.out.println("Department " + depName + " does not exist.");
                        } else {
                            String result = department.addLecturer(NewLecture);
                            System.out.println(result);
                        }
                    }
                    break;
                case "2":
                    System.out.print("Enter board name: ");
                    String boardName = mainObj.getStrFromUser();

                    if (op1.existBoard(boardName)) {
                        System.out.println("Board already exists.");
                        break;
                    }

                    System.out.print("Enter the name of the lecturer that will be the board manager: ");
                    String managerName = mainObj.getStrFromUser();
                    Lecture manager = op1.findLectureByName(managerName);

                    if (manager == null) {
                        System.out.println("That lecturer does not exist.");
                        break;
                    }

                    if (!manager.checkIsValidManager()) {
                        System.out.println("The lecturer must be a Dr. or Prof. to be the board manager.");
                        break;
                    }

                    Board newBoard = new Board(boardName, new Lecture[1], manager);
                    op1.addBoardToArray(newBoard);
                    System.out.println("Board " + boardName + " added successfully with manager " + manager.getName());

                    break;

                case "3":
                    System.out.print("Enter name of the board: ");
                    String boardiName = mainObj.getStrFromUser();
                    Board board = op1.findBoardByName(boardiName);

                    if (board == null) {
                        System.out.println("Board " + boardiName + " does not exist.");
                        break;
                    }

                    System.out.print("Enter name of the lecture to be added: ");
                    String lectureName = mainObj.getStrFromUser();
                    Lecture lecture = op1.findLectureByName(lectureName);

                    if (lecture == null) {
                        System.out.println("Lecture " + lectureName + " does not exist.");
                        break;
                    }

                    if (board.getManagerBoard() != null && board.getManagerBoard().getName().equals(lecture.getName())) {
                        System.out.println("This lecture is the board manager and cannot be added as a regular member.");
                        break;
                    }

                    try {
                        board.addLecture(lecture);
                        System.out.println("Lecture " + lecture.getName() + " added to the board.");
                    } catch (AlreadyManagerException | AlreadyMemberException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                case "4":
                    System.out.print("Enter name of the board: ");
                    String boardName1 = mainObj.getStrFromUser();
                    Board boardd = op1.findBoardByName(boardName1);

                    if (boardd == null) {
                        System.out.println("Board " + boardName1 + " does not exist.");
                        break;
                    }

                    System.out.print("Enter name of the lecture to be set as manager: ");
                    String lec = mainObj.getStrFromUser();
                    Lecture lect = op1.findLectureByName(lec);

                    if (lect == null) {
                        System.out.println("Lecture " + lec + " does not exist.");
                        break;
                    }

                    if (!lect.checkIsValidManager()) {
                        System.out.println("Only a Dr. or Professor can be assigned as board manager.");
                        break;
                    }

                    if (boardd.getManagerBoard() != null &&
                            boardd.getManagerBoard().getName().equals(lect.getName())) {
                        System.out.println(lec + " is already the manager of this board.");
                        break;
                    }

                    boardd.setManagerBoard(lect);
                    System.out.println("Board manager updated successfully to " + lect.getName());
                    break;

                case "5":
                    System.out.print("Enter name of the board: ");
                    String boardName3 = mainObj.getStrFromUser();
                    Board board3 = op1.findBoardByName(boardName3);

                    if (board3 == null) {
                        System.out.println("Board " + boardName3 + " does not exist.");
                        break;
                    }

                    System.out.print("Enter the name you want to remove: ");
                    String memberName = mainObj.getStrFromUser();
                    try {
                        op1.removeFromBoard(board3, memberName);
                        System.out.println(memberName + " deleted successfully.");
                    } catch (LectureNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                case "6":
                    System.out.print("Enter department name: ");
                    String depName = mainObj.getStrFromUser();

                    System.out.print("Enter number of students: ");
                    int numStudents = mainObj.getIntFromUser();
                    mainObj.input.nextLine();

                    String deptResult = op1.addDepartment(depName, numStudents);
                    System.out.println(deptResult);
                    break;


                case "7":
                    System.out.println("The avg is: " + op1.getSalaryForAll(null));
                    break;

                case "8":
                    System.out.print("Enter name of department: ");
                    String name = mainObj.getStrFromUser();
                    Department department = op1.findDepartment(name);

                    if (department != null) {
                        System.out.println("The avg is: " + op1.getSalaryForAll(department));
                    } else {
                        System.out.println("Department not found");
                    }
                    break;

                case "9":
                    System.out.println("Details of the lecturers at the college:");
                    System.out.println("-----------------------------------------");
                    Lecture[] lectures = op1.getLectures();
                    for (Lecture lectur : lectures) {
                        if (lectur != null) {
                            System.out.println(lectur);
                        }
                    }
                    break;

                case "10":
                    System.out.println("    Boards details:");
                    System.out.println("------------------------");
                    Board[] boards = op1.getBoards();
                    int count = 1;

                    for (Board boardDets : boards) {
                        if (boardDets != null) {
                            System.out.println(count + ". Board name: " + boardDets.getName());

                            Lecture managerr = boardDets.getManagerBoard();
                            if (managerr != null) {
                                System.out.println("   Name of the Manager: " + managerr.getName());
                            } else {
                                System.out.println("   No Manager assigned");
                            }

                            Lecture[] members = boardDets.getLectures();
                            boolean noMember = true;

                            System.out.print("   Members: ");
                            for (Lecture member : members) {
                                if (member != null && managerr != null && !member.getId().equals(managerr.getId())) {
                                    if (!noMember) {
                                        System.out.print(", ");
                                    }
                                    System.out.print(member.getName());
                                    noMember = false;
                                }
                            }
                            if (noMember) {
                                System.out.print("No members");
                            }

                            System.out.println();
                            System.out.println("   Number of Members: " + boardDets.getLogicalSize());
                            System.out.println();

                            count++;
                        }
                    }
                    break;

                case "11":
                    System.out.print("Enter name of the lecture to be added: ");
                    String lecName = mainObj.getStrFromUser();

                    System.out.print("Enter department name: ");
                    String departmentName = mainObj.getStrFromUser();
                    String res = op1.addLectureToDepartment(lecName, departmentName);
                    System.out.println(res);

                    break;

                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public static void printMenu() {
        System.out.println("\n----Menu----");
        System.out.println("0 - Exit");
        System.out.println("1 - Add Lecture");
        System.out.println("2 - Add board to College");
        System.out.println("3 - Add to the board");
        System.out.println("4 - Update board manager");
        System.out.println("5 - Remove from board");
        System.out.println("6 - Add study department");
        System.out.println("7 - Lecture avg salary");
        System.out.println("8 - Avg salary per department");
        System.out.println("9 - Show details about Lectures");
        System.out.println("10 - Show details about boards");
        System.out.println("11 - Add Lecture to Department");
    }

    public void printWelcome() {
        System.out.println("Welcome to our system! 🎓");
        System.out.print("To get started, please enter the name of your college: ");
        input.nextLine();
    }

    public String getStrFromUser() {
        return input.nextLine();
    }

    public boolean getBoolFromUser() {
        String answer = input.nextLine();
        return answer.equals("yes");
    }

    public double getDoubleFromUser() {
        return input.nextDouble();
    }

    public int getIntFromUser() {
        return input.nextInt();
    }
}
