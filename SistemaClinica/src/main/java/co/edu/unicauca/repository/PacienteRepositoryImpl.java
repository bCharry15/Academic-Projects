/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.repository;

/**
 *
 * @author jpuen
 */
import co.edu.unicauca.database.DatabaseConnection;
import co.edu.unicauca.model.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteRepositoryImpl implements IPacienteRepository {

    private Connection getConn() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    @Override
    public void guardar(Paciente p) {
        String sql = "INSERT INTO pacientes (nombre, apellido, cedula, telefono, correo, fecha_nacimiento, diagnostico) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getCedula());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getFechaNacimiento());
            ps.setString(7, p.getDiagnostico());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Paciente p) {
        String sql = "UPDATE pacientes SET nombre=?, apellido=?, cedula=?, telefono=?, "
                   + "correo=?, fecha_nacimiento=?, diagnostico=? WHERE id=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getCedula());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getFechaNacimiento());
            ps.setString(7, p.getDiagnostico());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM pacientes WHERE id=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Paciente> buscarPorId(int id) {
        String sql = "SELECT * FROM pacientes WHERE id=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar paciente por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Paciente> buscarPorCedula(String cedula) {
        String sql = "SELECT * FROM pacientes WHERE cedula=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar paciente por cédula", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes ORDER BY apellido, nombre";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes", e);
        }
        return lista;
    }

    private Paciente mapear(ResultSet rs) throws SQLException {
        return new Paciente(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("cedula"),
            rs.getString("telefono"),
            rs.getString("correo"),
            rs.getString("fecha_nacimiento"),
            rs.getString("diagnostico")
        );
    }
}
