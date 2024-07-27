module com.edu.espol.proyecto2ped {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.edu.espol.proyecto2ped to javafx.fxml;
    exports com.edu.espol.proyecto2ped;
}
