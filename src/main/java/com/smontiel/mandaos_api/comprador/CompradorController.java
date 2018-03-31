package com.smontiel.mandaos_api.comprador;

import com.smontiel.mandaos_api.administrador_tienda.AdministradorTienda;
import com.smontiel.mandaos_api.administrador_tienda.AdministradorTiendaResponse;
import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.usuario.UsuarioController;
import com.smontiel.simple_jdbc.SimpleJDBC;
import com.smontiel.simple_jdbc.ThrowingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
@RestController
@RequestMapping(value = "/comprador")
public class CompradorController {
    @Autowired SimpleJDBC db;
    @Autowired UsuarioController usuarioController;

    @GetMapping("/{id}")
    public ResponseEntity<CompradorResponse> getComprador(@PathVariable String id) {
        String query = "select * from comprador where id = " + id + ";";
        try {
            CompradorResponse response = db.one(query, rs -> {
                CompradorResponse d = new CompradorResponse();
                d.id = rs.getLong("id");
                String idUsuario = rs.getString("id_usuario");
                d.usuario = usuarioController.getUsuario(idUsuario).getBody();
                d.telefono = rs.getString("telefono");
                d.tipoVehiculo = Vehiculo.valueOf(rs.getString("tipo_vehiculo"));

                d.createdAt = rs.getString("created_at");
                d.updatedAt = rs.getString("updated_at");

                return d;
            });
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getCause());
        }
    }

    @PostMapping("")
    public ResponseEntity<CompradorResponse> createComprador(@RequestBody Comprador rt) {
        String compradorExists = "SELECT id, id_usuario, telefono, tipo_vehiculo FROM comprador "
                + "WHERE id_usuario = '" + rt.idUsuario + "' "
                + "AND telefono = '" + rt.telefono + "' "
                + "AND tipo_vehiculo = '" + rt.tipoVehiculo + "'";
        Comprador at = db.oneOrNone(compradorExists, rs -> {
            Comprador a = new Comprador();
            a.id = rs.getLong("id");
            return a;
        });
        if (at != null) return getComprador(String.valueOf(at.id));

        String query = "INSERT INTO comprador "
                + "(id_usuario, telefono, tipo_vehiculo) "
                + "VALUES('" + rt.idUsuario + "', '" + rt.telefono + "', '" + rt.tipoVehiculo
                + "') RETURNING id";
        ResponseEntity<CompradorResponse> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getComprador(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<CompradorResponse>> getCompradores() {
        String query = "SELECT * FROM comprador";
        List<CompradorResponse> response = db.any(query, (rs) -> {
            CompradorResponse d = new CompradorResponse();
            d.id = rs.getLong("id");
            String idUsuario = rs.getString("id_usuario");
            d.usuario = usuarioController.getUsuario(idUsuario).getBody();
            d.telefono = rs.getString("telefono");
            d.tipoVehiculo = Vehiculo.valueOf(rs.getString("tipo_vehiculo"));

            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
