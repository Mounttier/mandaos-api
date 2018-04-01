package com.smontiel.mandaos_api.usuario;

import com.smontiel.mandaos_api.direccion.DireccionController;
import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.error.FieldCollisionException;
import com.smontiel.simple_jdbc.SimpleJDBC;
import com.smontiel.simple_jdbc.ThrowingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Salvador Montiel on 29/mar/2018.
 */
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
    @Autowired SimpleJDBC db;
    @Autowired DireccionController direccionController;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable String id) {
        String query = "SELECT u.id, u.nombre, u.apellido_paterno, u.apellido_materno, u.url_foto, u.id_direccion, "
                + "u.e_mail, u.created_at, u.updated_at "
                + "FROM usuario u WHERE u.id = " + id + ";";
        try {
            UsuarioResponse response = db.one(query, rs -> {
                UsuarioResponse d = new UsuarioResponse();
                d.id = rs.getLong("id");
                d.nombre = rs.getString("nombre");
                d.apellidoPaterno = rs.getString("apellido_paterno");
                d.apellidoMaterno = rs.getString("apellido_materno");
                d.urlFoto = rs.getString("url_foto");
                d.direccion = direccionController.getDireccion(rs.getString("id_direccion")).getBody();
                d.email = rs.getString("e_mail");
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
    public ResponseEntity<UsuarioResponse> createUsuario(@RequestBody Usuario u) {
        try {
            String checkEmail = "SELECT u.e_mail FROM usuario u WHERE u.e_mail = '" + u.email + "'";
            String email = db.oneOrNone(checkEmail, new ThrowingFunction<ResultSet, String>() {
                @Override
                public String apply(ResultSet rs) throws Exception {
                    return rs.getString("e_mail");
                }
            });
            if (email != null) {
                throw new FieldCollisionException("El campo 'email' ya está registrado, usa otro");
            }
        } catch (Exception e) {
            throw new FieldCollisionException(e);
        }
        String query = "INSERT INTO usuario "
                + "(nombre, apellido_paterno, apellido_materno, url_foto, id_direccion, e_mail) "
                + "VALUES('" + u.nombre + "', '" + u.apellidoPaterno + "', '" + u.apellidoMaterno + "', '"
                + u.urlFoto + "', '" + u.idDireccion + "', '" + u.email
                + "') RETURNING id";
        ResponseEntity<UsuarioResponse> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getUsuario(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<UsuarioResponse>> getUsuarios() {
        String query = "SELECT u.id, u.nombre, u.apellido_paterno, u.apellido_materno, u.url_foto, u.id_direccion, "
                + "u.e_mail, u.created_at, u.updated_at "
                + "FROM usuario u";
        List<UsuarioResponse> response = db.any(query, (rs) -> {
            UsuarioResponse d = new UsuarioResponse();
            d.id = rs.getLong("id");
            d.nombre = rs.getString("nombre");
            d.apellidoPaterno = rs.getString("apellido_paterno");
            d.apellidoMaterno = rs.getString("apellido_materno");
            d.urlFoto = rs.getString("url_foto");
            d.email = rs.getString("e_mail");
            d.direccion = direccionController.getDireccion(rs.getString("id_direccion")).getBody();
            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
