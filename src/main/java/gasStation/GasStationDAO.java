package gasStation;


import util.DBConnector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Scanner;

public class GasStationDAO {
    static Connection connection = DBConnector.getInstance().getConnection();
    static int numberOfFile = 0;

    static void addLedgerEntry(int fuelPumpId, FuelType fuelType, double quantity, int loadingTimeSeconds){

        String sql = "INSERT INTO gas_station (fuel_pump_id, fuel_type, time, quantity) VALUES (?,?,?,?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, fuelPumpId);
            ps.setString(2, fuelType.name());
            ps.setDouble(4, quantity);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveInReportAllRefueling(){
        String sql= "SELECT fuel_pump_id, fuel_type, SUM(quantity) AS quantity, `time` FROM gas_station.gas_station\n" +
                "GROUP BY fuel_pump_id, fuel_type\n" +
                "ORDER BY fuel_pump_id, fuel_type, time ASC";


        File file = new File("reports/report" + ++numberOfFile + "-" + LocalDateTime.now().toLocalDate() + ".txt");
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                PrintStream print = new PrintStream(file);
        ){

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                print.print("  fuel_pump_id : " + resultSet.getString(1) + ",");
                print.print("  fuel_type : " + resultSet.getString(2) + ",");
                print.print("  quantity : " + resultSet.getDouble(3) + ",");
                print.print("  time : " + resultSet.getString(4));
                print.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to file - " + e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void saveInReportCarsToday(){
        String sql= "SELECT fuel_pump_id, COUNT(id) AS cars_today FROM gas_station\n" +
                "WHERE DATE(time) = DATE(NOW())\n" +
                "GROUP BY fuel_pump_id";


        File file = new File("reports/report" + ++numberOfFile + "-" + LocalDateTime.now().toLocalDate() + ".txt");
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                PrintStream print = new PrintStream(file);
        ){

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                print.print("  fuel_pump_id : " + resultSet.getString(1) + ",");
                print.print("  cars_today : " + resultSet.getString(2) + ",");
                print.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to file - " + e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void saveInReportTotalRefilmentByType(){
        String sql= "SELECT fuel_type, COUNT(quantity) AS quantity FROM gas_station\n" +
                "GROUP BY fuel_type";


        File file = new File("reports/report" + ++numberOfFile + "-" + LocalDateTime.now().toLocalDate() + ".txt");
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                PrintStream print = new PrintStream(file);
        ){

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                print.print("  fuel_type : " + resultSet.getString(1) + ",");
                print.print("  quantity : " + resultSet.getString(2) + ",");
                print.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to file - " + e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
