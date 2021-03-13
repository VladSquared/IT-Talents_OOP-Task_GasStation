package gasStation;

public class Car extends Thread {
    private GasStation gasStation;
    private int id;

    static class Driver {
        private static double moneyCash;
        private static double intentToLoadFuel;

        public static double getMoneyCash() {
            return moneyCash;
        }

        public static double getIntentToLoadFuel() {
            return intentToLoadFuel;
        }

    }

    private FuelType fuelType;

    public Car(Double moneyInCash, Double intentToLoadFuel, FuelType fuel, int id) throws Exception {

        if (moneyInCash >= intentToLoadFuel * fuel.getPrice()) {
            Driver.moneyCash = moneyInCash;
        } else {
            throw new Exception("Not enough money!");
        }
        if (intentToLoadFuel >= 10 && intentToLoadFuel <= 40) {
            Driver.intentToLoadFuel = intentToLoadFuel;
        } else {
            throw new Exception("Intend to load fuel not in range (10,40)");
        }
        this.fuelType = fuel;
        this.id = id;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    void goToStationToLoadFuel() {
        gasStation.acceptVehicle(this);
    }

    public double getIntentToLoadFuel() {
        return Driver.intentToLoadFuel;
    }

    public double getMoneyCash() {
        return Driver.moneyCash;
    }

    public int getCarId() {
        return id;
    }

    @Override
    public void run() {
        goToStationToLoadFuel();
    }
}
