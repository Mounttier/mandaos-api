package com.smontiel.mandaos_api.comprador;

import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.usuario.UsuarioController;
import com.smontiel.simple_jdbc.SimpleJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String query = "SELECT c.id, c.id_usuario, c.telefono, c.tipo_vehiculo, "
                + "c.created_at, c.updated_at "
                + "FROM comprador c WHERE c.id = " + id + ";";
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
            throw new EntityNotFoundException(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<CompradorResponse> createComprador(@RequestBody Comprador rt) {
        String compradorExists = "SELECT c.id, c.id_usuario, c.telefono, c.tipo_vehiculo FROM comprador c "
                + "WHERE c.id_usuario = '" + rt.idUsuario + "' "
                + "AND c.telefono = '" + rt.telefono + "' "
                + "AND c.tipo_vehiculo = '" + rt.tipoVehiculo + "'";
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
        String query = "SELECT c.id, c.id_usuario, c.telefono, c.tipo_vehiculo, "
                + "c.created_at, c.updated_at "
                + "FROM comprador c";
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
