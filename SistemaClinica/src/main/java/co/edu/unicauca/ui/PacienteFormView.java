package co.edu.unicauca.ui;

import co.edu.unicauca.model.Paciente;
import co.edu.unicauca.model.Usuario;
import co.edu.unicauca.service.IPacienteService;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PacienteFormView {

    private final Paciente pacienteExistente; // null = nuevo, no null = editar
    private final Usuario usuarioActivo;
    private final IPacienteService pacienteService;
    private final Runnable onGuardar;

    public PacienteFormView(Paciente pacienteExistente, Usuario usuarioActivo,
                             IPacienteService pacienteService, Runnable onGuardar) {
        this.pacienteExistente = pacienteExistente;
        this.usuarioActivo = usuarioActivo;
        this.pacienteService = pacienteService;
        this.onGuardar = onGuardar;
    }

    public void mostrar() {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle(pacienteExistente == null ? "Nuevo Paciente" : "Editar Paciente");
        ventana.setResizable(false);

        VBox root = new VBox(16);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #0d1117;");
        root.setPrefWidth(460);

        // ── Título ────────────────────────────────────────────────────
        Text titulo = new Text(pacienteExistente == null ? "➕ Nuevo Paciente" : "✏ Editar Paciente");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 20));
        titulo.setFill(Color.web("#e6edf3"));

        // ── Campos del formulario ─────────────────────────────────────
        TextField txtNombre       = crearCampo("Nombre *");
        TextField txtApellido     = crearCampo("Apellido *");
        TextField txtCedula       = crearCampo("Cédula *");
        TextField txtTelefono     = crearCampo("Teléfono");
        TextField txtCorreo       = crearCampo("Correo electrónico");
        TextField txtFecha        = crearCampo("Fecha de nacimiento (YYYY-MM-DD)");
        TextArea  txtDiagnostico  = new TextArea();
        txtDiagnostico.setPromptText("Diagnóstico");
        txtDiagnostico.setPrefRowCount(3);
        txtDiagnostico.setStyle(estiloInput());
        txtDiagnostico.setWrapText(true);

        // Si es edición, prellenar campos
        if (pacienteExistente != null) {
            txtNombre.setText(pacienteExistente.getNombre());
            txtApellido.setText(pacienteExistente.getApellido());
            txtCedula.setText(pacienteExistente.getCedula());
            txtCedula.setDisable(true); // no se puede cambiar la cédula
            txtTelefono.setText(pacienteExistente.getTelefono());
            txtCorreo.setText(pacienteExistente.getCorreo());
            txtFecha.setText(pacienteExistente.getFechaNacimiento());
            txtDiagnostico.setText(pacienteExistente.getDiagnostico());
        }

        // ── Mensaje de error ──────────────────────────────────────────
        Label lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #f85149; -fx-font-size: 12px;");
        lblError.setVisible(false);
        lblError.setWrapText(true);

        // ── Botones ───────────────────────────────────────────────────
        Button btnGuardar  = crearBoton("Guardar", "#238636", "#2ea043");
        Button btnCancelar = crearBoton("Cancelar", "#30363d", "#484f58");

        btnCancelar.setOnAction(e -> ventana.close());

        btnGuardar.setOnAction(e -> {
            try {
                Paciente p = new Paciente(
                    pacienteExistente != null ? pacienteExistente.getId() : 0,
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtCedula.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtFecha.getText().trim(),
                    txtDiagnostico.getText().trim()
                );

                if (pacienteExistente == null) {
                    pacienteService.registrar(p, usuarioActivo);
                } else {
                    pacienteService.actualizar(p, usuarioActivo);
                }

                onGuardar.run(); // recargar tabla
                ventana.close();

            } catch (Exception ex) {
                lblError.setText("❌ " + ex.getMessage());
                lblError.setVisible(true);
            }
        });

        HBox botones = new HBox(10, btnGuardar, btnCancelar);
        botones.setAlignment(Pos.CENTER_RIGHT);

        // ── Layout ────────────────────────────────────────────────────
        root.getChildren().addAll(
            titulo,
            new Separator(),
            crearFila("Nombre *", txtNombre),
            crearFila("Apellido *", txtApellido),
            crearFila("Cédula *", txtCedula),
            crearFila("Teléfono", txtTelefono),
            crearFila("Correo", txtCorreo),
            crearFila("Fecha Nac.", txtFecha),
            crearFila("Diagnóstico", txtDiagnostico),
            lblError,
            new Separator(),
            botones
        );

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.5));
        root.setEffect(shadow);

        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.showAndWait();
    }

    // ── Utilidades ────────────────────────────────────────────────────────────
    private HBox crearFila(String etiqueta, Control campo) {
        Label lbl = new Label(etiqueta);
        lbl.setMinWidth(110);
        lbl.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 12px;");
        HBox fila = new HBox(10, lbl, campo);
        fila.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(campo, Priority.ALWAYS);
        return fila;
    }

    private TextField crearCampo(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(estiloInput());
        return tf;
    }

    private String estiloInput() {
        return "-fx-background-color: #161b22;" +
               "-fx-text-fill: #e6edf3;" +
               "-fx-prompt-text-fill: #484f58;" +
               "-fx-border-color: #30363d;" +
               "-fx-border-radius: 6;" +
               "-fx-background-radius: 6;" +
               "-fx-padding: 7 10 7 10;" +
               "-fx-font-size: 13px;";
    }

    private Button crearBoton(String texto, String colorNormal, String colorHover) {
        Button btn = new Button(texto);
        String base = "-fx-background-color: %s; -fx-text-fill: white; " +
                      "-fx-background-radius: 8; -fx-cursor: hand; " +
                      "-fx-font-size: 13px; -fx-padding: 8 20 8 20;";
        btn.setStyle(String.format(base, colorNormal));
        btn.setOnMouseEntered(e -> btn.setStyle(String.format(base, colorHover)));
        btn.setOnMouseExited(e -> btn.setStyle(String.format(base, colorNormal)));
        return btn;
    }
}