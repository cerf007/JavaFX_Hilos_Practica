module ni.edu.uam.practicahilos.javafx_hilos_practica {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.practicahilos.javafx_hilos_practica to javafx.fxml;
    exports ni.edu.uam.practicahilos.javafx_hilos_practica;
}