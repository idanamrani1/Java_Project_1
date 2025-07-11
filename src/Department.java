import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
    private String depName;
    private int numOfStudents;
    private ArrayList<Lecture> numOfLecture;

    public Department(String depName, int numOfStudents) {
        setDepName(depName);
        setNumOfStudents(numOfStudents);
        this.numOfLecture = new ArrayList<>();
    }

    public String addLecturer(Lecture lecture) {
        if (lecture.isAssignedToDepartment()) {
            return "Lecture " + lecture.getName() + " is already assigned to a department.";
        }
        numOfLecture.add(lecture);
        lecture.setDepartment(this);
        return "Lecture " + lecture.getName() + " added successfully to department " + depName;
    }

    public void removeLecture(Lecture lecture) {
        for (int i = 0; i < numOfLecture.size(); i++) {
            if (numOfLecture.get(i).getName().equals(lecture.getName())) {
                numOfLecture.remove(i);
                break;
            }
        }
    }

    public String getDepName() {
        return depName;
    }

    public ArrayList<Lecture> getNumOfLecture() {
        return numOfLecture;
    }
    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setNumOfStudents(int numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    public String toString() {
        return depName;
    }
}
