package InformationClasses;

import java.io.Serializable;
import java.util.UUID;

public class BookInstance extends LibraryInfo implements Serializable   {


    private UUID inventoryNumber;
    private Book book;
    private boolean issued;

    public Book getBook() {
        return book;
    }

    public UUID getInventoryNumber() {
        return inventoryNumber;
    }

    public boolean getIssued() {
        return issued;
    }

    public void setBook(Book book) {
        this.book = book;
    }



    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public BookInstance( Book book, boolean issued)  {
        this.book = book;
        this.inventoryNumber = UUID.randomUUID();
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
        return book.hashCode();
    }
}
