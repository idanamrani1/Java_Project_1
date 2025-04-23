import java.util.Objects;

public class Lecture {
    private String name;
    private String id;
    private Degree degree;
    private String nameDegree;
    private double salary;
    private Board[] belongBoard;
    private Department belongDepartment;


    public void setDepartment(Department department) {
        if (this.belongDepartment != null) {
            System.out.println("Lecture is already assigned to a department!");
        } else {
            this.belongDepartment = department;
        }
    }

    public boolean isAssignedToDepartment() {
        return belongDepartment != null;
    }

    public Lecture(String name, String id, Degree degree, String nameDegree, double salary) {
        setName(name);
        setId(id);
        setDegree(degree);
        setNameDegree(nameDegree);
        setSalary(salary);
        this.belongBoard = null;
    }

    public Board[] getBelongBoard() {
        return belongBoard;
    }

    public void setBelongBoard(Board[] belongBoard) {
        this.belongBoard = belongBoard;
    }

    public String getName() {
        return name;
    }


    public double getSalary() {
        return salary;
    }


    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public void setNameDegree(String nameDegree) {
        this.nameDegree = nameDegree;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setBelongDep(Department belongDepartment) {
        this.belongDepartment = belongDepartment;
    }


    public boolean checkIsValidManager() {
        return (this.degree == Degree.DR || this.degree == Degree.PROFESSOR);
    }
    public Department getBelongDepartment() {
        return this.belongDepartment;
    }




    @Override
    public String toString() {
        return "Name: " + name +
                ", ID: " + id +
                ", Degree: " + degree +
                ", Salary: " + salary +
                ", Departments: " + belongDepartment +
                ", Boards: " + lectureBoardDetails();

    }
        public String lectureBoardDetails(){
            if (belongBoard == null) {
                return "None";
            }
            String details = "";
            for (int i = 0; i < belongBoard.length; i++) {
                if (belongBoard[i] != null) {
                    details += belongBoard[i].getName() + " ";
                }
            }
            if(details.equals("")) {
                return "None";
            }
            return details;
        }
    }
