module com.pos.possystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires jasperreports;
    
    opens com.pos.possystem to javafx.fxml;
    exports com.pos.possystem;
}
