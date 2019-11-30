package fr.d2factory.libraryapp.service;

import fr.d2factory.libraryapp.core.book.Book;
import fr.d2factory.libraryapp.core.exception.BookNotFoundException;
import fr.d2factory.libraryapp.core.exception.DomainErrorCode;
import fr.d2factory.libraryapp.core.exception.HasLateBooksException;
import fr.d2factory.libraryapp.core.member.Member;
import fr.d2factory.libraryapp.core.util.Constant;
import fr.d2factory.libraryapp.domain.Library;
import fr.d2factory.libraryapp.repository.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibraryImplementation extends Library {

    public BookRepository bookRepository;

    public LibraryImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
        Book availableBook = bookRepository.findBook(isbnCode);
        if (availableBook != null) {
            if (checkIfMemberCanBorrow(availableBook, borrowedAt, member)) {
                bookRepository.saveBookBorrow(availableBook, borrowedAt, member);
                return availableBook;
            } else {
                throw new HasLateBooksException(DomainErrorCode.HAS_LATE_BOOK_EXCEPTION);
            }
        } else {
            throw new BookNotFoundException(DomainErrorCode.BOOK_NOT_FOUND_EXCEPTION);
        }


    }

    public boolean checkIfMemberCanBorrow(Book availableBook, LocalDate borrowedAt, Member member) {
        boolean canBorrow = true;
        final Map<Book, LocalDate> borrowedBooksByMember = bookRepository.getBorrowedBooksByMember(member);
        if (borrowedBooksByMember.size() != 0) {
            canBorrow = borrowedBooksByMember.values().stream().anyMatch(dateborrwedOtherBook -> checkBorrow(member, dateborrwedOtherBook, borrowedAt));

        }
        return canBorrow;

    }

    public boolean checkBorrow(Member member, LocalDate dateborrwedOtherBook, LocalDate borrowedAt) {
        boolean canBorrow = false;
        long nbrDays = ChronoUnit.DAYS.between(dateborrwedOtherBook, borrowedAt);
        switch (member.getMemberType()) {
            case STUDENT:
                canBorrow = nbrDays > Constant.NBR_MAX_DAYS_BOOK_BORROW_STUDENT ? false : true;
                break;
            case RESIDENT:
                canBorrow = nbrDays > Constant.NBR_MAX_DAYS_BOOK_BORROW_RESIDENT ? false : true;
                break;
        }
        return canBorrow;
    }

    @Override
    public void returnBook(Book book, Member member) throws Exception {

        LocalDate borrowedDate = bookRepository.findBorrowedBookDate(book, member);
        if (borrowedDate != null) {
            long nbrDays = ChronoUnit.DAYS.between(borrowedDate, LocalDate.now());
            member.payBook(nbrDays);
            List<Book> books = new ArrayList<>();
            books.add(book);
            bookRepository.addBooks(books);
        } else {
            throw new BookNotFoundException(DomainErrorCode.BOOK_NOT_FOUND_EXCEPTION);
        }
    }

}
