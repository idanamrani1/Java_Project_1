// students name: idan amrani and shaked hakim
// students ID: 322205808, 325117653

import Exceptions.*;

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
                    try {
                        System.out.print("Enter Lecture name: ");
                        String nameNewLecture = mainObj.getStrFromUser();

                        while (op1.existLecture(nameNewLecture)) {
                            System.out.println("Lecture with this name already exists. Please enter a new name.");
                            System.out.print("Enter Lecture name: ");
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
                            throw new InvalidDegreeException("Invalid degree choice.");
                        }
                        mainObj.input.nextLine();

                        System.out.print("Enter name of the degree: ");
                        String nameDegree = mainObj.getStrFromUser();

                        System.out.print("Enter Lecture salary: ");
                        double salary = mainObj.getDoubleFromUser();
                        mainObj.input.nextLine();

                        if (salary < 0) {
                            throw new InvalidSalaryException("Error: Salary cannot be negative.");
                        }

                        Lecture lectureToAdd = null; // we will change it

                        if (degree == Degree.DR || degree == Degree.PROFESSOR) {
                            System.out.println("Enter number of published articles: ");
                            int numArticles = mainObj.getIntFromUser();

                            String[] articles = new String[numArticles];
                            for (int i = 0; i < numArticles; i++) {
                                System.out.println("Enter name of article number " + (i + 1) + ": ");
                                articles[i] = mainObj.getStrFromUser();
                            }

                            if (degree == Degree.DR) {
                                Doctor newDr = new Doctor(nameNewLecture, id, degree, nameDegree, salary);
                                for (int i = 0; i < numArticles; i++) {
                                    newDr.addArticle(articles[i]);
                                }
                                lectureToAdd = newDr;

                            } else {  // degree == Degree.PROFESSOR
                                System.out.print("Enter the organization that granted the professorship: ");
                                String organization = mainObj.getStrFromUser();

                                Professor newProf = new Professor(nameNewLecture, id, degree, nameDegree, salary, organization);
                                for (int i = 0; i < numArticles; i++) {
                                    newProf.addArticle(articles[i]);
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
                            String result = department.addLecturer(lectureToAdd);
                            System.out.println(result);
                        } else {
                            System.out.println("The lecture was not assigned to any department.");
                        }

                    } catch (ObjectNotFoundException | InvalidDegreeException | InvalidSalaryException e) {
                        System.err.println(e.getMessage());
                    }
                    break;


                case "2":
                    try {
                        System.out.print("Enter board name: ");
                        String boardName = mainObj.getStrFromUser();

                        op1.existBoard(boardName);

                        System.out.print("Enter the name of the lecturer that will be the board manager: ");
                        String managerName = mainObj.getStrFromUser();

                        Lecture manager = op1.findLectureByName(managerName);

                        manager.checkIsValidManager();

                        Board newBoard = new Board(boardName, new Lecture[1], manager);
                        op1.addBoardToArray(newBoard);
                        System.out.println("Board " + boardName + " added successfully with manager " + manager.getName());

                    } catch (AlreadyNameExistsException | ObjectNotFoundException | InvalidManagerException e) {
                        System.err.println(e.getMessage());
                    }
                    break;


                case "3":
                    System.out.print("Enter name of the board: ");
                    String boardName = mainObj.getStrFromUser();
                    Board board = null;
                    try {
                        board = op1.findBoardByName(boardName);
                    } catch (ObjectNotFoundException e) {
                        System.err.println(e.getMessage());
                        break;
                    }

                    System.out.print("Enter name of the lecture to be added: ");
                    String lectureName = mainObj.getStrFromUser();
                    Lecture lecture = null;
                    try {
                        lecture = op1.findLectureByName(lectureName);
                    } catch (ObjectNotFoundException e) {
                        System.err.println(e.getMessage());
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
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;


                case "4":
                    try {
                        System.out.print("Enter name of the board: ");
                        String boardName1 = mainObj.getStrFromUser();
                        Board boardd = op1.findBoardByName(boardName1);

                        if (boardd == null) {
                            throw new ObjectNotFoundException("Board " + boardName1 + " does not exist.");
                        }

                        System.out.print("Enter name of the lecture to be set as manager: ");
                        String lec = mainObj.getStrFromUser();
                        Lecture lect = op1.findLectureByName(lec);

                        if (lect == null) {
                            throw new ObjectNotFoundException("Lecture " + lec + " does not exist.");
                        }

                        lect.checkIsValidManager();

                        if (boardd.getManagerBoard() != null &&
                                boardd.getManagerBoard().getName().equals(lect.getName())) {
                            throw new AlreadyNameExistsException(lec + " is already the manager of this board.");
                        }

                        boardd.setManagerBoard(lect);
                        System.out.println("Board manager updated successfully to " + lect.getName());

                    } catch (ObjectNotFoundException | AlreadyNameExistsException | InvalidManagerException e) {
                        System.err.println(e.getMessage());
                    }
                    break;


                case "5":
                    try {
                        System.out.print("Enter name of the board: ");
                        String boardName3 = mainObj.getStrFromUser();
                        Board board3 = op1.findBoardByName(boardName3);

                        if (board3 == null) {
                            throw new ObjectNotFoundException("Board " + boardName3 + " does not exist.");
                        }

                        System.out.print("Enter the name you want to remove: ");
                        String memberName = mainObj.getStrFromUser();

                        op1.removeFromBoard(board3, memberName);
                        System.out.println(memberName + " deleted successfully.");

                    } catch (ObjectNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;


                case "6":
                    try {
                        System.out.print("Enter department name: ");
                        String depName = mainObj.getStrFromUser();

                        System.out.print("Enter number of students: ");
                        int numStudents = mainObj.getIntFromUser();
                        mainObj.input.nextLine();

                        String deptResult = op1.addDepartment(depName, numStudents);
                        System.out.println(deptResult);

                    } catch (AlreadyNameExistsException e) {
                        System.err.println("Error: " + e.getMessage());
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

                        System.out.println("The avg is: " + op1.getSalaryForAll(department));
                    } catch (ObjectNotFoundException e) {
                        System.err.println(e.getMessage());
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
                    try {
                        System.out.print("Enter name of the lecture to be added: ");
                        String lecName = mainObj.getStrFromUser();

                        System.out.print("Enter department name: ");
                        String departmentName = mainObj.getStrFromUser();

                        String res = op1.addLectureToDepartment(lecName, departmentName);
                        System.out.println(res);
                    } catch (ObjectNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case "12":
                    try {
                        System.out.println("Enter name of lecture (Doctoral degree or higher): ");
                        String lecName1 = mainObj.getStrFromUser();
                        Lecture lec1 = op1.findLectureByName(lecName1);

                        if (!(lec1 instanceof Doctor && lec1 instanceof Professor)) {
                            throw new InvalidDegreeException("Error: Lecturer is not a Doctor or Professor.");
                        }
                        System.out.println("Enter name of lecture (Doctoral degree or higher): ");
                        String lecName2 = mainObj.getStrFromUser();
                        Lecture lec2 = op1.findLectureByName(lecName2);

                        if (!(lec2 instanceof Doctor && lec2 instanceof Professor)) {
                            throw new InvalidDegreeException("Error: Lecturer is not a Doctor or Professor.");
                        }

                        int numOfArticlesLec1 = 0;
                        int numOfArticlesLec2 = 0;

                        if (lec1 instanceof Doctor) {
                            numOfArticlesLec1 = ((Doctor) lec1).getNumberOfArticles();
                        }
                        else if (lec1 instanceof Professor) {
                            numOfArticlesLec1 = ((Professor) lec1).getNumberOfArticles();
                        }

                        if (lec2 instanceof Doctor) {
                            numOfArticlesLec2 = ((Doctor) lec2).getNumberOfArticles();
                        }
                        else if (lec2 instanceof Professor) {
                            numOfArticlesLec2 = ((Professor) lec2).getNumberOfArticles();
                        }

                        if (numOfArticlesLec1 == 0 && numOfArticlesLec2 == 0) {
                            throw new NoArticlesException("Both lecturers have zero articles.");
                        }









                    } catch (ObjectNotFoundException | InvalidDegreeException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "13":
                    break;

                case "14":
                    System.out.println("Enter the name of the board you want to copy: ");
                    String nameBoardToCopy = mainObj.getStrFromUser();

                    try{
                        Board originalBoard = op1.findBoardByName(nameBoardToCopy);
                        Board copyBoard = originalBoard.clone();

                        op1.addBoardToArray(copyBoard);

                        System.out.println("Board " + nameBoardToCopy+ " duplicated successfully");
                    } catch (ObjectNotFoundException | CloneNotSupportedException e){
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;

                case "15":
                    try {
                        System.out.print("Enter the lecture name you want to remove from department: ");
                        String lectureNamee = mainObj.getStrFromUser();

                        Lecture lectureToRemoveFromDep = op1.findLectureByName(lectureNamee);

                        Department dept = lectureToRemoveFromDep.getBelongDepartment();
                        if (dept == null) {
                            System.out.println("Lecture does not belong to any department.");
                            break;
                        }

                        op1.removeFromDepartment(dept, lectureNamee);
                        System.out.println(lectureNamee + " deleted successfully from department.");

                    } catch (ObjectNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
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
        System.out.println("12 - Comparison between a Dr. or/ and a Prof. based on the number of their articles ");
        System.out.println("13 - Comparison between departments according to 2 criteria:" +
                "\n     according to the number of faculty members assigned to them or according to the total number" +
                "\n     of articles written by committee members");
        System.out.println("14 - Duplicate Board");
        System.out.println("15 - remove from department");
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
