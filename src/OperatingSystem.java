
import java.util.Scanner;

public class OperatingSystem {
    private Scanner input = new Scanner(System.in);
    private Lecture[] arrLecture = new Lecture[2];
    private Department[] arrDepartment = new Department[2];
    private Board[] arrBoard = new Board[2];
    private int sizeArrayLecture = 0;
    private int sizeArrayDepartment = 0;
    private int sizeArrayBoard = 0;

    public void printWelcome() {
        System.out.println("Welcome to our system! 🎓");
        System.out.print("To get started, please enter the name of your college: ");
        input.nextLine();
    }

    public boolean addLecture(Lecture newLecture) {
        if (OperationsOnArrays.isFullArray(arrLecture)) {
            arrLecture = extendLectureArray(arrLecture);
        }

        addLectureToArray(newLecture);
        return true;
    }

    private void addLectureToArray(Lecture newLecture) {
        for (int i = 0; i < arrLecture.length; i++) {
            if (arrLecture[i] == null) {
                arrLecture[i] = newLecture;
                return;
            }
        }
    }

    public boolean existLecture(String id) {
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
        } else if (board.getManagerBoard().getName().equals(lectureName)) {
            System.out.println(lectureName + " is already the manager of this board.");
        } else {
            board.setManagerBoard(toUpdateManager);
        }
    }

    public void printAllBoards() {
        for (Board board : arrBoard) {
            if (board != null) {
                System.out.println("Board name: " + board.getName());

                if (board.getManagerBoard() != null) {
                    System.out.println("Name of the Manager: " + board.getManagerBoard().getName());
                } else {
                    System.out.println("No manager assigned.");
                }

                System.out.print("Members: ");
                for (Lecture member : board.getLectures()) {
                    if (member != null && !member.getName().equals(board.getManagerBoard().getName())) {
                        System.out.print(member.getName() + " , ");
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

        boolean found = false;
        for (int i = 0; i < board.getLectures().length; i++) {
            Lecture lecture = board.getLectures()[i];
            if (lecture != null && lecture.getName().equals(memberName)) {
                board.getLectures()[i] = null;
                Board[] boards = lecture.getBelongBoard();
                if (boards != null) {
                    for (int j = 0; j < boards.length; j++) {
                        if (boards[j] != null && boards[j].getName().equals(board.getName())) {
                            boards[j] = null;
                            break;
                        }
                    }
                }
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println(memberName + " deleted successfully");
        } else {
            System.out.println("Lecture " + memberName + " is not found in the board");
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
            for (Lecture lecture : arrLecture) {
                if(lecture != null){
                    sum += lecture.getSalary();
                    counter++;
                }
            }
        }
        return counter > 0 ? sum / counter : 0;
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

    public void addLectureToDepartment() {
        System.out.println("Enter name of the lecture to be added: ");
        String lectureName = input.nextLine();

        Lecture lecture = findLectureByName(lectureName);
        if (lecture == null) {
            System.out.println("Lecture " + lectureName + " does not exist.");
            return;
        }

        System.out.println("Enter department name:");
        String depName = input.nextLine();

        Department department = findDepartment(depName);
        if (department == null) {
            System.out.println("Department does not exist.");
            return;
        }

        if (lecture.isAssignedToDepartment()) {
            Department currentDep = lecture.getBelongDepartment();

            if (currentDep != null && currentDep.getDepName().equals(department.getDepName())) {
                System.out.println("Lecture is already assigned to this department.");
                return;
            }

            Lecture[] lecturers = currentDep != null ? currentDep.getNumOfLecture() : new Lecture[0];
            for (int i = 0; i < lecturers.length; i++) {
                if (lecturers[i] != null && lecturers[i].getId().equals(lecture.getId())) {
                    lecturers[i] = null;
                    break;
                }
            }
            lecture.setBelongDep(null);
            System.out.println("Lecture was removed from previous department: " + currentDep.getDepName());
        }

        boolean added = department.addLecturer(lecture);
        if (added) {
            System.out.println("Lecture successfully assigned to department: " + depName);
        } else {
            System.out.println("Failed to assign lecture to department.");
        }
    }
}