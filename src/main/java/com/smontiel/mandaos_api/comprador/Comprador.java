package com.smontiel.mandaos_api.comprador;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 31/mar/2018.
 */
public class Comprador {
    public Long id;
    @JsonProperty("id_usuario")
    public Long idUsuario;
    public String telefono;
    @JsonProperty("tipo_vehiculo")
    public Vehiculo tipoVehiculo;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
