package Exceptions;

public class NoArticlesException extends RuntimeException {
    public NoArticlesException(String message) {
        super(message);
    }
}
