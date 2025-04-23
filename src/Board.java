import java.util.Arrays;
import java.util.Objects;

public class Board {
    private String name;
    private Lecture[] lectures;
    private Lecture managerBoard;
    private int logicalSize;


    public Board(String name,Lecture[] lectures,Lecture managerBoard) {
        setName(name);
        setLectures(lectures);
        this.managerBoard = managerBoard;
        logicalSize = 0;
    }

    public void addLecture(Lecture lecture) {
        if (managerBoard != null && lecture.getId().equals(managerBoard.getId())) {
            System.out.println("This lecture is already the manager of the board and cannot be added as a member.");
            return;
        }
        for (int i = 0; i < lectures.length; i++) {
            if (lectures[i] != null && lectures[i].getName().equals(lecture.getName())) {
                System.out.println("Lecture already exists.");
                return;
            }
        }
        if (OperationsOnArrays.isFullArray(lectures,logicalSize)) {
            expandLecturesArray();
        }
        addLectureToBoard(lecture);
        Board[] boards = lecture.getBelongBoard();
        if (boards == null) {
            boards = new Board[1];
            boards[0] = this;
            lecture.setBelongBoard(boards);
        } else {
            boolean added = false;
            for (int j = 0; j < boards.length; j++) {
                if (boards[j] == null) {
                    boards[j] = this;
                    added = true;
                    break;
                }
            }
            if (!added) {
                Board[] bigger = new Board[boards.length * 2];
                for (int j = 0; j < boards.length; j++) {
                    bigger[j] = boards[j];
                }
                bigger[boards.length] = this;
                lecture.setBelongBoard(bigger);
            }
        }
        System.out.println("lecture " + lecture.getName() + " added to the board.");
    }

    private void expandLecturesArray(){
        Lecture[] newArray = new Lecture[lectures.length*2];
        for ( int i = 0 ; i < lectures.length ; i++){
            newArray[i] = lectures[i];
        }
        lectures = newArray;
    }

    private void addLectureToBoard(Lecture lecture){
        lectures[logicalSize] = lecture;
        logicalSize++;
    }

    public void printBoardDetails(){
        System.out.println("Board name: " + this.name);

        if (managerBoard != null) {
            System.out.println("Name of the Manager: " + managerBoard.getName());
        } else {
            System.out.println("No Manager assigned");
        }

        System.out.print("Members: ");
        for (Lecture member : lectures) {
            if (member != null && !member.getName().equals(managerBoard.getName())) {
                System.out.print(member.getName() + " , ");
            }
        }
        System.out.println();
    }

    public int getLogicalSize() {
        return logicalSize;
    }

    public void setLogicalSize(int logicalSize) {
        this.logicalSize = logicalSize;
    }

    public void shiftLeftFromIndexBoard(int index){
        for (int i = index; i < logicalSize - 1; i++) {
            lectures[i] = lectures[i+1];
        }
        lectures[logicalSize-1] = null;
    }

    public String getName() {
        return name;
    }

    public Lecture[] getLectures() {
        return lectures;
    }

    public Lecture getManagerBoard() {
        return managerBoard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLectures(Lecture[] lectures) {
        this.lectures = lectures;
    }

    public void setManagerBoard(Lecture managerBoard) {
        if ((managerBoard.checkIsValidManager())) {
            System.out.println(managerBoard.getName() + " updated to be the manager board");
            this.managerBoard = managerBoard;
        }
        else{
        System.out.println("The lecturer must be a Dr. or Prof. to be the board manager");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.deepEquals(lectures, board.lectures);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(lectures);
    }
}
