module com.mycompany.library.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.library.application to javafx.fxml;
    exports com.mycompany.library.application;
}
