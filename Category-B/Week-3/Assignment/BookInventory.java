public class BookInventory {

    private String title;
    private String author;
    private int copiesAvailable;

    public BookInventory(String title, String author, int copiesAvailable) {
        this.title = title;
        this.author = author;
        this.copiesAvailable = copiesAvailable;
    }

    public void printEntry() {
        System.out.println(title + " by " + author + " - " + copiesAvailable + " copies available");
    }

    public static void main(String[] args) {
        BookInventory[] books = new BookInventory[4];
        books[0] = new BookInventory("Clean Code", "Robert C. Martin", 3);
        books[1] = new BookInventory("Effective Java", "Joshua Bloch", 5);
        books[2] = new BookInventory("Refactoring", "Martin Fowler", 0);
        books[3] = new BookInventory("Design Patterns", "GoF", 2);

        for (BookInventory book : books) {
            book.printEntry();
        }
    }
}
