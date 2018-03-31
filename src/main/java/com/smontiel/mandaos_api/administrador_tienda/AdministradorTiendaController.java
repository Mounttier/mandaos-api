package com.smontiel.mandaos_api.administrador_tienda;

import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.error.FieldCollisionException;
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
 * Created by Salvador Montiel on 23/mar/2018.
 */
@RestController
@RequestMapping(value = "/administrador-tienda")
public class AdministradorTiendaController {
    @Autowired SimpleJDBC db;
    @Autowired UsuarioController usuarioController;

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorTiendaResponse> getAdministradorTienda(@PathVariable String id) {
        String query = "select * from administrador_tienda where id = " + id + ";";
        try {
            AdministradorTiendaResponse response = db.one(query, rs -> {
                AdministradorTiendaResponse d = new AdministradorTiendaResponse();
                d.id = rs.getLong("id");
                String idUsuario = rs.getString("id_usuario");
                d.usuario = usuarioController.getUsuario(idUsuario).getBody();
                d.telefono = rs.getString("telefono");

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
    public ResponseEntity<AdministradorTiendaResponse> createAdministradorTienda(@RequestBody AdministradorTienda rt) {
        String adminExists = "SELECT id, id_usuario, telefono FROM administrador_tienda "
                + "WHERE id_usuario = '" + rt.idUsuario
                + "' AND telefono = '" + rt.telefono + "'";
        AdministradorTienda at = db.oneOrNone(adminExists, new ThrowingFunction<ResultSet, AdministradorTienda>() {
            @Override
            public AdministradorTienda apply(ResultSet rs) throws Exception {
                AdministradorTienda a = new AdministradorTienda();
                a.id = rs.getLong("id");
                a.telefono = rs.getString("telefono");
                return a;
            }
        });
        if (at != null) return getAdministradorTienda(String.valueOf(at.id));

        String query = "INSERT INTO administrador_tienda "
                + "(id_usuario, telefono) "
                + "VALUES('" + rt.idUsuario + "', '" + rt.telefono
                + "') RETURNING id";
        ResponseEntity<AdministradorTiendaResponse> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getAdministradorTienda(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<AdministradorTiendaResponse>> getAdministradoresDeTienda() {
        String query = "SELECT * FROM administrador_tienda";
        List<AdministradorTiendaResponse> response = db.any(query, (rs) -> {
            AdministradorTiendaResponse d = new AdministradorTiendaResponse();
            d.id = rs.getLong("id");
            String idUsuario = rs.getString("id_usuario");
            d.usuario = usuarioController.getUsuario(idUsuario).getBody();
            d.telefono = rs.getString("telefono");

            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
