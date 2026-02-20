package co.edu.unicauca.ui;

import co.edu.unicauca.model.Paciente;
import co.edu.unicauca.model.Usuario;
import co.edu.unicauca.repository.PacienteRepositoryImpl;
import co.edu.unicauca.service.IAuthService;
import co.edu.unicauca.service.IPacienteService;
import co.edu.unicauca.service.PacienteServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PacienteView {

    private final Usuario usuarioActivo;
    private final IAuthService authService;
    private final Stage stage;
    private final IPacienteService pacienteService;
    private TableView<Paciente> tabla;
    private ObservableList<Paciente> datos;

    public PacienteView(Usuario usuarioActivo, IAuthService authService, Stage stage) {
        this.usuarioActivo = usuarioActivo;
        this.authService = authService;
        this.stage = stage;
        this.pacienteService = new PacienteServiceImpl(new PacienteRepositoryImpl());
    }

    public void mostrar() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0d1117;");

        root.setTop(crearHeader());
        root.setCenter(crearCuerpo());

        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("Sistema Clínica — Pacientes");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        cargarDatos();
    }

    // ── Header ────────────────────────────────────────────────────────────────
    private HBox crearHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(16, 24, 16, 24));
        header.setSpacing(12);
        header.setStyle(
            "-fx-background-color: #161b22;" +
            "-fx-border-color: #30363d;" +
            "-fx-border-width: 0 0 1 0;"
        );

        Text icono = new Text("🏥");
        icono.setFont(Font.font(22));

        Text titulo = new Text("Sistema Clínica");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 20));
        titulo.setFill(Color.web("#e6edf3"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblUsuario = new Label("👤 " + usuarioActivo.getUsername() + "  [" + usuarioActivo.getRol() + "]");
        lblUsuario.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 13px;");

        Button btnSalir = new Button("Cerrar sesión");
        btnSalir.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #f85149;" +
            "-fx-border-color: #f85149;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 5 12 5 12;"
        );
        btnSalir.setOnAction(e -> new LoginView(authService, stage).mostrar());

        header.getChildren().addAll(icono, titulo, spacer, lblUsuario, btnSalir);
        return header;
    }

    // ── Cuerpo principal ──────────────────────────────────────────────────────
    private VBox crearCuerpo() {
        VBox cuerpo = new VBox(16);
        cuerpo.setPadding(new Insets(24));

        cuerpo.getChildren().addAll(crearBarraAcciones(), crearTabla());
        return cuerpo;
    }

    // ── Barra de acciones ─────────────────────────────────────────────────────
    private HBox crearBarraAcciones() {
        HBox barra = new HBox(10);
        barra.setAlignment(Pos.CENTER_LEFT);

        Text tituloSeccion = new Text("Lista de Pacientes");
        tituloSeccion.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        tituloSeccion.setFill(Color.web("#e6edf3"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnNuevo = crearBoton("＋ Nuevo Paciente", "#238636", "#2ea043");
        Button btnEditar = crearBoton("✏ Editar", "#1f6feb", "#388bfd");
        Button btnEliminar = crearBoton("🗑 Eliminar", "#b62324", "#da3633");

        // Solo ADMIN puede crear y eliminar
        btnNuevo.setDisable(!usuarioActivo.esAdmin());
        btnEliminar.setDisable(!usuarioActivo.esAdmin());

        btnNuevo.setOnAction(e -> abrirFormulario(null));
        btnEditar.setOnAction(e -> {
            Paciente seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                abrirFormulario(seleccionado);
            } else {
                mostrarAlerta("Selecciona un paciente para editar.", Alert.AlertType.WARNING);
            }
        });
        btnEliminar.setOnAction(e -> eliminarPaciente());

        barra.getChildren().addAll(tituloSeccion, spacer, btnNuevo, btnEditar, btnEliminar);
        return barra;
    }

    // ── Tabla ─────────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private TableView<Paciente> crearTabla() {
        tabla = new TableView<>();
        tabla.setStyle(
            "-fx-background-color: #161b22;" +
            "-fx-border-color: #30363d;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-table-cell-border-color: #21262d;"
        );
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabla, Priority.ALWAYS);

        TableColumn<Paciente, Integer> colId = crearColumna("ID", "id", 50);
        TableColumn<Paciente, String> colNombre = crearColumna("Nombre", "nombre", 120);
        TableColumn<Paciente, String> colApellido = crearColumna("Apellido", "apellido", 120);
        TableColumn<Paciente, String> colCedula = crearColumna("Cédula", "cedula", 110);
        TableColumn<Paciente, String> colTelefono = crearColumna("Teléfono", "telefono", 110);
        TableColumn<Paciente, String> colCorreo = crearColumna("Correo", "correo", 160);
        TableColumn<Paciente, String> colFecha = crearColumna("Fecha Nac.", "fechaNacimiento", 100);
        TableColumn<Paciente, String> colDiag = crearColumna("Diagnóstico", "diagnostico", 180);

        tabla.getColumns().addAll(colId, colNombre, colApellido, colCedula,
                                   colTelefono, colCorreo, colFecha, colDiag);

        datos = FXCollections.observableArrayList();
        tabla.setItems(datos);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.4));
        shadow.setRadius(15);
        tabla.setEffect(shadow);

        return tabla;
    }

    private <T> TableColumn<Paciente, T> crearColumna(String titulo, String propiedad, int ancho) {
        TableColumn<Paciente, T> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        col.setPrefWidth(ancho);
        col.setStyle("-fx-alignment: CENTER-LEFT;");
        return col;
    }

    // ── Formulario nuevo/editar ───────────────────────────────────────────────
    private void abrirFormulario(Paciente paciente) {
        new PacienteFormView(paciente, usuarioActivo, pacienteService, this::cargarDatos).mostrar();
    }

    // ── Eliminar ──────────────────────────────────────────────────────────────
    private void eliminarPaciente() {
        Paciente seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un paciente para eliminar.", Alert.AlertType.WARNING);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText("¿Eliminar a " + seleccionado.getNombre() + " " + seleccionado.getApellido() + "?");
        confirm.setContentText("Esta acción no se puede deshacer.");
        confirm.showAndWait().ifPresent(resp -> {
            if (resp == ButtonType.OK) {
                try {
                    pacienteService.eliminar(seleccionado.getId(), usuarioActivo);
                    cargarDatos();
                    mostrarAlerta("Paciente eliminado correctamente.", Alert.AlertType.INFORMATION);
                } catch (Exception ex) {
                    mostrarAlerta("Error: " + ex.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    // ── Cargar datos ──────────────────────────────────────────────────────────
    public void cargarDatos() {
        datos.setAll(pacienteService.listarTodos());
    }

    // ── Utilidades ────────────────────────────────────────────────────────────
    private Button crearBoton(String texto, String colorNormal, String colorHover) {
        Button btn = new Button(texto);
        String estiloBase = "-fx-background-color: %s; -fx-text-fill: white; " +
                            "-fx-background-radius: 8; -fx-cursor: hand; " +
                            "-fx-font-size: 12px; -fx-padding: 7 14 7 14;";
        btn.setStyle(String.format(estiloBase, colorNormal));
        btn.setOnMouseEntered(e -> btn.setStyle(String.format(estiloBase, colorHover)));
        btn.setOnMouseExited(e -> btn.setStyle(String.format(estiloBase, colorNormal)));
        return btn;
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}