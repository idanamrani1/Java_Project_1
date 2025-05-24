public class Doctor extends Lecture implements Researcher, expandArray, Comparable<Doctor> {
    private String[] articles; // array of the articles
    private int numberOfArticles; // the number of articles

    public Doctor(String name, String id, Degree degree, String nameDegree, double salary) {
        super(name, id, degree, nameDegree, salary);
        this.articles = new String[2];
        this.numberOfArticles = 0;
    }

    @Override
    public int compareTo(Doctor other) {
        return this.numberOfArticles - other.getNumberOfArticles();
    }

    public boolean addArticle(String article) {
        if (isFullArray()) {
            expandable();
        }
        articles[numberOfArticles++] = article;
        return true;
    }

    @Override
    public int getNumberOfArticles() {
        return numberOfArticles;
    }

    public boolean isFullArray() {
        return numberOfArticles == articles.length;
    }

    @Override
    public void expandable() {
        String[] newArticles = new String[articles.length * 2];
        for (int i = 0; i < articles.length; i++) {
            newArticles[i] = articles[i];
        }
        articles = newArticles;
    }

    public String[] getArticles() {
        return articles;
    }

    @Override
    public String toString() {
        StringBuilder articlesStr = new StringBuilder("Articles: ");
        if (numberOfArticles == 0) {
            articlesStr.append("None");
        } else {
            for (int i = 0; i < numberOfArticles; i++) {
                articlesStr.append(articles[i]);
                if (i < numberOfArticles - 1) {
                    articlesStr.append(", ");
                }
            }
        }

        return super.toString() + ", " + articlesStr;
    }
}
