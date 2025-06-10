public class Professor extends Doctor {
    private String organization;

    public Professor(String name, String id, Degree degree, String nameDegree, double salary, String organization) {
        super(name, id, degree, nameDegree, salary);
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return super.toString() + ", Organization: " + organization;
    }
}
