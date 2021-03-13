import gasStation.Car;
import gasStation.FuelType;
import gasStation.GasStation;
import gasStation.GasStationDAO;
import util.Randomizer;

import java.io.File;

public class Demo {

    static GasStation gasStation = new GasStation("demo1");

    public static void main(String[] args) {
        new Thread(()->generateCars()).start();
        new Thread(()->generateReports()).start();


    }

    static void generateCars(){
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

    static void generateReports(){
        while(true){
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            File dir = new File("reports");
            dir.mkdir();

            GasStationDAO.saveInReportAllRefueling();
            GasStationDAO.saveInReportCarsToday();
            GasStationDAO.saveInReportTotalRefilmentByType();
        }

    }


}
