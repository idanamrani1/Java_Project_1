import java.util.Arrays;
import java.util.Objects;

public class Board {
    private String name;
    private Lecture[] lectures;
    private Lecture managerBoard;


    public Board(String name,Lecture[] lectures,Lecture managerBoard) {
        setName(name);
        setLectures(lectures);
        this.managerBoard = managerBoard;
    }

    public void addLecture(Lecture lecture){
        if (lecture.getName().equals(managerBoard.getName())) {
            System.out.println("Can't add the lecture. He is the board manager");
            return ;
        }
        for (int i = 0; i < lectures.length; i++) {
            if (lectures[i] != null && lectures[i].getName().equals(lecture.getName())) {
                System.out.println("Lecture already exists.");
                return ;
            }
        }
        if (OperationsOnArrays.isFullArray(lectures)){
            expandLecturesArray();
        }
        addLectureToBoard(lecture);
        Board[] boards = lecture.getBelongBoard();
        if(boards == null){
            boards = new Board[1];
            boards[0] = this;
            lecture.setBelongBoard(boards);
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
        for ( int i = 0 ; i < lectures.length ; i++){
            if (lectures[i] == null){
                lectures[i] = lecture;
                break;
            }
        }
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
        System.out.println("An error occurred while updating the manager board");
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
