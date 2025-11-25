plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web")
}

dependencies {
    implementation("org.mariadb.jdbc:mariadb-java-client:3.2.0")
    implementation("com.sothawo:mapjfx:3.1.0")

    // BCrypt
    implementation("org.mindrot:jbcrypt:0.4")

    // JDBC driver mariadb already earlier
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.5")

    // MapJFX si tu l'utilises
    implementation("com.sothawo:mapjfx:2.14.0") // adapte version si nécessaire

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("org.example.App")
}
