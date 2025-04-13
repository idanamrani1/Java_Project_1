public class Board {
    private String name;
    private Lecture[] lectures;
    private Lecture managerBoard;


    public Board(String name,Lecture[] lectures,Lecture managerBoard) {
        setName(name);
        setLectures(lectures);
        //setManagerBoard(managerBoard); - אין צורך בה
        this.managerBoard = managerBoard;
    }

    public void CreateMangaer(){

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
        if (isFullArray()){
            expandLecturesArray();
        }
        addLectureToBoard(lecture);

        System.out.println("lecture " + lecture.getName() + " added to the board.");
    }

    private boolean isFullArray(){
        for ( int i = 0 ; i < lectures.length ; i++){
            if (lectures[i] == null){
                return false;
            }
        }
        return true;
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
        if (!(managerBoard.checkIsValidManager())) {
            System.out.println("The lecturer must be a Dr. or Prof. to be the board manager");
            return;
        }

        System.out.println("Wrote new lecture manager!");
        this.managerBoard = managerBoard;
    }
}
