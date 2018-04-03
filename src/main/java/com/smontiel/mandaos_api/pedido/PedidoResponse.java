package com.smontiel.mandaos_api.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smontiel.mandaos_api.comprador.CompradorResponse;
import com.smontiel.mandaos_api.direccion.Direccion;
import com.smontiel.mandaos_api.usuario.UsuarioResponse;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
public class PedidoResponse {
    public Long id;
    public EstadoPedido estado;
    public CompradorResponse comprador;
    @JsonProperty("direccion_envio")
    public Direccion direccionEnvio;
    public UsuarioResponse usuario;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
