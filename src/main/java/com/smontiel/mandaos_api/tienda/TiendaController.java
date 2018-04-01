package com.smontiel.mandaos_api.tienda;

import com.smontiel.mandaos_api.administrador_tienda.AdministradorTiendaController;
import com.smontiel.mandaos_api.direccion.DireccionController;
import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.producto.Formato;
import com.smontiel.mandaos_api.producto.Producto;
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
    @Autowired SimpleJDBC db;
    @Autowired DireccionController direccionController;
    @Autowired AdministradorTiendaController administradorTiendaController;

    @GetMapping("/{id}")
    public ResponseEntity<TiendaResponse> getTienda(@PathVariable String id) {
        try {
            String query = "SELECT t.id, t.nombre, t.descripcion, t.url_logo, t.id_direccion, "
                    + "t.id_administrador_tienda, t.created_at, t.updated_at "
                    + "FROM tienda t WHERE t.id = " + id + ";";
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
        String tiendaExists = "SELECT t.id FROM tienda t "
                + "WHERE t.nombre = '" + t.nombre + "' "
                + "AND t.descripcion = '" + t.descripcion + "' "
                + "AND t.url_logo = '" + t.urlLogo + "' "
                + "AND t.id_direccion = '" + t.idDireccion + "' "
                + "AND t.id_administrador_tienda = '" + t.idAdministradorTienda + "'";
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
        String query = "SELECT t.id, t.nombre, t.descripcion, t.url_logo, t.id_direccion, "
                + "t.id_administrador_tienda, t.created_at, t.updated_at "
                + "FROM tienda t";
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

    // TODO: 1/04/18 Revisit this query
    @GetMapping("/{idTienda}/productos")
    public ResponseEntity<List<Producto>> getProductos(@PathVariable String idTienda) {
        String query = "SELECT p.id, p.codigo_barras, p.nombre, p.descripcion, p.url_imagen, "
                + "p.precio, p.disponible, p.formato, p.id_tienda, p.created_at, p.updated_at "
                + "FROM producto p WHERE p.id_tienda = '" + idTienda +"'";
        List<Producto> productos = db.any(query, (rs) -> {
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

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
