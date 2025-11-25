package org.example.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.Foodtruck;
import org.example.repository.FoodtruckRepository;

public class OwnerDashboardViewModel {

    private final FoodtruckRepository repo = new FoodtruckRepository();
    private final ObservableList<Foodtruck> myFoodtrucks = FXCollections.observableArrayList();

    public ObservableList<Foodtruck> getMyFoodtrucks() {
        return myFoodtrucks;
    }

    public void loadMyFoodtrucks(int ownerId) {
        myFoodtrucks.setAll(repo.getFoodtrucksByOwner(ownerId));
    }
}
