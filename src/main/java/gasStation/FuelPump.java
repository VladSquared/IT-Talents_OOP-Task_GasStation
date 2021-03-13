package gasStation;

import java.util.LinkedList;
import java.util.Queue;

class FuelPump {
    private GasStation gasStation;
    private Queue<Car> cars;
    private int id;
    private boolean isLoadingFuel;

    FuelPump(GasStation gasStation, int id) {
        this.gasStation = gasStation;
        this.cars = new LinkedList();
        this.id = id;
        this.isLoadingFuel = false;
    }

    synchronized void addCar(Car car) {
        cars.offer(car);
        System.out.println("A car was added to " + this.id + " intend to load " + car.getIntentToLoadFuel() +
                ", money " + car.getMoneyCash() + " id " + car.getCarId());
    }

    Car checkForCar(){
        return cars.peek();
    }

    boolean isLoadingFuel(){
        return isLoadingFuel;
    }

    public void setIsLoadingFuel(boolean loadingFuel) {
        isLoadingFuel = loadingFuel;
    }
}
