package com.smontiel.mandaos_api.direccion;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

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
    @JsonProperty("codigo_postal")
    @Size(min = 5, max = 5)
    public int codigoPostal;
    public String localidad;
    public String estado;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
