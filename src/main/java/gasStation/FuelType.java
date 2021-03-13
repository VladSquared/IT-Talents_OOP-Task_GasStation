package gasStation;

import static gasStation.Constants.*;

public enum FuelType {
    DIESEL(PRICE_A95), A95(PRICE_DIESEL), NATURAL_GAS(PRICE_NATURAL_GAS);

    private double price;

    FuelType(Double price){
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
