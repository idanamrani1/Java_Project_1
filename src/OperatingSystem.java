
public class OperatingSystem {
    private Lecture[] arrLecture = new Lecture[1];
    private Department[] arrDepartment = new Department[1];
    private Board[] arrBoard = new Board[1];
    private int logicalSizeArrLecture = 0;
    private int logicalSizeArrDepartment = 0;


    public boolean addLecture(Lecture newLecture) {
        if (isFullArray(arrLecture, logicalSizeArrLecture)) {
            arrLecture = extendLectureArray(arrLecture);
        }
        addLectureToArray(newLecture);
        return true;
    }

    public static boolean isFullArray(Object[] Array, int logicalSize) {
        return logicalSize == Array.length;
    }

    private void addLectureToArray(Lecture newLecture) {
        arrLecture[logicalSizeArrLecture] = newLecture;
        this.logicalSizeArrLecture++;
    }

    public boolean existLecture(String name) {
        for (Lecture lecture : arrLecture) {
            if (lecture != null && lecture.getName().equals(name)) {
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


    boolean existBoard(String boardName) {
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

    void addBoardToArray(Board newBoard) {
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

    Lecture findLectureByName(String name) {
        for (Lecture lecture : arrLecture) {
            if (lecture != null && lecture.getName().equals(name)) {
                return lecture;
            }
        }
        return null;
    }

    Board findBoardByName(String boardName) {
        for (int i = 0; i < arrBoard.length; i++) {
            if (arrBoard[i] != null && arrBoard[i].getName().equals(boardName)) {
                return arrBoard[i];
            }
        }
        return null;
    }

    public Board[] getBoards() {
        return arrBoard;
    }

    public String removeFromBoard(Board board, String memberName) {

        Lecture[] lectures = board.getLectures();
        int logicalSize = board.getLogicalSize();

        boolean found = false;
        for (int i = 0; i < logicalSize; i++) {
            Lecture lecture = lectures[i];
            if (lecture != null && lecture.getName().equals(memberName)) {
                found = true;

                board.shiftLeftFromIndexBoard(i);
                board.setLogicalSize(logicalSize - 1);

                Board[] boards = lecture.getBelongBoard();
                if (boards != null) {
                    for (int j = 0; j < boards.length; j++) {
                        if (boards[j] != null && boards[j].getName().equals(board.getName())) {
                            boards[j] = null;
                            break;
                        }
                    }
                }

                break;
            }
        }

        if (!found) {
            return "Lecture " + memberName + " is not found in the board";
        }

        return memberName + " deleted successfully from the Board: " + board;
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
                if (lecture != null) {
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

    public Lecture[] getLectures() {
        return arrLecture;
    }


    public String addDepartment(String depName, int numStudents) {
        if (findDepartment(depName) != null) {
            return "Department already exists.";
        }

        Lecture[] lectures = new Lecture[1];
        Department newDep = new Department(depName, numStudents, lectures);

        if (isFullArray(arrDepartment, logicalSizeArrDepartment)) {
            arrDepartment = extendDepartmentArray(arrDepartment);
        }

        arrDepartment[logicalSizeArrDepartment] = newDep;
        this.logicalSizeArrDepartment++;
        return "Department " + depName + " added successfully";
    }


    public String addLectureToDepartment(String lectureName, String depName) {
        Lecture lecture = findLectureByName(lectureName);
        if (lecture == null) {
            return "Lecture " + lectureName + " does not exist.";
        }

        Department department = findDepartment(depName);
        if (department == null) {
            return "Department " + depName + " does not exist.";
        }

        if (lecture.isAssignedToDepartment()) {
            Department currentDep = lecture.getBelongDepartment();
            if (currentDep != null && currentDep.getDepName().equals(department.getDepName())) {
                return "Lecture is already assigned to this department.";
            }

            Lecture[] lecturers = currentDep != null ? currentDep.getNumOfLecture() : new Lecture[0];
            for (int i = 0; i < lecturers.length; i++) {
                if (lecturers[i] != null && lecturers[i].getName().equals(lecture.getName())) {
                    currentDep.shiftLeftFromIndexDepartment(i);
                    break;
                }
            }

            lecture.setBelongDep(null);
            department.addLecturer(lecture);
            return "Lecture has been moved from " + currentDep.getDepName() + " to " + depName + ".\n";
        }
        return department.addLecturer(lecture);
    }

    public String compareBoardByArticles(String b1, String b2) {
        Board board1 = findBoardByName(b1);
        Board board2 = findBoardByName(b2);
        int res = new CompareByArticles().compare(board1, board2);

        if (res == 0) {
            return "Both boards have the same number of articles.";
        }
        return res > 0 ? b1 : b2;
    }


    public String compareBoardByLec(String boName1, String boName2) {
        Board board1 = findBoardByName(boName1);
        Board board2 = findBoardByName(boName2);
        int res = board1.getLogicalSize() - board2.getLogicalSize();

        if (res == 0) {
            return "Both boards have the same number of lectures.";
        }
        return res > 0 ? boName1 : boName2;
    }


}
