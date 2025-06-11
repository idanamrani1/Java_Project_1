import java.io.Serializable;

public class Professor extends Doctor implements Serializable {
    private String organization;

    public Professor(String name, String id, Degree degree, String nameDegree, double salary, String organization) {
        super(name, id, degree, nameDegree, salary);
        this.organization = organization;
    }

    @Override
    public String toString() {
        return super.toString() + ", Organization: " + organization;
    }
}
