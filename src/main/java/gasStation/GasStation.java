package gasStation;

import java.util.ArrayList;
import java.util.Collections;

import util.Randomizer;

public class GasStation {
    private ArrayList<FuelPump> fuelPumps;
    private ArrayList<Cashier> cashiers;
    private ArrayList<PetrolLoader> petrolLoader;
    private String name;

    private final Object loadersLock = new Object();

    public GasStation(String name) {
        this.name = name;

        //init fuel pumps
        fuelPumps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fuelPumps.add(new FuelPump(this, i));
        }
        //init cashiers
        cashiers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            cashiers.add(new Cashier(this, "Cashier-"+(i+1)));
        }
        //init petrol loaders
        petrolLoader = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            petrolLoader.add(new PetrolLoader(this, "Fuel loader-" + (i+1)));
            for(PetrolLoader loader: petrolLoader){
                new Thread(loader).start();
            }
        }
    }

    void acceptVehicle(Car car) {
        synchronized (loadersLock) {
            int randomPump = Randomizer.randomInt(0, fuelPumps.size() - 1);
            fuelPumps.get(randomPump).addCar(car);
            loadersLock.notifyAll();
        }
    }

    public FuelPump getCarToRefuel() {
        synchronized (loadersLock){

            ArrayList<FuelPump> randomizedPumps = new ArrayList(this.fuelPumps);
            Collections.shuffle(randomizedPumps);

            boolean areCarsOnPumps = false;
            while(true) {
                for (FuelPump pump : randomizedPumps) {
                    Car car = pump.checkForCar();
                    if (car != null && !pump.isLoadingFuel()) {
                        pump.setIsLoadingFuel(true);
                        return pump;
                    }
                }
                if (!areCarsOnPumps) {
                    try {
                        loadersLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
