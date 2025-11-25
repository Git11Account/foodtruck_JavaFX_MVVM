package org.example.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.repository.UserRepository;

public class RegisterViewModel {

    private final UserRepository repo = new UserRepository();

    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public StringProperty usernameProperty() { return username; }
    public StringProperty passwordProperty() { return password; }

    public boolean register() {
        return repo.register(username.get(), password.get());
    }
}
