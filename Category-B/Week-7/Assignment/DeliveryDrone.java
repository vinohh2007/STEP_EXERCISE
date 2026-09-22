public class DeliveryDrone extends Drone implements Trackable {

    public DeliveryDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return id + " flying delivery route";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}
