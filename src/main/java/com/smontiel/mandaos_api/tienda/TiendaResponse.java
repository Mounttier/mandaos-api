package com.smontiel.mandaos_api.tienda;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smontiel.mandaos_api.administrador_tienda.AdministradorTiendaResponse;
import com.smontiel.mandaos_api.direccion.Direccion;

/**
 * Created by Salvador Montiel on 30/mar/2018.
 */
public class TiendaResponse {
    public Long id;
    public String nombre;
    public String descripcion;
    @JsonProperty("url_logo")
    public String urlLogo;
    public Direccion direccion;
    public AdministradorTiendaResponse administradorTienda;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
