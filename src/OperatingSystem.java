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

        System.out.println("Enter Lecture degree: ");
        String degree = input.nextLine();

        System.out.println("Enter name of the degree: ");
        String nameDegree = input.nextLine();

        System.out.println("Enter Lecture salary: ");
        double salary = input.nextDouble();
        input.nextLine();

        Lecture newLecture = new Lecture(name, id, degree, nameDegree, salary);

        if (lectureIsFull()) {
            arrLecture = extendLectureArray(arrLecture);
        }
        addLectureToArray(newLecture);

        System.out.println("Enter Department name to assign this lecture: ");
        String depName = input.nextLine();

        Department dep = findOrCreateDepartment(depName);
        boolean added = dep.addLecturer(newLecture);

        if (added) {
            System.out.println("Lecture successfully assigned to department.");
        }
        else {
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

    private boolean lectureIsFull() {
        for (Lecture lecture : arrLecture) {
            if (lecture == null) {
                return false;
            }
        }
        return true;
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
        Department newDep = new Department(name, 0,newDepLectures);

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


    public void insertBoard(){
        System.out.println("Enter board name: ");
        String boardName = input.nextLine();
        if (existBoard(boardName)){
            System.out.println("Board already exists.");
            return;
        }
        System.out.println("Enter the name of the lecturer who will be the board manager:");
        String managerName = input.nextLine();

        Lecture manager = findLectureByName(managerName);

        if (manager == null) {
            System.out.println("That lecturer does not exist.");
            return;
        }
        if (!(manager.getDegree().equals("Dr") || manager.getDegree().equals("Prof"))) {
            System.out.println("The lecturer must be a Dr. or Prof. to be the board manager");
            return;
        }

        Board newBoard = new Board(boardName,manager,arrBoard);
        addBoardToArray(newBoard);
        
        System.out.println("Board" + boardName +"added successfully.");
    }

    private boolean existBoard(String boardName) {
        for (Board board : arrBoard) {
            if (boardName != null && board.getName().equals(boardName)) {
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
        if (isBoardArrayFull()){
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


    public void addLectureToBoard(){
        System.out.println("Enter the name of the board:");
        String boardName = input.nextLine();

        Board board = findBoardByName(boardName);
        if (board == null) {
            System.out.println("Board not found.");
            return;
        }

        System.out.println("Enter the name of the lecturer to add:");
        String lecturerName = input.nextLine();

        Lecture lecture = findLectureByName(lecturerName);
        if (lecture == null) {
            System.out.println("Lecture not found.");
            return;
        }


        boolean added = board.addMember(lecture);
        if (!added) {
            System.out.println("Lecture already exists.");
            return;
        }
        else{
            System.out.println("Lecture" + lecturerName + "added successfully to the board" + boardName + ".");
            return;
        }
    }

    private Board findBoardByName(String boardName) {
        for (Board board : arrBoard) {
            if (board.getName().equals(boardName)) {
                return board;
            }
        }
        return null;
    }


    public void printAllBoards(){
        for (int i=0; i<arrBoard.length; i++) {
            if (arrBoard[i] != null) {
                System.out.println(arrBoard[i].getDetails());
            }
        }
    }
}
