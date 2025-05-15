import Exceptions.AlreadyNameExistsException;
import Exceptions.ObjectNotFoundException;

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


    public void existBoard(String boardName) throws AlreadyNameExistsException {
        for (int i = 0; i < arrBoard.length; i++) {
            if (arrBoard[i] != null && arrBoard[i].getName().equals(boardName)) {
                throw new AlreadyNameExistsException("Board " + boardName + " already exists.");
            }
        }
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

    public Lecture findLectureByName(String lectureName) throws ObjectNotFoundException {
        for (int i = 0; i < arrLecture.length; i++) {
            if (arrLecture[i] != null && arrLecture[i].getName().equals(lectureName)) {
                return arrLecture[i];
            }
        }
        throw new ObjectNotFoundException("Lecture " + lectureName + " does not exist.");
    }

    Board findBoardByName(String boardName) throws ObjectNotFoundException {
        for (int i = 0; i < arrBoard.length; i++) {
            if (arrBoard[i] != null && arrBoard[i].getName().equals(boardName)) {
                return arrBoard[i];
            }
        }
        throw new ObjectNotFoundException("Board " + boardName + " does not exist.");
    }

    public Board[] getBoards() {
        return arrBoard;
    }

    public void removeFromBoard(Board board, String memberName) throws ObjectNotFoundException {
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
            throw new ObjectNotFoundException("Lecture " + memberName + " is not found in the board");
        }
    }

    public void removeFromDepartment(Department department, String lectureName) throws ObjectNotFoundException {
        Lecture[] lectures = department.getNumOfLecture();
        int logicalSize = department.getLogicalSize();

        boolean found = false;

        for (int i = 0; i < logicalSize; i++) {
            Lecture lecture = lectures[i];
            if (lecture != null && lecture.getName().equals(lectureName)) {
                found = true;

                department.shiftLeftFromIndexDepartment(i);
                department.setLogicalSize(logicalSize - 1);
                lecture.setDepartment(null);

                break;
            }
        }

        if (!found) {
            throw new ObjectNotFoundException("Lecture " + lectureName + " is not found in the department");
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
                if (lecture != null) {
                    sum += lecture.getSalary();
                    counter++;
                }
            }
        }
        return counter > 0 ? sum / counter : 0;
    }


    public Department findDepartment(String department) throws ObjectNotFoundException {
        for (int i = 0; i < arrDepartment.length; i++) {
            if (arrDepartment[i] != null && arrDepartment[i].getDepName().equals(department)) {
                return arrDepartment[i];
            }
        }
        throw new ObjectNotFoundException("Error: Department " + department + " does not exist.");
    }

    public Lecture[] getLectures() {
        return arrLecture;
    }


    public String addDepartment(String depName, int numStudents) throws AlreadyNameExistsException {
        for (int i = 0; i < arrDepartment.length; i++) {
            if (arrDepartment[i] != null && arrDepartment[i].getDepName().equals(depName)) {
                throw new AlreadyNameExistsException("Department with name " + depName + " already exists.");
            }
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


    public String addLectureToDepartment(String lectureName, String depName) throws ObjectNotFoundException {
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
}
