import Exceptions.InvalidManagerException;

import java.io.Serializable;
import java.util.ArrayList;

public class Lecture implements Serializable {
    private String name;
    private String id;
    private Degree degree;
    private String nameDegree;
    private double salary;
    private ArrayList<Board<? extends Lecture>> belongBoards;
    private Department belongDepartment;

    public Lecture(String name, String id, Degree degree, String nameDegree, double salary) {
        setName(name);
        setId(id);
        setDegree(degree);
        setNameDegree(nameDegree);
        setSalary(salary);
        this.belongBoards = new ArrayList<>();
    }

    public String setDepartment(Department department) {
        if (this.belongDepartment != null) {
            return "Lecture is already assigned to a department!";
        } else {
            this.belongDepartment = department;
            return "Lecture assigned to department successfully.";
        }
    }

    public boolean isAssignedToDepartment() {
        return belongDepartment != null;
    }

    public ArrayList<Board<? extends Lecture>> getBelongBoards() {
        return belongBoards;
    }

    public void addBoard(Board<? extends Lecture> board) {
        if (board != null) {
            for (int i = 0; i < belongBoards.size(); i++) {
                if (belongBoards.get(i).getName().equals(board.getName())) {
                    return;
                }
            }
            belongBoards.add(board);
        }
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

    public Degree getDegree() {
        return degree;
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

    public Department getBelongDepartment() {
        return this.belongDepartment;
    }

    public void checkIsValidManager() throws InvalidManagerException {
        if (!(this.degree == Degree.DR || this.degree == Degree.PROFESSOR)) {
            throw new InvalidManagerException("The lecturer must be a Dr. or Prof. to be the board manager.");
        }
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", ID: " + id +
                ", Degree: " + degree +
                ", Salary: " + salary +
                ", Departments: " + (belongDepartment != null ? belongDepartment.getDepName() : "None") +
                ", Boards: " + lectureBoardDetails();
    }

    public String lectureBoardDetails() {
        if (belongBoards == null || belongBoards.size() == 0) {
            return "None";
        }

        String details = "";
        for (int i = 0; i < belongBoards.size(); i++) {
            Board<? extends Lecture> board = belongBoards.get(i);
            if (board != null) {
                details += board.getName();
                if (i < belongBoards.size() - 1) {
                    details += " ";
                }
            }
        }

        if (details.equals("")) {
            return "None";
        }
        return details;
    }
}
