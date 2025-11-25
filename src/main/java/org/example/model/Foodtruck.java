package org.example.model;

public class Foodtruck {

    private int id;
    private int ownerId;
    private String nom;
    private String type;
    private String telephone;
    private String description;
    private double latitude;
    private double longitude;
    private String status;

    public Foodtruck() {}

    public Foodtruck(int id, int ownerId, String nom, String type, String telephone,
                     String description, double latitude, double longitude, String status) {
        this.id = id;
        this.ownerId = ownerId;
        this.nom = nom;
        this.type = type;
        this.telephone = telephone;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    // getters + setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return nom + " (" + status + ")";
    }
}
