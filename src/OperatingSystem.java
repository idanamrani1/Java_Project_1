import java.util.ArrayList;

public class OperatingSystem {
    private ArrayList<Lecture> lectures = new ArrayList<>();
    private ArrayList<Department> departments = new ArrayList<>();
    private ArrayList<Board<?>> boards = new ArrayList<>();

    public boolean addLecture(Lecture newLecture) {
        lectures.add(newLecture);
        return true;
    }

    public boolean existLecture(String name) {
        for (int i = 0; i < lectures.size(); i++) {
            if (lectures.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean existBoard(String boardName) {
        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i).getName().equals(boardName)) {
                return true;
            }
        }
        return false;
    }

    public void addBoardToArray(Board<?> newBoard) {
        boards.add(newBoard);
    }

    public Lecture findLectureByName(String name) {
        for (int i = 0; i < lectures.size(); i++) {
            if (lectures.get(i).getName().equals(name)) {
                return lectures.get(i);
            }
        }
        return null;
    }

    public Board<?> findBoardByName(String boardName) {
        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i).getName().equals(boardName)) {
                return boards.get(i);
            }
        }
        return null;
    }

    public ArrayList<Board<?>> getBoards() {
        return boards;
    }

    public String removeFromBoard(Board<?> board, String memberName) {
        ArrayList<? extends Lecture> boardLectures = board.getLectures();
        for (int i = 0; i < boardLectures.size(); i++) {
            Lecture lecture = boardLectures.get(i);
            if (lecture.getName().equals(memberName)) {
                boardLectures.remove(i);

                Board[] lectureBoards = lecture.getBelongBoard();
                if (lectureBoards != null) {
                    for (int j = 0; j < lectureBoards.length; j++) {
                        if (lectureBoards[j] != null && lectureBoards[j].getName().equals(board.getName())) {
                            lectureBoards[j] = null;
                            break;
                        }
                    }
                }

                return memberName + " deleted successfully from the Board: " + board;
            }
        }

        return "Lecture " + memberName + " is not found in the board";
    }

    public double getSalaryForAll(Department department) {
        double sum = 0;
        int count = 0;

        if (department != null) {
            ArrayList<Lecture> deptLectures = department.getNumOfLecture();
            for (int i = 0; i < deptLectures.size(); i++) {
                sum += deptLectures.get(i).getSalary();
                count++;
            }
        } else {
            for (int i = 0; i < lectures.size(); i++) {
                sum += lectures.get(i).getSalary();
                count++;
            }
        }

        if (count > 0) {
            return sum / count;
        }
        return 0;
    }

    public Department findDepartment(String departmentName) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepName().equals(departmentName)) {
                return departments.get(i);
            }
        }
        return null;
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public String addDepartment(String depName, int numStudents) {
        if (findDepartment(depName) != null) {
            return "Department already exists.";
        }

        Department newDep = new Department(depName, numStudents);
        departments.add(newDep);
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

            currentDep.removeLecture(lecture);
            lecture.setBelongDep(null);
        }

        return department.addLecturer(lecture);
    }

    public String compareBoardByArticles(String b1, String b2) {
        Board<?> board1 = findBoardByName(b1);
        Board<?> board2 = findBoardByName(b2);
        int res = new CompareByArticles().compare(board1, board2);

        if (res == 0) {
            return "Both boards have the same number of articles.";
        }
        return res > 0 ? b1 : b2;
    }

    public String compareBoardByLec(String b1, String b2) {
        Board<?> board1 = findBoardByName(b1);
        Board<?> board2 = findBoardByName(b2);
        int res = board1.getLogicalSize() - board2.getLogicalSize();

        if (res == 0) {
            return "Both boards have the same number of lectures.";
        }
        return res > 0 ? b1 : b2;
    }
}
