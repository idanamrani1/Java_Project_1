
import javax.print.DocFlavor;
import java.util.Scanner;

public class OperatingSystem {
    private Scanner input = new Scanner(System.in);
    private static Lecture[] arrLecture = new Lecture[1];
    private static Department[] arrDepartment = new Department[1];
    private static Board[] arrBoard = new Board[1];

    public void printWelcome() {
        System.out.println("Welcome to our system! 🎓");
        System.out.print("To get started, please enter the name of your college: ");
        input.nextLine();
    }

    public void insertLectureDetails() {
        System.out.println("Enter Lecture name: ");
        String name = input.nextLine();

        System.out.println("Enter Lecture ID: ");
        String id = input.nextLine();

        if (existLecture(id)) {
            System.out.println("Lecture with this ID already exists.");
            return;
        }

        System.out.println("Enter Lecture degree: (Prof/Dr)");
        String degree = input.nextLine();

        System.out.println("Enter name of the degree: ");
        String nameDegree = input.nextLine();

        System.out.println("Enter Lecture salary: ");
        double salary = input.nextDouble();
        input.nextLine();

        Lecture newLecture = new Lecture(name, id, degree, nameDegree, salary);

        if ((OperationsOnArrays.isFullArray(arrLecture))) {
            arrLecture = extendLectureArray(arrLecture);
        }
        addLectureToArray(newLecture);

        System.out.println("Enter Department name to assign this lecture: ");
        String depName = input.nextLine();

        Department dep = findOrCreateDepartment(depName);
        boolean added = dep.addLecturer(newLecture);

        if (added) {
            System.out.println("Lecture successfully assigned to department.");
        } else {
            System.out.println("Failed to assign lecture to department.");
        }
    }

    private void addLectureToArray(Lecture newLecture) {
        for (int i = 0; i < arrLecture.length; i++) {
            if (arrLecture[i] == null) {
                arrLecture[i] = newLecture;
                return;
            }
        }
    }


