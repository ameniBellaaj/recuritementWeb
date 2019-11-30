package fr.d2factory.libraryapp.core.member;


import fr.d2factory.libraryapp.core.exception.DomainErrorCode;
import fr.d2factory.libraryapp.core.exception.InsufficientWalletException;
import fr.d2factory.libraryapp.core.util.Constant;
import fr.d2factory.libraryapp.core.util.MemberType;

public class Resident extends Member {


    public Resident() {
        super(MemberType.RESIDENT);
    }

    @Override
    public float payBook(long numberOfDays) {
        float price = numberOfDays <= Constant.NBR_MAX_DAYS_BOOK_BORROW_RESIDENT ? numberOfDays * Constant.BORROWING_TARIFF : numberOfDays * Constant.BORROWING_TARIFF + (numberOfDays - Constant.NBR_MAX_DAYS_BOOK_BORROW_RESIDENT) * Constant.LATE_BORROWING_TARIFF_RESIDENT;

        updateWallet(price);
        return price;
    }

    private void updateWallet(float price) {
        if (price <= getWallet()) {
            setWallet(getWallet() - price);
        } else {
            throw new InsufficientWalletException(DomainErrorCode.INSUFFICIENT_WALLET_EXCEPTION_RESIDENT);
        }
    }
}
