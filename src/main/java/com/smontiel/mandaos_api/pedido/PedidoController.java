package com.smontiel.mandaos_api.pedido;

import com.smontiel.mandaos_api.comprador.CompradorController;
import com.smontiel.mandaos_api.direccion.DireccionController;
import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.usuario.UsuarioController;
import com.smontiel.simple_jdbc.SimpleJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {
    @Autowired SimpleJDBC db;
    @Autowired CompradorController compradorController;
    @Autowired DireccionController direccionController;
    @Autowired UsuarioController usuarioController;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> getPedido(@PathVariable String id) {
        String query = "SELECT p.id, p.estado, p.id_comprador, p.id_direccion_envio, p.id_usuario, "
                + "p.created_at, p.updated_at "
                + "FROM pedido p WHERE p.id = " + id + ";";
        try {
            PedidoResponse response = db.one(query, rs -> {
                PedidoResponse d = new PedidoResponse();
                d.id = rs.getLong("id");
                d.estado = EstadoPedido.valueOf(rs.getString("estado"));
                d.comprador = compradorController.getComprador(rs.getString("id_comprador")).getBody();
                String idDireccion = rs.getString("id_direccion_envio");
                d.direccionEnvio = direccionController.getDireccion(idDireccion).getBody();
                String idUsuario = rs.getString("id_usuario");
                d.usuario = usuarioController.getUsuario(idUsuario).getBody();

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
    public ResponseEntity<PedidoResponse> createPedido(@RequestBody Pedido rt) {
        String pedidoExists = "SELECT p.id, p.estado, p.id_comprador, p.id_direccion_envio, p.id_usuario FROM pedido p "
                + "WHERE p.estado = '" + rt.estado + "' "
                + "AND p.id_comprador = '" + rt.idComprador + "' "
                + "AND p.id_direccion_envio = '" + rt.idDireccionEnvio + "' "
                + "AND p.id_usuario = '" + rt.idUsuario + "'";
        Pedido at = db.oneOrNone(pedidoExists, rs -> {
            Pedido a = new Pedido();
            a.id = rs.getLong("id");
            return a;
        });
        if (at != null) return getPedido(String.valueOf(at.id));

        String query = "INSERT INTO pedido "
                + "(estado, id_comprador, id_direccion_envio, id_usuario) "
                + "VALUES('" + rt.estado + "', '" + rt.idComprador + "', '" + rt.idDireccionEnvio
                    + "', '" + rt.idUsuario
                + "') RETURNING id";
        ResponseEntity<PedidoResponse> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getPedido(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<PedidoResponse>> getPedidos() {
        String query = "SELECT p.id, p.estado, p.id_comprador, p.id_direccion_envio, p.id_usuario, "
                + "p.created_at, p.updated_at FROM pedido p";
        List<PedidoResponse> response = db.any(query, (rs) -> {
            PedidoResponse d = new PedidoResponse();
            d.id = rs.getLong("id");
            d.estado = EstadoPedido.valueOf(rs.getString("estado"));
            d.comprador = compradorController.getComprador(rs.getString("id_comprador")).getBody();
            String idDireccion = rs.getString("id_direccion_envio");
            d.direccionEnvio = direccionController.getDireccion(idDireccion).getBody();
            String idUsuario = rs.getString("id_usuario");
            d.usuario = usuarioController.getUsuario(idUsuario).getBody();

            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