    private boolean existLecture(String id) {
        for (Lecture lecture : arrLecture) {
            if (lecture != null && lecture.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private Lecture[] extendLectureArray(Lecture[] oldArr) {
        Lecture[] newArr = new Lecture[oldArr.length * 2];
        for (int i = 0; i < oldArr.length; i++) {
            newArr[i] = oldArr[i];
        }
        return newArr;
    }

    private Department findOrCreateDepartment(String name) {
        for (Department dep : arrDepartment) {
            if (dep != null && dep.getDepName().equals(name)) {
                return dep;
            }
        }
        Lecture[] newDepLectures = new Lecture[5];
        Department newDep = new Department(name, 0, newDepLectures);

        for (int i = 0; i < arrDepartment.length; i++) {
            if (arrDepartment[i] == null) {
                arrDepartment[i] = newDep;
                return newDep;
            }
        }

        arrDepartment = extendDepartmentArray(arrDepartment);
        arrDepartment[arrDepartment.length / 2] = newDep;
        return newDep;
    }

    private Department[] extendDepartmentArray(Department[] oldArr) {
        Department[] newArr = new Department[oldArr.length * 2];
        for (int i = 0; i < oldArr.length; i++) {
            newArr[i] = oldArr[i];
        }
        return newArr;
    }

    public void insertBoard() {
        System.out.println("Enter board name: ");
        String boardName = input.nextLine();

        if (existBoard(boardName)) {
            System.out.println("Board already exists.");
            return;
        }
        System.out.println("Enter the name of the lecturer that will be the board manager:");
        String managerName = input.nextLine();

        Lecture manager = findLectureByName(managerName);

        if (manager == null) {
            System.out.println("That lecturer does not exist.");
            return;
        }
        if (manager.checkIsValidManager()) {
            System.out.println(managerName + " is now the board manager");

            Board newBoard = new Board(boardName, new Lecture[1], manager);
            addBoardToArray(newBoard);
            System.out.println("Board " + boardName + " added successfully.");
        } else {
            System.out.println("The lecturer must be a Dr. or Prof. to be the board manager");
        }
    }

    private boolean existBoard(String boardName) {
        for (int i = 0; i < arrBoard.length; i++) {
            if (arrBoard[i] != null && arrBoard[i].getName().equals(boardName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardArrayFull() {
        for (Board board : arrBoard) {
            if (board == null) {
                return false;
            }
        }
        return true;
    }

    private void addBoardToArray(Board newBoard) {
        if (isBoardArrayFull()) {
            extendBoardsArray();
        }
        for (int i = 0; i < arrBoard.length; i++) {
            if (arrBoard[i] == null) {
                arrBoard[i] = newBoard;
                return;
            }
        }
    }

    private void extendBoardsArray() {
        Board[] newArr = new Board[arrBoard.length * 2];
        for (int i = 0; i < arrBoard.length; i++) {
            newArr[i] = arrBoard[i];
        }
        arrBoard = newArr;
    }

    private Lecture findLectureByName(String name) {
        for (Lecture lecture : arrLecture) {
            if (lecture != null && lecture.getName().equals(name)) {
                return lecture;
            }
        }
        return null;
    }

    private Board findBoardByName(String boardName) {
        for (int i = 0; i < arrBoard.length; i++) {
            if (arrBoard[i] != null && arrBoard[i].getName().equals(boardName)) {
                return arrBoard[i];
            }
        }
        return null;
    }

    public void addLectureToBoard() {
        System.out.println("Enter name of the board: ");
        String boardName = input.nextLine();

        Board board = findBoardByName(boardName);
        if (board == null) {
            System.out.println("Board " + boardName + " does not found.");
            return;
        }

        System.out.println("Enter name of the lecture to be added: ");
        String lectureName = input.nextLine();

        Lecture lecture = findLectureByName(lectureName);
        if (lecture == null) {
            System.out.println("Lecture " + lectureName + " does not exist.");
            return;
        }
        board.addLecture(lecture);
    }

    public void updateMan() {
        System.out.println("Enter name of the board: ");
        String boardName = input.nextLine();

        Board board = findBoardByName(boardName);

        if (board == null) {
            System.out.println("Board " + boardName + " does not exist.");
            return;
        }

        System.out.println("Enter name of the lecture to be updated: ");
        String lectureName = input.nextLine();
        Lecture toUpdateManager = findLectureByName(lectureName);

        if (toUpdateManager == null) {
            System.out.println("Lecture " + lectureName + " does not exist.");
        } else {
            board.setManagerBoard(toUpdateManager);
        }
    }

    public void printAllBoards() {
        for (Board board : arrBoard) {
            if (board != null) {
                System.out.println("Board name: " + board.getName());

                System.out.println("Name of the Manager: " + board.getManagerBoard().getName());

                System.out.print("Members: ");
                for (Lecture member : board.getLectures()) {
                    if (member != null && !member.getName().equals(board.getManagerBoard().getName())) {
                        System.out.print(String.join(" , ", member.getName()) + " ");
                    }
                }
                System.out.println();
            }
        }

    }

    public void removeFromBoard() {
        System.out.println("Enter name of the board: ");
        String boardName = input.nextLine();

        Board board = findBoardByName(boardName);

        if (board == null) {
            System.out.println("Board " + boardName + " does not exist.");
            return;
        }
        System.out.println("Enter the name you want to remove: ");
        String memberName = input.nextLine();
        if (findLectureByName(memberName) != null) {
            for (int i = 0; i < board.getLectures().length; i++) {
                if (board.getLectures()[i] != null && board.getLectures()[i].getName().equals(memberName)) {
                    board.getLectures()[i] = null;
                }
            }
            System.out.println(memberName + " deleted successfully");
        } else {
            System.out.println("Lecture " + memberName + " does not exist");
        }
    }

    public double getSalaryForAll(Department department) {
        // option 8
        int counter = 0;
        double sum = 0;
        if (department != null) {
            for (int i = 0; i < department.getNumOfLecture().length; i++) {
                if (department.getNumOfLecture()[i] != null) {
                    sum += department.getNumOfLecture()[i].getSalary();
                    counter++;
                }
            }
        } else { // option 7
            for (int i = 0; i < arrLecture.length; i++) {
                sum += arrLecture[i].getSalary();
                counter++;
            }
        }
        return sum / counter;
    }


    public Department findDepartment(String department) {
        for (int i = 0; i < arrDepartment.length; i++) {
            if (arrDepartment[i] != null && arrDepartment[i].getDepName().equals(department)) {
                return arrDepartment[i];
            }
        }
        return null;
    }

    public void lectureDetails(){
        for(Lecture lecture : arrLecture){
            if(lecture != null){
                System.out.println(lecture);
            }
        }
    }

    public void addDepartment(){
        System.out.println("Enter department name:");
        String depName = input.nextLine();

        if(findDepartment(depName)!=null){
            System.out.println("Department already exist");
            return;
        }

        System.out.println("Enter numbers of students:");
        int numStudents = input.nextInt();
        input.nextLine();

        Lecture[] lectures = new Lecture[1];
        Department newDep = new Department(depName,numStudents,lectures);

        if(OperationsOnArrays.isFullArray(arrDepartment)){
            arrDepartment = extendDepartmentArray(arrDepartment);
        }

        for (int i =0;i<arrDepartment.length;i++){
            if(arrDepartment[i] == null){
                arrDepartment[i] = newDep;
                break;
            }
        }
        System.out.println("Department "+depName + " added successfully");
    }


}