package org.example.utils;

import org.example.model.Foodtruck;

public class TempHolder {

    private static Foodtruck selectedFoodtruck;

    public static Foodtruck getSelectedFoodtruck() {
        return selectedFoodtruck;
    }

    public static void setSelectedFoodtruck(Foodtruck ft) {
        selectedFoodtruck = ft;
    }
}
