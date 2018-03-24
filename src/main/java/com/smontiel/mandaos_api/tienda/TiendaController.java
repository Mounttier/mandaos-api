package com.smontiel.mandaos_api.tienda;

import com.smontiel.simple_jdbc.SimpleJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Salvador Montiel on 23/mar/2018.
 */
@RestController
@RequestMapping(value = "/tienda")
public class TiendaController {
    @Autowired
    SimpleJDBC db;

    @GetMapping("/{id}")
    public ResponseEntity<Tienda> getTienda(@PathVariable String id) {
        String query = "select * from tienda where id = " + id + ";";
        Tienda response = db.one(query, rs -> {
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

    @PostMapping("")
    public ResponseEntity<Tienda> createTienda(@RequestBody Tienda t) {
        String query = "INSERT INTO tienda "
                + "(nombre, descripcion, url_logo, id_direccion, id_administrador_tienda) "
                + "VALUES('" + t.nombre + "', '" + t.descripcion + "', '" + t.urlLogo + "', '"
                + t.idDireccion + "', '" + t.idAdministradorTienda
                + "') RETURNING id";
        ResponseEntity<Tienda> response = db.one(query, (rs) -> {
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
