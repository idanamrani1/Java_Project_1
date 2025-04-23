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

    public boolean addLecturer(Lecture lecture) {
        if (lecture.isAssignedToDepartment()) {
            System.out.println("Lecture is already assigned to a department.");
            return false;
        }


        if (OperationsOnArrays.isFullArray(numOfLecture,logicalSize)) {
            expandLecturerArray();
        }

        numOfLecture[logicalSize] = lecture;
        lecture.setDepartment(this);
        logicalSize++;
                /*
                this- הינה מתייחסת לאובייקט הנוכחי
                אז אנחנו שולחים את המחלקה לאובייקט מרצה,
                זאת אומר, אנחנו שייכים למחלקת לימוד מסויימת,
                ואנחנו רוצים לעדכן שהמרצה (שהוא האובייקט) שהמחלקה שלו היא המחלקה הנתונהת
                לכן רשום הthis בפנים.
                 */
        return true;
    }

    private void expandLecturerArray() {
        Lecture[] bigger = new Lecture[numOfLecture.length * 2];
        for (int i = 0; i < numOfLecture.length; i++) {
            bigger[i] = numOfLecture[i];
        }
        numOfLecture = bigger;
    }

    public int getLogicalSize() {
        return logicalSize;
    }

    public void setLogicalSize(int logicalSize) {
        this.logicalSize = logicalSize;
    }

    public void shiftLeftFromIndexDepartment(int index){
        for (int i = index; i < logicalSize - 1; i++) {
            numOfLecture[i] = numOfLecture[i+1];
        }
        numOfLecture[logicalSize-1] = null;
        logicalSize--;
        System.out.println(numOfStudents);
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
