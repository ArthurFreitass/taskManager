package model.exceptions;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("User exists in System!");
    }
}
