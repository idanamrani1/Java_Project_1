import Exceptions.AlreadyManagerException;
import Exceptions.AlreadyMemberException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Board<T extends Lecture> implements Cloneable, Serializable {
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
        lecture.addBoard(this);

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
