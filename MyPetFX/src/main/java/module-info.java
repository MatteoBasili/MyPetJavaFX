module com.application.mypetfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires log4j;
    requires mysql.connector.j;
    requires java.prefs;
    requires java.mail;
    requires com.jfoenix;
    requires java.desktop;
    requires javafx.swing;


    exports com.application.mypetfx.splash_screen;
    opens com.application.mypetfx.splash_screen to javafx.fxml;
    exports com.application.mypetfx.login.view;
    opens com.application.mypetfx.login.view to javafx.fxml;
    exports com.application.mypetfx.pwd_recovery.view;
    opens com.application.mypetfx.pwd_recovery.view to javafx.fxml;
    exports com.application.mypetfx.registration;
    opens com.application.mypetfx.registration to javafx.fxml;
    exports com.application.mypetfx.registration.data;
    opens com.application.mypetfx.registration.data to javafx.fxml;
    exports com.application.mypetfx.registration.view;
    opens com.application.mypetfx.registration.view to javafx.fxml;
    exports com.application.mypetfx.services;
    opens com.application.mypetfx.services to javafx.fxml;
    exports com.application.mypetfx.services.profile;
    opens com.application.mypetfx.services.profile to javafx.fxml;
    exports com.application.mypetfx.services.profile.view;
    opens com.application.mypetfx.services.profile.view to javafx.fxml;
    exports com.application.mypetfx.services.profile.data;
    opens com.application.mypetfx.services.profile.data to javafx.fxml;
    exports com.application.mypetfx.services.search.view;
    opens com.application.mypetfx.services.search.view to javafx.fxml;
    exports com.application.mypetfx.services.search.data;
    opens com.application.mypetfx.services.search.data to javafx.fxml;
    exports com.application.mypetfx.services.search;
    opens com.application.mypetfx.services.search to javafx.fxml;
    exports com.application.mypetfx.utils.singleton_examples;
    opens com.application.mypetfx.utils.singleton_examples to javafx.fxml;
    exports com.application.mypetfx;
    opens com.application.mypetfx to javafx.fxml;
}