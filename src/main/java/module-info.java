module com.pos.possystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires jasperreports;
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.testfx.junit5;
    
    opens com.pos.possystem to javafx.fxml;
    exports com.pos.possystem;
    
}
