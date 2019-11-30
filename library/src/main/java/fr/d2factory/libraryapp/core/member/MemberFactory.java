package fr.d2factory.libraryapp.core.member;

import fr.d2factory.libraryapp.core.exception.DomainErrorCode;
import fr.d2factory.libraryapp.core.exception.MemberTypeException;
import fr.d2factory.libraryapp.core.util.MemberType;

public class MemberFactory {
    public static Member buildMember(MemberType memberType) {
        switch (memberType) {
            case RESIDENT:
                return new Resident();
            case STUDENT:
                return new Student();
            default:
                throw new MemberTypeException(DomainErrorCode.MEMBER_TYPE_EXCEPTION);
        }
    }
}
