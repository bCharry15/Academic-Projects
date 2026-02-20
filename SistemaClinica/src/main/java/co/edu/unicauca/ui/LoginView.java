package co.edu.unicauca.ui;

import co.edu.unicauca.model.Usuario;
import co.edu.unicauca.service.IAuthService;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView {

    private final IAuthService authService;
    private final Stage stage;

    public LoginView(IAuthService authService, Stage stage) {
        this.authService = authService;
        this.stage = stage;
    }

    public void mostrar() {
        // ── Fondo principal ──────────────────────────────────────────
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0d1117;");

        // ── Tarjeta de login ─────────────────────────────────────────
        VBox card = new VBox(18);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40, 50, 40, 50));
        card.setMaxWidth(400);
        card.setStyle(
            "-fx-background-color: #161b22;" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #30363d;" +
            "-fx-border-radius: 16;" +
            "-fx-border-width: 1;"
        );
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.6));
        shadow.setRadius(30);
        card.setEffect(shadow);

        // ── Ícono / título ───────────────────────────────────────────
        Text icono = new Text("🏥");
        icono.setFont(Font.font(48));

        Text titulo = new Text("Sistema Clínica");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        titulo.setFill(Color.web("#e6edf3"));

        Text subtitulo = new Text("Ingresa tus credenciales");
        subtitulo.setFont(Font.font("Georgia", 13));
        subtitulo.setFill(Color.web("#8b949e"));

        // ── Campos ───────────────────────────────────────────────────
        Label lblUser = crearLabel("Usuario");
        TextField txtUsuario = crearTextField("admin");

        Label lblPass = crearLabel("Contraseña");
        PasswordField txtPassword = crearPasswordField("••••••••");

        // ── Mensaje de error ─────────────────────────────────────────
        Label lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #f85149; -fx-font-size: 12px;");
        lblError.setVisible(false);

        // ── Botón login ──────────────────────────────────────────────
        Button btnLogin = new Button("Ingresar");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setStyle(
            "-fx-background-color: #238636;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 10 0 10 0;"
        );
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(
            "-fx-background-color: #2ea043;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 10 0 10 0;"
        ));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle(
            "-fx-background-color: #238636;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 10 0 10 0;"
        ));

        // ── Acción login ─────────────────────────────────────────────
        Runnable accionLogin = () -> {
            String user = txtUsuario.getText().trim();
            String pass = txtPassword.getText();
            Optional<Usuario> resultado = authService.login(user, pass);
            if (resultado.isPresent()) {
                new PacienteView(resultado.get(), authService, stage).mostrar();
            } else {
                lblError.setText("❌ Usuario o contraseña incorrectos");
                lblError.setVisible(true);
            }
        };

        btnLogin.setOnAction(e -> accionLogin.run());
        txtPassword.setOnAction(e -> accionLogin.run());

        // ── Footer ───────────────────────────────────────────────────
        Text footer = new Text("Universidad del Cauca • 2026");
        footer.setFont(Font.font("Georgia", 11));
        footer.setFill(Color.web("#484f58"));

        card.getChildren().addAll(
            icono, titulo, subtitulo,
            new Separator(),
            lblUser, txtUsuario,
            lblPass, txtPassword,
            lblError, btnLogin,
            new Separator(),
            footer
        );

        root.getChildren().add(card);

        Scene scene = new Scene(root, 500, 600);
        stage.setTitle("Sistema Clínica — Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private Label crearLabel(String texto) {
        Label lbl = new Label(texto);
        lbl.setStyle("-fx-text-fill: #e6edf3; -fx-font-size: 13px; -fx-font-weight: bold;");
        return lbl;
    }

    private TextField crearTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(
            "-fx-background-color: #0d1117;" +
            "-fx-text-fill: #e6edf3;" +
            "-fx-prompt-text-fill: #484f58;" +
            "-fx-border-color: #30363d;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-font-size: 13px;"
        );
        return tf;
    }

    private PasswordField crearPasswordField(String prompt) {
        PasswordField pf = new PasswordField();
        pf.setPromptText(prompt);
        pf.setStyle(
            "-fx-background-color: #0d1117;" +
            "-fx-text-fill: #e6edf3;" +
            "-fx-prompt-text-fill: #484f58;" +
            "-fx-border-color: #30363d;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-font-size: 13px;"
        );
        return pf;
    }
}