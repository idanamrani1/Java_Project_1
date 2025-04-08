import java.util.Scanner;

public class OperatingSystem {
    private Scanner input = new Scanner(System.in);
    private String[] arrLecture = new String[1];
    private String[] arrBoard = new String[1];
    private String[] arrStudyDepartment = new String[1];
    private int lectureSize = 0;
    private int arrBoardSize = 0;
    private int arrStudyDepartmentSize = 0;

    public void printWelcome() {
        System.out.println("Welcome to our system! 🎓");
        System.out.print("To get started, please enter the name of your college: ");
        input.nextLine();
    }

    public Lecture addLecture() {
        System.out.println("Enter Lecture name: ");
        String name = input.nextLine();
        System.out.println("Enter Lecture id: ");
        String id = input.nextLine();
        System.out.println("Enter Lecture degree (First/Second/Doctor/Professor): ");
        String degree = input.nextLine();
        System.out.println("Enter name of your degree:");
        String nameDegree = input.nextLine();
        System.out.println("Enter Lecture department: ");
        String department = input.nextLine();
        System.out.println("Enter Lecture salary: ");
        double salary = input.nextDouble();
        input.nextLine();

        if (existArr(arrLecture, name)) {
            if (lectureSize >= arrLecture.length) {
                arrLecture = extendArr(arrLecture, lectureSize);
            }
            arrLecture[lectureSize] = name;
            lectureSize++;
        } else {
            System.out.println("Lecture already exists.");
        }
        return new Lecture(name, id, degree,nameDegree, department, salary);
    }

    public Board addBoard(){
        System.out.println("Enter board name:");
        String name = input.nextLine();
        System.out.println("Enter Board manager:");
        String boardManager = input.nextLine();

        if()
    }

    public  String[] addArrayName(String[] arr,int size,String name) {
        String newName = name;
        existArr(arr, newName);
        if (existArr(arr, newName)) {
            if (size >= arr.length) {
                arr = extendArr(arr, size);
                arr[size] = newName;
            } else {
                arr[size] = newName;
            }
        }
        else {
            arr = addArrayName(arr,size,name);
        }
        return arr;
    }

    public  boolean existArr(String[] arr,String name) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null){
                if (arr[i].equals(name)) {
                    return false;
                }
            }}
        return true;
    }

    public  String[] extendArr(String[] oldArr ,int size) {
        String[] newArr = new String[size * 2];
        copyArr(newArr,oldArr);
        return newArr;
    }

    public  void copyArr(String[] newArr,String[] oldArr){
        for (int i = 0 ; i < oldArr.length; i++) {
            newArr[i] = oldArr[i];
        }
    }

    public  void printArray(String[] arr){
        for (int i = 0; i <arr.length;i++){
            if (arr[i] == null){
                continue;}
            System.out.print(arr[i] + "  ");
        }
        System.out.println();
    }
}
