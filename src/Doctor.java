public class Doctor extends Lecture implements Researcher, expandArray, IsFullArray {
    private String[] articles; // array of the articles
    private int numberOfArticles; // the number of articles

    public Doctor(String name, String id, Degree degree, String nameDegree, double salary, Board[] belongBoard, Department belongDepartment) {
        super( name,  id,  degree,  nameDegree, salary) ;
        this.articles = new String[2];
        this.numberOfArticles = 0;
    }

    public boolean addArticle(String article) {
        if (isFullArray()) {
            expandable();
        }
        articles[numberOfArticles] = article;
        numberOfArticles++;
        return true;
    }

    @Override
    public int getNumberOfArticles() {
        return numberOfArticles;
    }

    public String[] getArticles() {
        return articles;
    }

    @Override
    public void expandable(){
        String[] newArticles = new String[numberOfArticles * 2];
        for (int i = 0; i < numberOfArticles; i++) {
            newArticles[i] = articles[i];
        }
        articles = newArticles;
    }

    @Override
    public boolean isFullArray() {
        return numberOfArticles == articles.length;
    }


}
