package InformationClasses;

import java.io.Serializable;

public class BookInstance implements Serializable {


    private int inventoryNumber;
    private Book book;
    private boolean issued;

    public Book getBook() {
        return book;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public boolean getIssued() {
        return issued;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setInventoryNumber(int inventoryNumber) throws BadFieldsException {
        if (inventoryNumber < 0) {
            throw (new BadFieldsException(" Invalid inventory number "));
        }
        this.inventoryNumber = inventoryNumber;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public BookInstance(int inventoryNumber, Book book, boolean issued) throws BadFieldsException {
        this.book = book;
        this.setInventoryNumber(inventoryNumber);
        this.setIssued(issued);
    }

    @Override
    public String toString() {
        return "BookInstance(Invent numb: " + inventoryNumber + "  (" + book.toString() + ")issued: " + issued + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BookInstance) {
            if (obj.hashCode() == hashCode()) {
                BookInstance toEq = (BookInstance) obj;
                return book.equals(toEq.book) && (inventoryNumber == toEq.inventoryNumber) && (issued == toEq.issued);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return inventoryNumber ^ book.hashCode();
    }
}
