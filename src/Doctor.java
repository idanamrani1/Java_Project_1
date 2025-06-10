import java.util.ArrayList;

public class Doctor extends Lecture implements Researcher, Comparable<Doctor> {
    private ArrayList<String> articles;

    public Doctor(String name, String id, Degree degree, String nameDegree, double salary) {
        super(name, id, degree, nameDegree, salary);
        this.articles = new ArrayList<>();
    }

    public void addArticle(String article) {
        if (article != null && !article.isEmpty()) {
            articles.add(article);
        }
    }

    public ArrayList<String> getArticles() {
        return articles;
    }

    @Override
    public int getNumberOfArticles() {
        return articles.size();
    }

    @Override
    public int compareTo(Doctor other) {
        return Integer.compare(this.getNumberOfArticles(), other.getNumberOfArticles());
    }

    @Override
    public String toString() {
        return super.toString() + ", Articles: " + articles.size();
    }
}
