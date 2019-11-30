package fr.d2factory.libraryapp.repository;

import fr.d2factory.libraryapp.core.book.Book;
import fr.d2factory.libraryapp.core.book.ISBN;
import fr.d2factory.libraryapp.core.member.Member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {

    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Member, Map<Book, LocalDate>> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books) {
        books.stream().forEach(
                book -> availableBooks.put(book.getIsbn(), book)
        );
    }

    public Book findBook(long isbnCode) {
        if (availableBooks.size() != 0) {
            return availableBooks.values().stream().filter(book -> book.getIsbn().getIsbnCode() == isbnCode).findFirst().orElse(null);
        }
        return null;
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt, Member member) {
        Map<Book, LocalDate> borrowedBooksByMember = getBorrowedBooksByMember(member);
        borrowedBooksByMember.put(availableBooks.remove(book.getIsbn()), borrowedAt);
        borrowedBooks.put(member, borrowedBooksByMember);
    }

    public LocalDate findBorrowedBookDate(Book book, Member member) {
        if (borrowedBooks.size() != 0) {

            Optional<Map.Entry<Book, LocalDate>> bookDate = borrowedBooks.get(member).entrySet().stream().filter(specifiedBook -> specifiedBook.getKey().equals(book)).findFirst();
            if (bookDate.isPresent()) {
                return bookDate.get().getValue();
            }
        }
        return null;

    }

    public Map<Book, LocalDate> getBorrowedBooksByMember(Member member) {
        Map<Book, LocalDate> books = new HashMap<>();
        if (borrowedBooks.size() != 0) {

            for (Map.Entry<Member, Map<Book, LocalDate>> entry : borrowedBooks.entrySet()) {
                if (entry.getKey() == member) {
                    for (Map.Entry<Book, LocalDate> borrowed : entry.getValue().entrySet()) {
                        books.put(borrowed.getKey(), borrowed.getValue());
                    }
                }
            }

        }
        return books;
    }

}
