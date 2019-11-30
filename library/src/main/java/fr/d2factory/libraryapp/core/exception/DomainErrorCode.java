package fr.d2factory.libraryapp.core.exception;

public interface DomainErrorCode {
    static final String MEMBER_TYPE_EXCEPTION = "type membre renseigné n'existe pas";
    static final String INSUFFICIENT_WALLET_EXCEPTION_RESIDENT = "Le Résident ne possède pas d'argent";
    static final String INSUFFICIENT_WALLET_EXCEPTION_STUDENT = "Le Résident ne possède pas d'argent";
    static final String BOOK_NOT_FOUND_EXCEPTION = "Le livre spécifié n'existe pas";
    static final String HAS_LATE_BOOK_EXCEPTION = "Ce membre ne peut pas emprunter ce livre : un livre en retard";


}
