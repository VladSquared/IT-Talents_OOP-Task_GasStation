package gasStation;

abstract class Staff {
    GasStation gasStation;
    private String name;

    public Staff(GasStation gasStation, String name) {
        this.gasStation = gasStation;
        if(name.matches("[a-zA-Z]+")) {
            this.name = name;
        }
    }
}
