import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Main mainObj = new Main();
        System.out.println("Enter file name to load/save system data:");
        String fileName = mainObj.getStrFromUser();
        OperatingSystem op1;

        File file = new File(fileName);
        if(file.exists()){
            op1 = OperatingSystem.loadSystemFile(fileName);
            System.out.println("System loaded from file: " + fileName);
        }
        else {
            op1 = new OperatingSystem();
            System.out.println("No existing file found. Starting with empty system.");
        }

        mainObj.printWelcome();

        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            String choice = mainObj.getStrFromUser();

            switch (choice) {
                case "0":
                    if(op1.saveSystemToFile(fileName)){
                        System.out.println("System saved successfully to: " + fileName);
                    }
                    else {
                        System.out.println("Failed to save system.");
                    }
                    isRunning = false;
                    break;

                case "1":
                    try {
                        System.out.print("Enter Lecture name: ");
                        String nameNewLecture = mainObj.getStrFromUser();

                        while (op1.existLecture(nameNewLecture)) {
                            System.out.println("Lecture with this name already exists. Please enter a new name.");
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

                        System.out.print("Enter name of the degree: ");
                        String nameDegree = mainObj.getStrFromUser();

                        System.out.print("Enter Lecture salary: ");
                        double salary = mainObj.getDoubleFromUser();

                        if (salary < 0) {
                            System.out.println("Error: Salary cannot be negative.");
                            break;
                        }

                        Lecture lectureToAdd;

                        if (degree == Degree.DR || degree == Degree.PROFESSOR) {
                            System.out.print("Enter number of published articles: ");
                            int numArticles = mainObj.getIntFromUser();

                            String[] articles = new String[numArticles];
                            for (int i = 0; i < numArticles; i++) {
                                System.out.print("Enter name of article number " + (i + 1) + ": ");
                                articles[i] = mainObj.getStrFromUser();
                            }

                            if (degree == Degree.DR) {
                                Doctor newDr = new Doctor(nameNewLecture, id, degree, nameDegree, salary);
                                for (String article : articles) {
                                    newDr.addArticle(article);
                                }
                                lectureToAdd = newDr;
                            } else {
                                System.out.print("Enter the organization that granted the professorship: ");
                                String organization = mainObj.getStrFromUser();
                                Professor newProf = new Professor(nameNewLecture, id, degree, nameDegree, salary, organization);
                                for (String article : articles) {
                                    newProf.addArticle(article);
                                }
                                lectureToAdd = newProf;
                            }
                        } else {
                            lectureToAdd = new Lecture(nameNewLecture, id, degree, nameDegree, salary);
                        }

                        op1.addLecture(lectureToAdd);
                        System.out.println(lectureToAdd.getClass().getSimpleName() + " " + nameNewLecture + " added successfully");

                        System.out.print("Do you want to assign this lecture to a department now? (yes/no): ");
                        if (mainObj.getBoolFromUser()) {
                            System.out.print("Enter department name: ");
                            String depName = mainObj.getStrFromUser();
                            Department department = op1.findDepartment(depName);
                            if (department != null) {
                                String result = department.addLecturer(lectureToAdd);
                                System.out.println(result);
                            } else {
                                System.out.println("Department not found.");
                            }
                        } else {
                            System.out.println("The lecture was not assigned to any department.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        System.out.print("Enter board name: ");
                        String boardName = mainObj.getStrFromUser();

                        if(op1.existBoard(boardName)){
                            System.out.println("Board with this name already exist.");
                            break;
                        }

                        System.out.print("Enter the name of the lecturer that will be the board manager: ");
                        String managerName = mainObj.getStrFromUser();

                        Lecture manager = op1.findLectureByName(managerName);
                        if (manager == null) {
                            System.out.println("Manager not found.");
                            break;
                        }

                        manager.checkIsValidManager();

                        System.out.println("Select the degree for this board:");
                        for (Degree d : Degree.values()) {
                            System.out.println(d.getNumber() + " - " + d);
                        }

                        int degreeNum = mainObj.getIntFromUser();
                        Degree degree = Degree.fromNumber(degreeNum);

                        if (degree == null) {
                            System.out.println("Invalid degree choice.");
                            break;
                        }

                        Board<? extends Lecture> newBoard;

                        if (degree == Degree.BA || degree == Degree.MA) {
                            newBoard = new Board<>(boardName, manager, degree);
                        } else if (degree == Degree.DR || degree == Degree.PROFESSOR) {
                            if (!(manager instanceof Doctor)) {
                                System.out.println("Manager must be Doctor or Professor for this board.");
                                break;
                            }
                            newBoard = new Board<Doctor>(boardName, manager, degree);
                        } else {
                            System.out.println("Unsupported degree type.");
                            break;
                        }

                        op1.addBoardToArray(newBoard);
                        System.out.println("Board " + boardName + " added successfully with manager " + manager.getName());

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;


                case "3":
                    try {
                        System.out.print("Enter board name: ");
                        String boardName = mainObj.getStrFromUser();
                        Board board = op1.findBoardByName(boardName);
                        if (board == null) {
                            System.out.println("Board not found.");
                            break;
                        }

                        System.out.print("Enter name of the lecture to be added: ");
                        String lectureName = mainObj.getStrFromUser();
                        Lecture lecture = op1.findLectureByName(lectureName);
                        if (lecture == null) {
                            System.out.println("Lecture not found.");
                            break;
                        }

                        if (board.getManagerBoard() != null && board.getManagerBoard().getName().equals(lecture.getName())) {
                            System.out.println("This lecture is the board manager and cannot be added as a regular member.");
                            break;
                        }

                        board.addLecture(lecture);
                        System.out.println("Lecture " + lecture.getName() + " added to the board.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "4":
                    try {
                        System.out.print("Enter board name: ");
                        String boardName1 = mainObj.getStrFromUser();
                        Board boardd = op1.findBoardByName(boardName1);
                        if (boardd == null) {
                            System.out.println("Board not found.");
                            break;
                        }

                        System.out.print("Enter name of the lecture to be set as manager: ");
                        String lec = mainObj.getStrFromUser();
                        Lecture lect = op1.findLectureByName(lec);
                        if (lect == null) {
                            System.out.println("Lecture not found.");
                            break;
                        }

                        lect.checkIsValidManager();

                        if (boardd.getManagerBoard() != null && boardd.getManagerBoard().getName().equals(lect.getName())) {
                            System.out.println("This lecture is already the manager.");
                            break;
                        }

                        boardd.setManagerBoard(lect);
                        System.out.println("Board manager updated successfully to " + lect.getName());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "5":
                    try {
                        System.out.print("Enter board name: ");
                        String boardName3 = mainObj.getStrFromUser();
                        Board board3 = op1.findBoardByName(boardName3);
                        if (board3 == null) {
                            System.out.println("Board not found.");
                            break;
                        }

                        System.out.print("Enter the name you want to remove: ");
                        String memberName = mainObj.getStrFromUser();

                        String res = op1.removeFromBoard(board3, memberName);
                        System.out.println(res);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "6":
                    try {
                        System.out.print("Enter department name: ");
                        String depName = mainObj.getStrFromUser();

                        System.out.print("Enter number of students: ");
                        int numStudents = mainObj.getIntFromUser();

                        String deptResult = op1.addDepartment(depName, numStudents);
                        System.out.println(deptResult);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "7":
                    System.out.println("The avg is: " + op1.getSalaryForAll(null));
                    break;

                case "8":
                    try {
                        System.out.print("Enter name of department: ");
                        String name = mainObj.getStrFromUser();
                        Department department = op1.findDepartment(name);
                        if (department != null) {
                            System.out.println("The avg is: " + op1.getSalaryForAll(department));
                        } else {
                            System.out.println("Department not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "9":
                    System.out.println("Details of the lecturers at the college:");
                    System.out.println("-----------------------------------------");
                    for (Lecture lectur : op1.getLectures()) {
                        if (lectur != null) {
                            System.out.println(lectur);
                        }
                    }
                    break;

                case "10":
                    System.out.println("Boards details:");
                    System.out.println("------------------------");
                    int count = 1;

                    for (Board boardDets : op1.getBoards()) {
                        if (boardDets != null) {
                            System.out.println(count + ". Board name: " + boardDets.getName());

                            Lecture managerr = boardDets.getManagerBoard();
                            System.out.println("   Name of the Manager: " + (managerr != null ? managerr.getName() : "None"));

                            System.out.print("   Members: ");
                            boolean hasMembers = false;

                            ArrayList<Lecture> membersArray = boardDets.getLectures();
                            for (int i = 0; i < boardDets.getLogicalSize(); i++) {
                                Lecture member = membersArray.get(i);
                                if (member != null && (managerr == null || !member.getId().equals(managerr.getId()))) {
                                    if (hasMembers) {
                                        System.out.print(", ");
                                    }
                                    System.out.print(member.getName());
                                    hasMembers = true;
                                }
                            }

                            if (!hasMembers) {
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
                    try {
                        System.out.print("Enter name of the lecture to be added: ");
                        String lecName = mainObj.getStrFromUser();

                        System.out.print("Enter department name: ");
                        String departmentName = mainObj.getStrFromUser();

                        String res = op1.addLectureToDepartment(lecName, departmentName);
                        System.out.println(res);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "12":
                    try {
                        System.out.println("Enter name of first lecturer (Doctoral degree or higher): ");
                        String lecName1 = mainObj.getStrFromUser();
                        Lecture lec1 = op1.findLectureByName(lecName1);
                        if (lec1 == null || !(lec1 instanceof Doctor)) {
                            System.out.println("Error: First lecturer is not a Doctor or Professor.");
                            break;
                        }

                        System.out.println("Enter name of second lecturer (Doctoral degree or higher): ");
                        String lecName2 = mainObj.getStrFromUser();
                        Lecture lec2 = op1.findLectureByName(lecName2);
                        if (lec2 == null || !(lec2 instanceof Doctor)) {
                            System.out.println("Error: Second lecturer is not a Doctor or Professor.");
                            break;
                        }

                        int compareResult = ((Doctor) lec1).compareTo((Doctor) lec2);
                        if (compareResult > 0) {
                            System.out.println(lec1.getName() + " has more articles.");
                        } else if (compareResult < 0) {
                            System.out.println(lec2.getName() + " has more articles.");
                        } else {
                            System.out.println("Both have the same number of articles.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "13":
                    try {
                        while (true) {
                            System.out.println("Enter the name of the first board: ");
                            String b1 = mainObj.getStrFromUser();
                            if (b1.equals("back"))
                                break;

                            Board board1 = op1.findBoardByName(b1);
                            if (board1 == null) {
                                System.out.println("First board not found.");
                                continue;
                            }

                            System.out.println("Enter the name of the second board: ");
                            String b2 = mainObj.getStrFromUser();
                            if (b2.equals("back"))
                                break;

                            Board board2 = op1.findBoardByName(b2);
                            if (board2 == null) {
                                System.out.println("Second board not found.");
                                continue;
                            }

                            System.out.println("Choose type of compare option:");
                            System.out.println("[1] Compare by number of Articles");
                            System.out.println("[2] Compare by number of Lectures");
                            System.out.println("[3] Back to main menu");

                            String option = mainObj.getStrFromUser();

                            if (option.equals("3"))
                                break;

                            switch (option) {
                                case "1":
                                    String resultArticles = op1.compareBoardByArticles(b1, b2);
                                    if (resultArticles.startsWith("Both boards")) {
                                        System.out.println(resultArticles);
                                    } else {
                                        System.out.println(resultArticles + " has more articles.");
                                    }
                                    break;

                                case "2":
                                    String resultLectures = op1.compareBoardByLec(b1, b2);
                                    if (resultLectures.startsWith("Both boards")) {
                                        System.out.println(resultLectures);
                                    } else {
                                        System.out.println(resultLectures + " has more lectures.");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                            }
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "14":
                    try {
                        System.out.println("Enter the name of the board you want to copy: ");
                        String nameBoardToCopy = mainObj.getStrFromUser();
                        Board originalBoard = op1.findBoardByName(nameBoardToCopy);
                        if (originalBoard == null) {
                            System.out.println("Board not found.");
                            break;
                        }
                        Board copyBoard = originalBoard.clone();
                        op1.addBoardToArray(copyBoard);
                        System.out.println("Board " + nameBoardToCopy + " duplicated successfully");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                default:
                    System.err.println("Wrong input");
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
        System.out.println("12 - Compare two Dr./Prof. by articles");
        System.out.println("13 - Compare between Boards");
        System.out.println("14 - Duplicate Board");
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
        return answer.equalsIgnoreCase("yes");
    }

    public double getDoubleFromUser() {
        while (!input.hasNextDouble()) {
            System.out.println("Please enter a valid number.");
            input.next();
        }
        double value = input.nextDouble();
        input.nextLine();
        return value;
    }

    public int getIntFromUser() {
        while (!input.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            input.next();
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }
}
