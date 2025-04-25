public class Department {
    private String depName;
    private int numOfStudents;
    private Lecture[] numOfLecture;
    private int logicalSize;

    public Department(String depName,int numOfStudents,Lecture[] numOfLecture) {
        setDepName(depName);
        setNumOfLecture(numOfLecture);
        setNumOfStudents(numOfStudents);
        logicalSize = 0;
    }

    private boolean isFullArray(Object[] array) {
        for (Object obj : array) {
            if (obj == null) return false;
        }
        return true;
    }


    public String addLecturer(Lecture lecture) {
        if (lecture.isAssignedToDepartment()) {
            return "Lecture " + lecture.getName() + " is already assigned to a department.";
        }

        if (OperatingSystem.isFullArray(numOfLecture,logicalSize)) {
            expandLecturerArray();
        }
        numOfLecture[logicalSize] = lecture;
        lecture.setDepartment(this);
        this.logicalSize++;
        return "Lecture " + lecture.getName() +" added successfully to department " + depName;
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

    public int getLogicalSize() { return logicalSize;}

    public int getNumOfStudents() {
        return numOfStudents;
    }

    public Lecture[] getNumOfLecture() {
        return numOfLecture;
    }

    public void setLogicalSize(int logicalSize) {
        this.logicalSize = logicalSize;
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

    public void shiftLeftFromIndexDepartment(int index){
        for (int i = index; i < logicalSize - 1; i++) {
            numOfLecture[i] = numOfLecture[i+1];
        }
        numOfLecture[logicalSize-1] = null;
        this.logicalSize--;
        System.out.println(numOfStudents);
    }

    public String toString(){
        return depName;
    }
}
