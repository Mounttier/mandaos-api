package com.smontiel.mandaos_api.item_pedido;

import com.smontiel.mandaos_api.error.EntityNotFoundException;
import com.smontiel.mandaos_api.producto.ProductoController;
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
@RequestMapping(value = "/item-pedido")
public class ItemPedidoController {
    @Autowired SimpleJDBC db;
    @Autowired ProductoController productoController;

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> getItemPedido(@PathVariable String id) {
        String query = "SELECT i.id, i.cantidad, i.id_producto, i.id_pedido, "
                + "i.created_at, i.updated_at "
                + "FROM item_pedido i WHERE i.id = '" + id + "';";
        try {
            ItemPedidoResponse response = db.one(query, rs -> {
                ItemPedidoResponse d = new ItemPedidoResponse();
                d.id = rs.getLong("id");
                d.cantidad = rs.getInt("cantidad");
                String idProducto = rs.getString("id_producto");
                d.producto = productoController.getProducto(idProducto).getBody();
                d.idPedido = rs.getLong("id_pedido");

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
    public ResponseEntity<ItemPedidoResponse> createItemPedido(@RequestBody ItemPedido rt) {
        String pedidoExists = "SELECT i.id FROM item_pedido i "
                + "WHERE i.id_producto = '" + rt.idProducto + "' "
                + "AND i.id_pedido = '" + rt.idPedido + "'";
        ItemPedido at = db.oneOrNone(pedidoExists, rs -> {
            ItemPedido a = new ItemPedido();
            a.id = rs.getLong("id");
            return a;
        });
        if (at != null) return getItemPedido(String.valueOf(at.id));

        String query = "INSERT INTO item_pedido "
                + "(cantidad, id_producto, id_pedido) "
                + "VALUES('" + rt.cantidad + "', '" + rt.idProducto + "', '" + rt.idPedido
                + "') RETURNING id";
        ResponseEntity<ItemPedidoResponse> response = db.one(query, (rs) -> {
            Long id = rs.getLong("id");

            return getItemPedido(String.valueOf(id));
        });

        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<ItemPedidoResponse>> getItemsDePedidos() {
        String query = "SELECT i.id, i.cantidad, i.id_producto, i.id_pedido, "
                + "i.created_at, i.updated_at FROM item_pedido i";
        List<ItemPedidoResponse> response = db.any(query, (rs) -> {
            ItemPedidoResponse d = new ItemPedidoResponse();
            d.id = rs.getLong("id");
            d.cantidad = rs.getInt("cantidad");
            String idProducto = rs.getString("id_producto");
            d.producto = productoController.getProducto(idProducto).getBody();
            d.idPedido = rs.getLong("id_pedido");

            d.createdAt = rs.getString("created_at");
            d.updatedAt = rs.getString("updated_at");

            return d;
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
