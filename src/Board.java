public class Board {
    private String name;
    private Lecture[] lectures;
    private Lecture managerBoard;

    public Board(String name,Lecture managerBoard) {
        setName(name);
        setManagerBoard(managerBoard);
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
}
