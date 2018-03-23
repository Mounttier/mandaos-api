package com.smontiel.mandaos_api.direccion;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 23/mar/2018.
 */
public class Direccion {
    public Long id;
    public String calle;
    @JsonProperty("numero_interior")
    public String numeroInterior;
    @JsonProperty("numero_exterior")
    public String numeroExterior;
    public String colonia;
    public int codigoPostal;
    public String localidad;
    public String estado;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
