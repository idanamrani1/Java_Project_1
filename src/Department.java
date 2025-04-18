public class Department {
    private String depName;
    private int numOfStudents;
    private Lecture[] numOfLecture;

    public Department(String depName,int numOfStudents,Lecture[] numOfLecture) {
        setDepName(depName);
        setNumOfLecture(numOfLecture);
        setNumOfStudents(numOfStudents);
    }

    public boolean addLecturer(Lecture lecture) {
        if (lecture.isAssignedToDepartment()) {
            System.out.println("Lecture is already assigned to a department.");
            return false;
        }

        if (OperationsOnArrays.isFullArray(numOfLecture)) {
            expandLecturerArray();
        }

        for (int i = 0; i < numOfLecture.length; i++) {
            if (numOfLecture[i] == null) {
                numOfLecture[i] = lecture;
                lecture.setDepartment(this);
                return true;
            }
        }

        System.out.println("Failed to add lecturer.");
        return false;
    }

    private void expandLecturerArray() {
        Lecture[] bigger = new Lecture[numOfLecture.length * 2];
        for (int i = 0; i < numOfLecture.length; i++) {
            bigger[i] = numOfLecture[i];
        }
        numOfLecture = bigger;
        System.out.println("Lecturer array expanded to size: " + numOfLecture.length);
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
