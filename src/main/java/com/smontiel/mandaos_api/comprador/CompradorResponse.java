package com.smontiel.mandaos_api.comprador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smontiel.mandaos_api.usuario.UsuarioResponse;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
public class CompradorResponse {
    public Long id;
    public UsuarioResponse usuario;
    public String telefono;
    @JsonProperty("tipo_vehiculo")
    public Vehiculo tipoVehiculo;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
