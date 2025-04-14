import java.util.Objects;

public class Lecture {
    private String name;
    private String id;
    private String degree;
    private String nameDegree;
    private double salary;
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

    public Lecture(String name, String id, String degree, String nameDegree, double salary) {
        setName(name);
        setId(id);
        setDegree(degree);
        setNameDegree(nameDegree);
        setSalary(salary);
        setBelongDep(belongDepartment);
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

    public void setBelongDep(Department belongDepartment) {
        this.belongDepartment = null;
    }

    public boolean checkIsValidManager() {
        if (((this.degree.equals("Dr")) || (this.degree.equals("Prof")))) {
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public String toString() {
        return name;
    }

}
