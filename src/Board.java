import Exceptions.AlreadyManagerException;
import Exceptions.AlreadyMemberException;

import java.util.Arrays;
import java.util.Objects;

public class Board implements expandArray, Cloneable {
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


    public void addLecture(Lecture lecture) throws AlreadyMemberException, AlreadyManagerException {
        if (managerBoard != null && lecture.getName().equals(managerBoard.getName())) {
            throw new AlreadyManagerException("This lecture is already the manager of the board and cannot be added as a member.");
        }

        for (int i = 0; i < lectures.length; i++) {
            if (lectures[i] != null && lectures[i].getName().equals(lecture.getName())) {
                throw new AlreadyMemberException("Lecture already exists in the board.");
            }
        }

        if (OperatingSystem.isFullArray(lectures, logicalSize)) {
            expandable();
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
    }


    @Override
    public void expandable() {
        Lecture[] newArray = new Lecture[lectures.length*2];
        for ( int i = 0 ; i < lectures.length ; i++){
            newArray[i] = lectures[i];
        }
        lectures = newArray;
    }


    private void addLectureToBoard(Lecture lecture){
        lectures[logicalSize] = lecture;
        this.logicalSize++;
    }


    public int getLogicalSize() {
        return this.logicalSize;
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
        this.managerBoard = managerBoard;
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

    @Override
    public Board clone() throws CloneNotSupportedException {
        try {
            Board copy = (Board) super.clone();
            copy.name = this.name + "-new";
            copy.managerBoard = this.managerBoard;

            if (this.lectures != null) {
                copy.lectures = new Lecture[this.lectures.length];
                for (int i=0;i<this.lectures.length;i++){
                    copy.lectures[i] = this.lectures[i];
                }
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone failed");
        }

    }
}
