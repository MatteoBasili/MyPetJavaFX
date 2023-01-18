module com.application.mypetfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires log4j;
    requires mysql.connector.j;
    requires java.prefs;


    opens com.application.mypetfx to javafx.fxml;
    exports com.application.mypetfx;
    exports com.application.mypetfx.splash_screen;
    opens com.application.mypetfx.splash_screen to javafx.fxml;
    exports com.application.mypetfx.login.view;
    opens com.application.mypetfx.login.view to javafx.fxml;
}