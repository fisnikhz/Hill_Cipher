module com.example.hill_cipher {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hill_cipher to javafx.fxml;
    exports com.example.hill_cipher;
}