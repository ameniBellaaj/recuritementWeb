package fr.d2factory.libraryapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.core.book.Book;
import fr.d2factory.libraryapp.core.exception.BookNotFoundException;
import fr.d2factory.libraryapp.core.exception.HasLateBooksException;
import fr.d2factory.libraryapp.core.member.Member;
import fr.d2factory.libraryapp.core.member.MemberFactory;
import fr.d2factory.libraryapp.core.util.MemberType;
import fr.d2factory.libraryapp.domain.Library;
import fr.d2factory.libraryapp.repository.BookRepository;
import fr.d2factory.libraryapp.service.LibraryImplementation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryTest {


    BookRepository bookRepository = new BookRepository();

    LibraryImplementation library = new LibraryImplementation(bookRepository);

    private Member student;

    private Member resident;


    public List<Book> books = new ArrayList<>();

    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("books.json")).getFile());
        ObjectMapper mapper = new ObjectMapper();
        books = mapper.readValue(file, new TypeReference<List<Book>>() {
        });
        bookRepository.addBooks(books);
        resident = MemberFactory.buildMember(MemberType.RESIDENT);
        student = MemberFactory.buildMember(MemberType.STUDENT);

    }

    @Test
    public void member_can_borrow_a_book_if_book_is_available() throws Exception {
        Book book = books.get(0);

        Book result = library.borrowBook(book.getIsbn().getIsbnCode(), resident, LocalDate.of(2019, 11, 1));
        Assert.assertNotNull(result);
    }

    @Test(expected = BookNotFoundException.class)
    public void borrowed_book_is_no_longer_available() {
        Book book = books.get(0);
        library.borrowBook(book.getIsbn().getIsbnCode(), resident, LocalDate.of(2019, 10, 1));
        Book result = library.borrowBook(book.getIsbn().getIsbnCode(), student, LocalDate.of(2020, 2, 1));
        Assert.assertNull(result);

    }

    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() throws Exception {
        Book book = books.get(0);
        resident.setWallet(10);
        Book result = library.borrowBook(book.getIsbn().getIsbnCode(), resident, LocalDate.of(2019, 11, 1));
        library.returnBook(book, resident);

        Assert.assertEquals(resident.getWallet(), 7.1, 2.9);
    }

    @Test
    public void students_pay_10_cents_the_first_30days() throws Exception {

        Book book = books.get(0);
        student.setWallet(10);
        library.borrowBook(book.getIsbn().getIsbnCode(), student, LocalDate.of(2019, 11, 1));
        library.returnBook(book, student);

        Assert.assertEquals(student.getWallet(), 7, 3);
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days() throws Exception {

        Book book = books.get(0);
        student.setWallet(10);
        library.borrowBook(book.getIsbn().getIsbnCode(), student, LocalDate.of(2019, 11, 1));
        library.returnBook(book, student);

        Assert.assertEquals(student.getWallet(), 8.5, 1.5);
    }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() throws Exception {

        Book book = books.get(0);
        student.setWallet(10);
        library.borrowBook(book.getIsbn().getIsbnCode(), student, LocalDate.of(2019, 10, 28));
        library.returnBook(book, student);

        Assert.assertEquals(student.getWallet(), 6.4, 3.6);
    }


    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days()
            throws Exception {
        Book book = books.get(0);
        resident.setWallet(15);
        library.borrowBook(book.getIsbn().getIsbnCode(), resident, LocalDate.of(2019, 9, 1));
        library.returnBook(book, resident);

        Assert.assertEquals(resident.getWallet(), 0, 15.0);
    }

    @Test(expected = HasLateBooksException.class)
    public void members_cannot_borrow_book_if_they_have_late_books() {
        Book book1 = books.get(0);
        Book book2 = books.get(1);
        library.borrowBook(book1.getIsbn().getIsbnCode(), resident, LocalDate.of(2019, 10, 1));
        Book result = library.borrowBook(book2.getIsbn().getIsbnCode(), resident, LocalDate.of(2020, 2, 1));
        Assert.assertNull(result);

    }
}
