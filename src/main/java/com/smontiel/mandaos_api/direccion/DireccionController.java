package com.smontiel.mandaos_api.direccion;

import com.smontiel.simple_jdbc.SimpleJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Salvador Montiel on 22/mar/2018.
 */
@RestController
@RequestMapping(value = "/direccion")
public class DireccionController {
    @Autowired
    SimpleJDBC db;

    @GetMapping("/{id}")
    public ResponseEntity<String> getDireccion(@PathVariable String id) {
        //select array_to_json(array_agg(d)) from direccion d;
        String query = "select row_to_json(d) from direccion d where id = " + id + ";";
        String response = db.one(query, rs -> rs.getString("row_to_json"));

        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Direccion> createDireccion(@RequestBody Direccion d) {
        String query = "INSERT INTO direccion "
                + "(calle, numero_interior, numero_exterior, colonia, codigo_postal, localidad, estado) "
                + "VALUES('" + d.calle + "', '" + d.numeroInterior + "', '" + d.numeroExterior + "', '"
                + d.colonia + "', '" + d.codigoPostal + "', '" + d.localidad + "', '" + d.estado
                + "') RETURNING id";
        Direccion response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");
            d.id = id;
            return d;
        });

        return new ResponseEntity<Direccion>(response, HttpStatus.OK);
    }
}
