package com.smontiel.mandaos_api.tienda;

import com.smontiel.mandaos_api.administrador_tienda.AdministradorTienda;
import com.smontiel.mandaos_api.administrador_tienda.AdministradorTiendaController;
import com.smontiel.mandaos_api.direccion.DireccionController;
import com.smontiel.mandaos_api.error.EntityNotFoundException;
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
@RequestMapping(value = "/tienda")
public class TiendaController {
    @Autowired SimpleJDBC db;
    @Autowired DireccionController direccionController;
    @Autowired AdministradorTiendaController administradorTiendaController;

    @GetMapping("/{id}")
    public ResponseEntity<TiendaResponse> getTienda(@PathVariable String id) {
        try {
            String query = "select * from tienda where id = " + id + ";";
            TiendaResponse response = db.one(query, rs -> {
                TiendaResponse d = new TiendaResponse();
                d.id = rs.getLong("id");
                d.nombre = rs.getString("nombre");
                d.descripcion = rs.getString("descripcion");
                d.urlLogo = rs.getString("url_logo");
                d.direccion = direccionController.getDireccion(rs.getString("id_direccion")).getBody();
                d.administradorTienda = administradorTiendaController.getAdministradorTienda(
                        rs.getString("id_administrador_tienda")
                ).getBody();
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
    public ResponseEntity<TiendaResponse> createTienda(@RequestBody Tienda t) {
        String tiendaExists = "SELECT id FROM tienda "
                + "WHERE nombre = '" + t.nombre + "' "
                + "AND descripcion = '" + t.descripcion + "' "
                + "AND url_logo = '" + t.urlLogo + "' "
                + "AND id_direccion = '" + t.idDireccion + "' "
                + "AND id_administrador_tienda = '" + t.idAdministradorTienda + "'";
        Tienda at = db.oneOrNone(tiendaExists, rs -> {
            Tienda a = new Tienda();
            a.id = rs.getLong("id");
            return a;
        });
        if (at != null) return getTienda(String.valueOf(at.id));

        String query = "INSERT INTO tienda "
                + "(nombre, descripcion, url_logo, id_direccion, id_administrador_tienda) "
                + "VALUES('" + t.nombre + "', '" + t.descripcion + "', '" + t.urlLogo + "', '"
                + t.idDireccion + "', '" + t.idAdministradorTienda
                + "') RETURNING id";
        ResponseEntity<TiendaResponse> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getTienda(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<Tienda>> getTiendas() {
        String query = "SELECT * FROM tienda";
        List<Tienda> response = db.any(query, (rs) -> {
            Tienda d = new Tienda();
            d.id = rs.getLong("id");
            d.nombre = rs.getString("nombre");
            d.descripcion = rs.getString("descripcion");
            d.urlLogo = rs.getString("url_logo");
            d.idDireccion = rs.getLong("id_direccion");
            d.idAdministradorTienda = rs.getLong("id_administrador_tienda");
            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
