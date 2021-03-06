package com.smontiel.mandaos_api.direccion;

import com.smontiel.mandaos_api.error.EntityNotFoundException;
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
        try {
            String query = "SELECT d.id, d.calle, d.numero_interior, d.numero_exterior, d.colonia, "
                    + "d.codigo_postal, d.localidad, d.estado, d.created_at, d.updated_at "
                    + "FROM direccion d WHERE d.id = " + id + ";";
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
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getCause());
        }
    }

    @PostMapping("")
    public ResponseEntity<Direccion> createDireccion(@RequestBody Direccion d) {
        String direccionExists = "SELECT d.id FROM direccion d "
                + "WHERE d.calle = '" + d.calle + "' "
                + "AND d.numero_interior = '" + d.numeroInterior + "' "
                + "AND d.numero_exterior = '" + d.numeroExterior + "' "
                + "AND d.colonia = '" + d.colonia+ "' "
                + "AND d.codigo_postal = '" + d.codigoPostal + "' "
                + "AND d.localidad = '" + d.localidad + "' "
                + "AND d.estado = '" + d.estado + "'";
        Direccion at = db.oneOrNone(direccionExists, rs -> {
            Direccion a = new Direccion();
            a.id = rs.getLong("id");
            return a;
        });
        if (at != null) return getDireccion(String.valueOf(at.id));

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
        String query = "SELECT d.id, d.calle, d.numero_interior, d.numero_exterior, d.colonia, "
                + "d.codigo_postal, d.localidad, d.estado, d.created_at, d.updated_at "
                + "FROM direccion d";
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
