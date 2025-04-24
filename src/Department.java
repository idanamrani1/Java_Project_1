public class Department {
    private String depName;
    private int numOfStudents;
    private Lecture[] numOfLecture;

    public Department(String depName,int numOfStudents,Lecture[] numOfLecture) {
        setDepName(depName);
        setNumOfLecture(numOfLecture);
        setNumOfStudents(numOfStudents);
    }

    private boolean isFullArray(Object[] array) {
        for (Object obj : array) {
            if (obj == null) return false;
        }
        return true;
    }


    public String addLecturer(Lecture lecture) {
        if (lecture.isAssignedToDepartment()) {
            return "Lecture is already assigned to a department.";
        }

        if (isFullArray(numOfLecture)) {
            expandLecturerArray();
        }

        for (int i = 0; i < numOfLecture.length; i++) {
            if (numOfLecture[i] == null) {
                numOfLecture[i] = lecture;
                lecture.setDepartment(this);
                return "Lecture added successfully to department " + depName;            }
        }
        return "Failed to add lecturer.";
    }

    private void expandLecturerArray() {
        Lecture[] bigger = new Lecture[numOfLecture.length * 2];
        for (int i = 0; i < numOfLecture.length; i++) {
            bigger[i] = numOfLecture[i];
        }
        numOfLecture = bigger;
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
