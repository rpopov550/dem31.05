module demEx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
	requires jdk.incubator.vector;
	requires org.junit.jupiter.api;
	requires junit;
    opens application to javafx.graphics, javafx.fxml, javafx.base;
}