import java.util.Scanner;

public class OperatingSystem {
    private Scanner input = new Scanner(System.in);
    private static Lecture[] arrLecture = new Lecture[1];
    private static Department[] arrDepartment = new Department[1];

    public void printWelcome() {
        System.out.println("Welcome to our system! 🎓");
        System.out.print("To get started, please enter the name of your college: ");
        input.nextLine();
    }

    public void insertLectureDetails() {
        System.out.println("Enter Lecture name: ");
        String name = input.nextLine();

        System.out.println("Enter Lecture ID: ");
        String id = input.nextLine();

        if (existLecture(id)) {
            System.out.println("Lecture with this ID already exists.");
            return;
        }

        System.out.println("Enter Lecture degree: ");
        String degree = input.nextLine();

        System.out.println("Enter name of the degree: ");
        String nameDegree = input.nextLine();

        System.out.println("Enter Lecture salary: ");
        double salary = input.nextDouble();
        input.nextLine();

        Lecture newLecture = new Lecture(name, id, degree, nameDegree, salary);

        if (lectureIsFull()) {
            arrLecture = extendLectureArray(arrLecture);
        }
        addLectureToArray(newLecture);

        System.out.println("Enter Department name to assign this lecture: ");
        String depName = input.nextLine();

        Department dep = findOrCreateDepartment(depName);
        boolean added = dep.addLecturer(newLecture);

        if (added) {
            System.out.println("Lecture successfully assigned to department.");
        }
        else {
            System.out.println("Failed to assign lecture to department.");
        }
    }

    private void addLectureToArray(Lecture newLecture) {
        for (int i = 0; i < arrLecture.length; i++) {
            if (arrLecture[i] == null) {
                arrLecture[i] = newLecture;
                return;
            }
        }
    }

    private boolean lectureIsFull() {
        for (Lecture lecture : arrLecture) {
            if (lecture == null) {
                return false;
            }
        }
        return true;
    }

    private boolean existLecture(String id) {
        for (Lecture lecture : arrLecture) {
            if (lecture != null && lecture.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private Lecture[] extendLectureArray(Lecture[] oldArr) {
        Lecture[] newArr = new Lecture[oldArr.length * 2];
        for (int i = 0; i < oldArr.length; i++) {
            newArr[i] = oldArr[i];
        }
        return newArr;
    }

    private Department findOrCreateDepartment(String name) {
        for (Department dep : arrDepartment) {
            if (dep != null && dep.getDepName().equals(name)) {
                return dep;
            }
        }
        Lecture[] newDepLectures = new Lecture[5];
        Department newDep = new Department(name, 0,newDepLectures);

        for (int i = 0; i < arrDepartment.length; i++) {
            if (arrDepartment[i] == null) {
                arrDepartment[i] = newDep;
                return newDep;
            }
        }

        arrDepartment = extendDepartmentArray(arrDepartment);
        arrDepartment[arrDepartment.length / 2] = newDep;
        return newDep;
    }

    private Department[] extendDepartmentArray(Department[] oldArr) {
        Department[] newArr = new Department[oldArr.length * 2];
        for (int i = 0; i < oldArr.length; i++) {
            newArr[i] = oldArr[i];
        }
        return newArr;
    }
}
