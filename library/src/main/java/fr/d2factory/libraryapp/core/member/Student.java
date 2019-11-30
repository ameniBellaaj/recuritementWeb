package fr.d2factory.libraryapp.core.member;


import fr.d2factory.libraryapp.core.exception.DomainErrorCode;
import fr.d2factory.libraryapp.core.exception.InsufficientWalletException;
import fr.d2factory.libraryapp.core.util.Constant;
import fr.d2factory.libraryapp.core.util.MemberType;

public class Student extends Member {
    private Boolean studentFirstYear;


    public Student() {
        super(MemberType.STUDENT);
    }

    @Override
    public float payBook(long numberOfDays) {
        float price = 0;
        if (getStudentFirstYear()) {
            price = numberOfDays <= Constant.NBR_MAX_DAYS_BOOK_BORROW_STUDENT ?
                    (Constant.NBR_DAYS_FREE_BOOK_BORROW_STUDENT_FIRST_YEAR > numberOfDays ?
                            (numberOfDays - Constant.NBR_DAYS_FREE_BOOK_BORROW_STUDENT_FIRST_YEAR) * Constant.BORROWING_TARIFF : 0)
                    : (numberOfDays - Constant.NBR_MAX_DAYS_BOOK_BORROW_STUDENT) * Constant.LATE_BORROWING_TARIFF_STUDENT + numberOfDays * Constant.BORROWING_TARIFF;
        } else {
            price = numberOfDays <= Constant.NBR_MAX_DAYS_BOOK_BORROW_STUDENT ? numberOfDays * Constant.BORROWING_TARIFF
                    : (numberOfDays - Constant.NBR_MAX_DAYS_BOOK_BORROW_STUDENT) * Constant.LATE_BORROWING_TARIFF_STUDENT + numberOfDays * Constant.BORROWING_TARIFF;
        }
        updateWallet(price);
        return price;
    }

    private void updateWallet(float price) {
        if (price <= getWallet()) {
            setWallet(getWallet() - price);
        } else {
            throw new InsufficientWalletException(DomainErrorCode.INSUFFICIENT_WALLET_EXCEPTION_STUDENT);
        }

    }

    public Boolean getStudentFirstYear() {
        if (studentFirstYear == null) return false;
        return studentFirstYear;
    }

    public void setStudentFirstYear(Boolean studentFirstYear) {
        this.studentFirstYear = studentFirstYear;
    }
}
