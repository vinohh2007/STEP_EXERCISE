public abstract class KitchenTool {

    private int speedLevel;

    public KitchenTool() {
    }

    public abstract String prepare();

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        if (speedLevel < 1 || speedLevel > 5) {
            return; // reject invalid values, keep current speedLevel unchanged
        }
        this.speedLevel = speedLevel;
    }
}
