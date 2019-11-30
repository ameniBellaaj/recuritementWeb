package fr.d2factory.libraryapp;


import fr.d2factory.libraryapp.core.book.Book;
import fr.d2factory.libraryapp.core.book.ISBN;
import fr.d2factory.libraryapp.core.member.Member;
import fr.d2factory.libraryapp.core.member.MemberFactory;
import fr.d2factory.libraryapp.core.util.MemberType;
import fr.d2factory.libraryapp.repository.BookRepository;
import fr.d2factory.libraryapp.service.LibraryImplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryApplication {

    public static void main(String[] args) throws Exception {
        BookRepository bookRepository = new BookRepository();
        LibraryImplementation libraryTonwsville = new LibraryImplementation(bookRepository);
        ISBN isbnDanhel = new ISBN(123L);
        ISBN isbnPretty = new ISBN(456L);
        Book b1 = new Book("les trois soeurs", "Jack", isbnDanhel);
        Book b2 = new Book("la belle fille", "Salmine", isbnPretty);
        List<Book> books = new ArrayList<>();
        books.add(b1);
        books.add(b2);
        bookRepository.addBooks(books);
        Member ameni = MemberFactory.buildMember(MemberType.RESIDENT);
        ameni.setWallet(300);
        Member john = MemberFactory.buildMember(MemberType.STUDENT);
        john.setWallet(10);
        john.setStudentFirstYear(true);
        System.out.println(libraryTonwsville.borrowBook(b1.getIsbn().getIsbnCode(), ameni, LocalDate.of(2019, 8, 8)));
//       System.out.println(libraryTonwsville.borrowBook(b2.getIsbn().getIsbnCode(),ameni,LocalDate.of(2019,11,28)));
        libraryTonwsville.returnBook(b1, ameni);
        //  System.out.println(libraryTonwsville.bookRepository.findBook(b1.getIsbn().getIsbnCode()));

    }
}
