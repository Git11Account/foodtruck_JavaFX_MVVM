package org.example.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.repository.AdminRepository;
import com.sothawo.mapjfx.Coordinate;

public class AdminDashboardViewModel {

    private final AdminRepository repo = new AdminRepository();

    public static class RequestItem {
        public int id;
        public String owner;
        public String nom;
        public String description;
        public Coordinate coord;

        public RequestItem(int id, String owner, String nom, String description, Coordinate coord) {
            this.id = id;
            this.owner = owner;
            this.nom = nom;
            this.description = description;
            this.coord = coord;
        }

        @Override
        public String toString() {
            return nom + " — " + owner;
        }
    }

    private final ObservableList<RequestItem> demandes = FXCollections.observableArrayList();
    public ObservableList<RequestItem> demandesProperty() { return demandes; }

    public void chargerDemandes() {
        demandes.clear();
        var list = repo.getPendingRequests();

        for (var f : list) {
            demandes.add(new RequestItem(
                    f.id(),
                    f.ownerName(),
                    f.nom(),
                    f.description(),
                    f.coord()
            ));
        }
    }

    public void approve(int id) {
        repo.approve(id);
        chargerDemandes();
    }

    public void refuse(int id) {
        repo.refuse(id);
        chargerDemandes();
    }
}
