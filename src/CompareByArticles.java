import java.io.Serializable;
import java.util.Comparator;

public class CompareByArticles implements Comparator<Board>, Serializable {

    @Override
    public int compare(Board b1, Board b2) {
        return b1.getNumOfArticles() - b2.getNumOfArticles();
    }
}
