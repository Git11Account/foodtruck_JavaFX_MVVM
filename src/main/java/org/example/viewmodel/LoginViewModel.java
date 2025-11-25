package org.example.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.repository.UserRepository;
import org.example.model.User;

public class LoginViewModel {

    private final UserRepository repo = new UserRepository();

    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public StringProperty usernameProperty() { return username; }
    public StringProperty passwordProperty() { return password; }

    /**
     * Retourne un objet User si login OK, sinon null.
     */
    public User loginUser() {
        System.out.println("--------------------------------------------------");
        return repo.loginUserObject(username.get(), password.get());
    }
}
