public class Professor extends Doctor {
    private String organization;

    public Professor(String name, String id, Degree degree, String nameDegree, double salary, Board[] belongBoard, Department belongDepartment, String organization) {
        super(name, id, degree, nameDegree, salary, belongBoard, belongDepartment);
        setOrganization(organization);
    }
    public String getOrganization() {
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }


}
