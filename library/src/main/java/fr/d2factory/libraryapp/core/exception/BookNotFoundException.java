package fr.d2factory.libraryapp.core.exception;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
