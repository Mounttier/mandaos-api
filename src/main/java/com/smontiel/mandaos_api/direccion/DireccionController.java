package com.smontiel.mandaos_api.direccion;

import com.smontiel.simple_jdbc.SimpleJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Salvador Montiel on 22/mar/2018.
 */
@RestController
@RequestMapping(value = "/direccion")
public class DireccionController {
    @Autowired
    SimpleJDBC db;

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> getDireccion(@PathVariable String id) {
        String query = "select * from direccion where id = " + id + ";";
        Direccion response = db.one(query, rs -> {
            Direccion d = new Direccion();
            d.id = rs.getLong("id");
            d.calle = rs.getString("calle");
            d.numeroInterior = rs.getString("numero_interior");
            d.numeroExterior = rs.getString("numero_exterior");
            d.colonia = rs.getString("colonia");
            d.codigoPostal = rs.getInt("codigo_postal");
            d.localidad = rs.getString("localidad");
            d.estado = rs.getString("estado");
            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Direccion> createDireccion(@RequestBody Direccion d) {
        String query = "INSERT INTO direccion "
                + "(calle, numero_interior, numero_exterior, colonia, codigo_postal, localidad, estado) "
                + "VALUES('" + d.calle + "', '" + d.numeroInterior + "', '" + d.numeroExterior + "', '"
                + d.colonia + "', '" + d.codigoPostal + "', '" + d.localidad + "', '" + d.estado
                + "') RETURNING id";
        ResponseEntity<Direccion> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getDireccion(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<Direccion>> getDirecciones() {
        String query = "SELECT * FROM direccion";
        List<Direccion> response = db.any(query, (rs) -> {
            Direccion d = new Direccion();
            d.id = rs.getLong("id");
            d.calle = rs.getString("calle");
            d.numeroInterior = rs.getString("numero_interior");
            d.numeroExterior = rs.getString("numero_exterior");
            d.colonia = rs.getString("colonia");
            d.codigoPostal = rs.getInt("codigo_postal");
            d.localidad = rs.getString("localidad");
            d.estado = rs.getString("estado");
            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<List<Direccion>>(response, HttpStatus.OK);
    }
}
