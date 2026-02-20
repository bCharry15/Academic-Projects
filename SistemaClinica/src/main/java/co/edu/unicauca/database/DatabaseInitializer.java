/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.database;

/**
 *
 * @author jpuen
 */
import co.edu.unicauca.util.PasswordUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private final Connection connection;

    public DatabaseInitializer(Connection connection) {
        this.connection = connection;
    }

    public void inicializar() throws SQLException {
        crearTablaUsuarios();
        crearTablaPacientes();
        insertarAdminPorDefecto();
    }

    private void crearTablaUsuarios() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id            INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username      TEXT    NOT NULL UNIQUE,"
                + "password_hash TEXT    NOT NULL,"
                + "rol           TEXT    NOT NULL CHECK(rol IN ('ADMIN','USER'))"
                + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    private void crearTablaPacientes() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS pacientes ("
                + "id               INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre           TEXT    NOT NULL,"
                + "apellido         TEXT    NOT NULL,"
                + "cedula           TEXT    NOT NULL UNIQUE,"
                + "telefono         TEXT,"
                + "correo           TEXT,"
                + "fecha_nacimiento TEXT,"
                + "diagnostico      TEXT"
                + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    private void insertarAdminPorDefecto() throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM usuarios WHERE username = 'admin'";
        try (Statement stmt = connection.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(checkSql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                String hashAdmin = PasswordUtil.hashear("admin123");
                String insertSql = "INSERT INTO usuarios (username, password_hash, rol) "
                        + "VALUES ('admin', '" + hashAdmin + "', 'ADMIN')";
                try (Statement s2 = connection.createStatement()) {
                    s2.execute(insertSql);
                }
            }
        }
    }
}
