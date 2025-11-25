package org.example.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.repository.DisponibiliteRepository;
import com.sothawo.mapjfx.Coordinate;

import java.time.LocalDate;

public class MapViewModel {

    private final ObservableList<String> disponibilites = FXCollections.observableArrayList();
    private final DisponibiliteRepository repo = new DisponibiliteRepository();
    private final ObservableList<Coordinate> coordonnees = FXCollections.observableArrayList();

    public ObservableList<String> getDisponibilites() {
        return disponibilites;
    }
    public ObservableList<Coordinate> getCoordonnees() { return coordonnees; }

    public void chargerDisponibilites(LocalDate date) {
        var result = repo.getDisponibilitesComplet(date);

        disponibilites.setAll(result.noms);
        coordonnees.setAll(result.coords);
    }
}
