public abstract class Toy {

    private static int counter = 1000;

    protected final String name;
    private final String toyId;

    public Toy(String name) {
        this.name = name;
        counter++;
        this.toyId = "TOY-" + counter;
    }

    public abstract String makeSound();

    public String getToyId() {
        return toyId;
    }
}
