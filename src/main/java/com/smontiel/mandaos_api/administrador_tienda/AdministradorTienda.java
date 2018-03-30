package com.smontiel.mandaos_api.responsable_tienda;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 23/mar/2018.
 */
public class AdministradorTienda {
    public Long id;
    public String nombre;
    @JsonProperty("apellido_paterno")
    public String apellidoPaterno;
    @JsonProperty("apellido_materno")
    public String apellidoMaterno;
    @JsonProperty("url_foto")
    public String urlFoto;
    public String telefono;
    @JsonProperty("e_mail")
    public String email;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
