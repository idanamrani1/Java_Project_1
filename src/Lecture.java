import java.util.Scanner;

public class Lecture {
    private Scanner input = new Scanner(System.in);
    private String name;
    private String id;
    private String degree;
    private String nameDegree;
    private double salary;
    private String belongDep;
    private Department department;

    public void setDepartment(Department department) {
        if (this.department != null) {
            System.out.println("Lecture is already assigned to a department!");
        } else {
            this.department = department;
        }
    }
    public boolean isAssignedToDepartment() {
        return department != null;
    }
    public Lecture(String name,String id,String degree,String nameDegree,String belongDep,double salary) {
        setName(name);
        setId(id);
        setDegree(degree);
        setNameDegree(nameDegree);
        setSalary(salary);
        setBelongDep(belongDep);
    }


    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public String getNameDegree() {
        return nameDegree;
    }

    public double getSalary() {
        return salary;
    }

    public String getBelongDep() {
        return belongDep;
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

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setNameDegree(String nameDegree) {
        this.nameDegree = nameDegree;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setBelongDep(String belongDep) {
        this.belongDep = belongDep;
    }


}
