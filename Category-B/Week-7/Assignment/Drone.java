public abstract class Drone {

    protected final String id;

    public Drone(String id) {
        this.id = id;
    }

    public abstract String fly();
}
