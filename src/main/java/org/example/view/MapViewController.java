package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import org.example.viewmodel.MapViewModel;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapViewController {

    @FXML private DatePicker datePicker;
    @FXML private Button loadButton;
    @FXML private ListView<String> listDisponibilites;
    @FXML private MapView mapView;

    private final MapViewModel viewModel = new MapViewModel();

    // Liste pour gérer les markers ajoutés
    private final List<Marker> markers = new ArrayList<>();

    @FXML
    private void initialize() {
        // Initialise la carte correctement pour MapJFX 3.x
        mapView.initialize(Configuration.builder()
                .showZoomControls(true)
                .build());

        mapView.setZoom(12);
        mapView.setCenter(new Coordinate(48.8566, 2.3522)); // Paris

        // Bind vue <-> viewmodel
        listDisponibilites.setItems(viewModel.getDisponibilites());

        loadButton.setOnAction(e -> {
            LocalDate date = datePicker.getValue();
            viewModel.chargerDisponibilites(date);

            afficherPointsSurCarte();
        });
    }

    // Supprime tous les markers de la carte
    private void clearAllMarkers() {
        for (Marker m : markers) {
            mapView.removeMarker(m);
        }
        markers.clear();
    }

    // Ajoute les markers correspondant aux coordonnées du viewmodel
    private void afficherPointsSurCarte() {
        clearAllMarkers();

        viewModel.getCoordonnees().forEach(coord -> {
            Marker marker = Marker.createProvided(Marker.Provided.BLUE)
                    .setPosition(coord)
                    .setVisible(true);

            mapView.addMarker(marker);
            markers.add(marker);
        });
    }
}
