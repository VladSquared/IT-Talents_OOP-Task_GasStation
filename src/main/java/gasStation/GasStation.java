package gasStation;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import util.Randomizer;

public class GasStation {
    private ArrayList<FuelPump> fuelPumps;
    private ArrayList<PetrolLoader> petrolLoader;
    private HashMap<Cashier, LinkedBlockingQueue<Car>> cashierQueues;
    private String name;
    private ArrayList<HashMap<String, String>> ledger;

    private final Object loadersLock = new Object();
    private final Object cashierLock = new Object();

    public GasStation(String name) {
        this.name = name;
        this.ledger = new ArrayList<>();

        //init fuel pumps
        fuelPumps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fuelPumps.add(new FuelPump(this, i));

        }
        //init cashiers
        cashierQueues = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            cashierQueues.put(new Cashier(this, "Cashier-" + (i + 1)), new LinkedBlockingQueue());
            Iterator<Cashier> iterator = cashierQueues.keySet().iterator();
            while (iterator.hasNext()) {
                new Thread(iterator.next()).start();
            }
        }
        //init petrol loaders
        petrolLoader = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            petrolLoader.add(new PetrolLoader(this, "Fuel loader-" + (i + 1)));
            for (PetrolLoader loader : petrolLoader) {
                new Thread(loader).start();
            }
        }
    }

    void acceptVehicle(Car car) {
        synchronized (loadersLock) {
            int randomPump = Randomizer.randomInt(0, fuelPumps.size() - 1);
            car.indexPump = randomPump;
            fuelPumps.get(randomPump).addCar(car);
            loadersLock.notifyAll();
        }
    }

    public FuelPump getCarToRefuel() {
        synchronized (loadersLock) {

            ArrayList<FuelPump> randomizedPumps = new ArrayList(this.fuelPumps);
            Collections.shuffle(randomizedPumps);

            boolean areCarsOnPumps = false;
            while (true) {
                for (FuelPump pump : randomizedPumps) {
                    Car car = pump.getFirstCar();
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

    public void payForFuel(Car car) {
        int randomCashier = Randomizer.randomInt(1, cashierQueues.size());
        Iterator<Map.Entry<Cashier, LinkedBlockingQueue<Car>>> iterator = cashierQueues.entrySet().iterator();
        for (int i = 0; i < randomCashier - 1; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            }
        }
        Queue<Car> clientsQueue = iterator.next().getValue();
        clientsQueue.offer(car);
    }

    Car checkForCashierClients(Cashier cashier) {
        try {
            return cashierQueues.get(cashier).take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    void removeCarFromPump(int indexPump) {
        fuelPumps.get(indexPump).removeCarAfterPayment();
        fuelPumps.get(indexPump).setIsLoadingFuel(false);
    }

    void addToLedger(int idPump, FuelType fuelType, double quantity, int loadingTimeSeconds){
        HashMap record = new HashMap<>();
        record.put("id_pump", idPump);
        record.put("fuel_type", fuelType);
        record.put("quantity", quantity);
        record.put("loading_time_seconds", loadingTimeSeconds);
        ledger.add(record);
    }
}
