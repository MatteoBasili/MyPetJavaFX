module com.application.mypetfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.application.mypetfx to javafx.fxml;
    exports com.application.mypetfx;
}