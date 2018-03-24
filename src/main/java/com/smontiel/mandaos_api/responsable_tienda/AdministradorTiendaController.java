package com.smontiel.mandaos_api.responsable_tienda;

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
@RequestMapping(value = "/administrador-tienda")
public class AdministradorTiendaController {
    @Autowired
    SimpleJDBC db;

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorTienda> getAdministradorTienda(@PathVariable String id) {
        String query = "select * from responsable_tienda d where id = " + id + ";";
        AdministradorTienda response = db.one(query, rs -> {
            AdministradorTienda d = new AdministradorTienda();
            d.id = rs.getLong("id");
            d.nombre = rs.getString("nombre");
            d.apellidoPaterno = rs.getString("apellido_paterno");
            d.apellidoMaterno = rs.getString("apellido_materno");
            d.urlFoto = rs.getString("url_foto");
            d.telefono = rs.getString("telefono");
            d.email = rs.getString("e_mail");
            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AdministradorTienda> createAdministradorTienda(@RequestBody AdministradorTienda rt) {
        String query = "INSERT INTO responsable_tienda "
                + "(nombre, apellido_paterno, apellido_materno, url_foto, telefono, e_mail) "
                + "VALUES('" + rt.nombre + "', '" + rt.apellidoPaterno + "', '" + rt.apellidoMaterno + "', '"
                + rt.urlFoto + "', '" + rt.telefono + "', '" + rt.email
                + "') RETURNING id";
        ResponseEntity<AdministradorTienda> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getAdministradorTienda(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<AdministradorTienda>> getAdministradoresDeTienda() {
        String query = "SELECT * FROM responsable_tienda";
        List<AdministradorTienda> response = db.any(query, (rs) -> {
            AdministradorTienda d = new AdministradorTienda();
            d.id = rs.getLong("id");
            d.nombre = rs.getString("nombre");
            d.apellidoPaterno = rs.getString("apellido_paterno");
            d.apellidoMaterno = rs.getString("apellido_materno");
            d.urlFoto = rs.getString("url_foto");
            d.telefono = rs.getString("telefono");
            d.email = rs.getString("e_mail");
            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
