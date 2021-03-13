import gasStation.Car;
import gasStation.FuelType;
import gasStation.GasStation;
import util.Randomizer;

public class Demo {


    public static void main(String[] args) {
        GasStation gasStation = new GasStation("demo1");
        //generate cars
        int idCar = 0;
        while(true){
            FuelType f = FuelType.values()[Randomizer.randomInt(0, FuelType.values().length-1)];
            double intentToLoad = Randomizer.randomInt(10,40);
            double moneyCash = 100;
            try {
                Car newCar = new Car(moneyCash, intentToLoad, f, ++idCar);
                newCar.setGasStation(gasStation);
                newCar.start();
            } catch (Exception e) {
                System.out.println("Cannot generate car - " + e.getMessage());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


}
