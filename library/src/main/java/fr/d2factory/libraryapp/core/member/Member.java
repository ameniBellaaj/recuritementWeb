package fr.d2factory.libraryapp.core.member;

import fr.d2factory.libraryapp.core.util.MemberType;
import fr.d2factory.libraryapp.domain.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    /**
     * An initial sum of money the member has
     */
    private float wallet;
    private MemberType memberType;
    private Boolean studentFirstYear;


    public Member(MemberType memberType) {
        this.memberType = memberType;
    }

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract float payBook(long numberOfDays);

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public Boolean getStudentFirstYear() {
        if (studentFirstYear == null) {
            studentFirstYear = false;
        }
        return studentFirstYear;
    }

    public void setStudentFirstYear(Boolean studentFirstYear) {
        this.studentFirstYear = studentFirstYear;
    }
}
