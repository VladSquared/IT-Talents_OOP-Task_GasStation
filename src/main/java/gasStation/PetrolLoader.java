package gasStation;

class PetrolLoader extends Staff implements Runnable{
    public PetrolLoader(GasStation gasStation, String name) {
        super(gasStation, name);
    }

    @Override
    public void run() {
        while(true){
            FuelPump pump = this.gasStation.getCarToRefuel();
            try {
                Thread.sleep(5000);
                System.out.println("Car " + pump.checkForCar().getCarId() + " was filled up!");
                pump.setIsLoadingFuel(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
