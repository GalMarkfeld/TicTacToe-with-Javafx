module com.example.tictactoev4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.tictactoev4 to javafx.fxml;
    exports com.example.tictactoev4;
}