package gasStation;

public class Car extends Thread {
    private GasStation gasStation;
    private int id;
    int indexPump;

    static class Driver {
        private static double moneyCash;
        private static double intentToLoadFuelLiters;

        public static double getMoneyCash() {
            return moneyCash;
        }

        public static double getIntentToLoadFuel() {
            return intentToLoadFuelLiters;
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
            Driver.intentToLoadFuelLiters = intentToLoadFuel;
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

    public double getLoadedFuelLiters() {
        return Driver.intentToLoadFuelLiters;
    }

    public double getMoneyCash() {
        return Driver.moneyCash;
    }

    public int getCarId() {
        return id;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void notifyToPay() {
        gasStation.payForFuel(this);
    }

    public void goBackToRoad(){
        gasStation.removeCarFromPump(indexPump);
        System.out.println("Car " + this.id + "was removed from " + indexPump + ". The driver has left with " + getMoneyCash());
    }

    public void payForPetrol(double amount) {
        Driver.moneyCash -= amount;
    }

    @Override
    public void run() {
        goToStationToLoadFuel();
    }
}
