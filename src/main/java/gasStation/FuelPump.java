package gasStation;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class FuelPump {
    private GasStation gasStation;
    private Queue<Car> cars;
    private int id;
    private boolean isLoadingFuel;

    FuelPump(GasStation gasStation, int id) {
        this.gasStation = gasStation;
        this.cars = new ConcurrentLinkedQueue<>();
        this.id = id;
        this.isLoadingFuel = false;
    }

    synchronized void addCar(Car car) {
        cars.offer(car);
        System.out.println("A car was added to " + this.id + " intend to load " + car.getLoadedFuelLiters() +
                ", money " + car.getMoneyCash() + ", car id " + car.getCarId());
    }

    Car getFirstCar(){
        return cars.peek();
    }

    boolean isLoadingFuel(){
        return isLoadingFuel;
    }

    public void setIsLoadingFuel(boolean loadingFuel) {
        isLoadingFuel = loadingFuel;
    }

    public void removeCarAfterPayment() {
        cars.remove();
    }
}
