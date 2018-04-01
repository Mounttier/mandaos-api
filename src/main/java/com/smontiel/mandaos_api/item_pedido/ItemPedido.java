package com.smontiel.mandaos_api.item_pedido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
public class ItemPedido {
    public Long id;
    public Integer cantidad;
    @JsonProperty("id_producto")
    public Long idProducto;
    @JsonProperty("id_pedido")
    public Long idPedido;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
