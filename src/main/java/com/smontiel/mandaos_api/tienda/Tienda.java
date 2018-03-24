package com.smontiel.mandaos_api.tienda;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 23/mar/2018.
 */
public class Tienda {
    public Long id;
    public String nombre;
    public String descripcion;
    @JsonProperty("url_logo")
    public String urlLogo;
    @JsonProperty("id_direccion")
    public Long idDireccion;
    @JsonProperty("id_administrador_tienda")
    public Long idAdministradorTienda;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
