package com.smontiel.mandaos_api.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
public class Pedido {
    public Long id;
    public EstadoPedido estado;
    @JsonProperty("id_comprador")
    public Long idComprador;
    @JsonProperty("id_direccion_envio")
    public Long idDireccionEnvio;
    @JsonProperty("id_usuario")
    public Long idUsuario;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
