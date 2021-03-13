package gasStation;

class Cashier extends Staff implements Runnable {

    public Cashier(GasStation gasStation, String name) {
        super(gasStation, name);
    }


    @Override
    public void run() {
        while (true) {
            Car client = gasStation.checkForCashierClients(this);

            FuelType fuelType = client.getFuelType();
            client.payForPetrol(client.getLoadedFuelLiters() * fuelType.getPrice());

            gasStation.addToLedger(client.indexPump, client.getFuelType(), client.getLoadedFuelLiters(), 5);

            GasStationDAO.addLedgerEntry(client.indexPump, client.getFuelType(), client.getLoadedFuelLiters(), 5);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            client.goBackToRoad();

        }
    }
}
