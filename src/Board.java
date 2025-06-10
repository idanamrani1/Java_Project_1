import Exceptions.AlreadyManagerException;
import Exceptions.AlreadyMemberException;

import java.util.ArrayList;
import java.util.Objects;

public class Board<T extends Lecture> implements Cloneable {
    private String name;
    private ArrayList<T> lectures;
    private Lecture managerBoard;
    private Degree committeeDegree;

    public Board(String name, Lecture managerBoard, Degree degree) {
        this.name = name;
        this.managerBoard = managerBoard;
        this.committeeDegree = degree;
        this.lectures = new ArrayList<>();
    }

    public int getNumOfArticles() {
        int sum = 0;
        for (T lecture : lectures) {
            if (lecture != null && (lecture.getDegree() == Degree.DR || lecture.getDegree() == Degree.PROFESSOR)) {
                sum += ((Doctor) lecture).getNumberOfArticles();
            }
        }
        return sum;
    }

    public void addLecture(T lecture) throws AlreadyMemberException, AlreadyManagerException {
        if (!lecture.getDegree().equals(committeeDegree)) {
            throw new IllegalArgumentException("Lecture degree does not match the committee's degree.");
        }

        if (managerBoard != null && lecture.getName().equals(managerBoard.getName())) {
            throw new AlreadyManagerException("This lecture is already the manager of the board and cannot be added as a member.");
        }

        for (T l : lectures) {
            if (l.getName().equals(lecture.getName())) {
                throw new AlreadyMemberException("Lecture already exists in the board.");
            }
        }

        lectures.add(lecture);

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
                System.arraycopy(boards, 0, bigger, 0, boards.length);
                bigger[boards.length] = this;
                lecture.setBelongBoard(bigger);
            }
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<T> getLectures() {
        return lectures;
    }

    public Lecture getManagerBoard() {
        return managerBoard;
    }

    public Degree getCommitteeDegree() {
        return committeeDegree;
    }

    public int getLogicalSize() {
        return lectures.size();
    }

    public void setManagerBoard(T managerBoard) {
        this.managerBoard = managerBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board<?> board = (Board<?>) o;
        return Objects.equals(lectures, board.lectures);
    }

    @Override
    public Board<T> clone() {
        Board<T> copy = new Board<>(this.name + " -copy", this.managerBoard, this.committeeDegree);
        copy.lectures = new ArrayList<>(this.lectures);
        return copy;
    }

    @Override
    public String toString() {
        return name;
    }
}
