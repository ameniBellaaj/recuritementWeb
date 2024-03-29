package fr.d2factory.libraryapp.domain;


import fr.d2factory.libraryapp.core.book.Book;
import fr.d2factory.libraryapp.core.exception.HasLateBooksException;
import fr.d2factory.libraryapp.core.member.Member;

import java.time.LocalDate;

/**
 * The library class is in charge of stocking the books and managing the return delays and members
 * <p>
 * The books are available via the {@link fr.d2factory.libraryapp.repository.BookRepository}
 */
public abstract class Library {


    /**
     * A member is borrowing a book from our library.
     *
     * @param isbnCode   the isbn code of the book
     * @param member     the member who is borrowing the book
     * @param borrowedAt the date when the book was borrowed
     * @return the book the member wishes to obtain if found
     * @throws HasLateBooksException in case the member has books that are late
     * @see fr.d2factory.libraryapp.core.book.ISBN
     * @see Member
     */
    public abstract Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException, Exception;

    /**
     * A member returns a book to the library.
     * We should calculate the tarif and probably charge the member
     *
     * @param book   the {@link Book} they return
     * @param member the {@link Member} who is returning the book
     */
    public abstract void returnBook(Book book, Member member) throws Exception;
}
