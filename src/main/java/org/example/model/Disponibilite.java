package org.example.model;

import java.time.LocalDateTime;

public class Disponibilite {
    private int id;
    private int foodtruckId;
    private int positionId; // optionnel selon ton schéma
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    public Disponibilite(int id, int foodtruckId, int positionId, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.id = id;
        this.foodtruckId = foodtruckId;
        this.positionId = positionId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Disponibilite(int foodtruckId, int positionId, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this(0, foodtruckId, positionId, dateDebut, dateFin);
    }

    // getters / setters
    public int getId() { return id; }
    public int getFoodtruckId() { return foodtruckId; }
    public int getPositionId() { return positionId; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }

    public void setId(int id) { this.id = id; }
    public void setFoodtruckId(int foodtruckId) { this.foodtruckId = foodtruckId; }
    public void setPositionId(int positionId) { this.positionId = positionId; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
}
