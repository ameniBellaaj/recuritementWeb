package fr.d2factory.libraryapp.core.book;

public class ISBN {
    private long isbnCode;

    public ISBN() {
    }

    public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    public long getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    @Override
    public String toString() {
        return "ISBN{" +
                "isbnCode=" + isbnCode +
                '}';
    }
}
