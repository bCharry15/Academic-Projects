package co.edu.unicauca;

import co.edu.unicauca.database.DatabaseConnection;
import co.edu.unicauca.database.DatabaseInitializer;
import co.edu.unicauca.repository.UsuarioRepositoryImpl;
import co.edu.unicauca.service.AuthServiceImpl;
import co.edu.unicauca.ui.LoginView;
import java.sql.Connection;
import javafx.application.Application;
import javafx.stage.Stage;

public class SistemaClinica extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Inicializar base de datos
        Connection conn = DatabaseConnection.getInstance();
        new DatabaseInitializer(conn).inicializar();

        // 2. Crear servicios
        UsuarioRepositoryImpl usuarioRepo = new UsuarioRepositoryImpl();
        AuthServiceImpl authService = new AuthServiceImpl(usuarioRepo);

        // 3. Mostrar pantalla de login
        new LoginView(authService, stage).mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}