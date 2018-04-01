package com.smontiel.mandaos_api.item_pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smontiel.mandaos_api.producto.Producto;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
public class ItemPedidoResponse {
    public Long id;
    public Integer cantidad;
    public Producto producto;
    @JsonProperty("id_pedido")
    public Long idPedido;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
