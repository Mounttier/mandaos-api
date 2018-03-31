package com.smontiel.mandaos_api.producto;

import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.tienda.Tienda;
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
@RequestMapping(value = "/producto")
public class ProductoController {
    @Autowired SimpleJDBC db;

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable String id) {
        try {
            String query = "select * from producto where id = '" + id + "';";
            Producto response = db.one(query, rs -> {
                Producto d = new Producto();
                d.id = rs.getLong("id");
                d.codigoBarras = rs.getString("codigo_barras");
                d.nombre = rs.getString("nombre");
                d.descripcion = rs.getString("descripcion");
                d.urlImagen = rs.getString("url_imagen");
                d.precio = rs.getLong("precio");
                d.disponible = rs.getBoolean("disponible");
                d.formato = Formato.valueOf(rs.getString("formato"));
                d.idTienda = rs.getLong("id_tienda");

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
    public ResponseEntity<Producto> createProducto(@RequestBody Producto t) {
        String productoExists = "SELECT id FROM producto "
                + "WHERE codigo_barras = '" + t.codigoBarras + "' "
                + "AND nombre = '" + t.nombre + "' "
                + "AND descripcion = '" + t.descripcion + "' "
                + "AND url_imagen = '" + t.urlImagen + "' "
                + "AND precio = '" + t.precio + "' "
                + "AND disponible = '" + t.disponible + "' "
                + "AND formato = '" + t.formato + "' "
                + "AND id_tienda = '" + t.idTienda + "'";
        Producto at = db.oneOrNone(productoExists, rs -> {
            Producto a = new Producto();
            a.id = rs.getLong("id");
            return a;
        });
        if (at != null) return getProducto(String.valueOf(at.id));

        String query = "INSERT INTO producto "
                + "(codigo_barras, nombre, descripcion, url_imagen, precio, disponible, formato, id_tienda) "
                + "VALUES('" + t.codigoBarras + "', '" + t.nombre + "', '" + t.descripcion + "', '" + t.urlImagen + "', '"
                + t.precio + "', '" + t.disponible + "', '" + t.formato + "', '" + t.idTienda
                + "') RETURNING id";
        ResponseEntity<Producto> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getProducto(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<Producto>> getProductos() {
        String query = "SELECT * FROM producto";
        List<Producto> response = db.any(query, (rs) -> {
            Producto d = new Producto();
            d.id = rs.getLong("id");
            d.codigoBarras = rs.getString("codigo_barras");
            d.nombre = rs.getString("nombre");
            d.descripcion = rs.getString("descripcion");
            d.urlImagen = rs.getString("url_imagen");
            d.precio = rs.getLong("precio");
            d.disponible = rs.getBoolean("disponible");
            d.formato = Formato.valueOf(rs.getString("formato"));
            d.idTienda = rs.getLong("id_tienda");

            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
