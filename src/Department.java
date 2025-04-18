public class Department {
    private String depName;
    private int numOfStudents;
    private Lecture[] numOfLecture;

    public Department(String depName,int numOfStudents,Lecture[] numOfLecture) {
        setDepName(depName);
        setNumOfLecture(numOfLecture);
        setNumOfStudents(numOfStudents);
    }

    public boolean addLecturer(Lecture lecture){
        if(lecture.isAssignedToDepartment()){
            System.out.println("already assigned to department");
            return false;
        }
        for (int i = 0; i < numOfLecture.length; i++) {
            if (numOfLecture[i] == null) {
                numOfLecture[i] = lecture;
                lecture.setDepartment(this);
                return true;
            }
        }
        System.out.println("department is full");
        return false;
    }

    public String getDepName() {
        return depName;
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }

    public Lecture[] getNumOfLecture() {
        return numOfLecture;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setNumOfStudents(int numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    public void setNumOfLecture(Lecture[] numOfLecture) {
        this.numOfLecture = numOfLecture;
    }

    public String toString(){
        return depName;
    }
}
