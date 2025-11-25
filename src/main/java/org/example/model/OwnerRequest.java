package org.example.model;

public class OwnerRequest {
    private int id;
    private int userId;
    private String nom;
    private String description;
    private String status;

    public OwnerRequest(int userId, String nom, String description) {
        this.userId = userId;
        this.nom = nom;
        this.description = description;
        this.status = "pending";
    }

    public OwnerRequest(int id, int userId, String nom, String description, String status) {
        this.id = id;
        this.userId = userId;
        this.nom = nom;
        this.description = description;
        this.status = status;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return nom + " (status: " + status + ")";
    }
}
