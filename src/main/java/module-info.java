module org.isslibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.isslibrary to javafx.fxml;
    opens org.isslibrary.domain to javafx.base;

    exports org.isslibrary;
    exports org.isslibrary.controller;
    opens org.isslibrary.controller to javafx.fxml;
}