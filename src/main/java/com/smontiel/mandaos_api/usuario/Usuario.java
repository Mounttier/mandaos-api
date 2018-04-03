package com.smontiel.mandaos_api.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 29/mar/2018.
 */
public class Usuario {
    public Long id;
    public String nombre;
    @JsonProperty("apellido_paterno")
    public String apellidoPaterno;
    @JsonProperty("apellido_materno")
    public String apellidoMaterno;
    @JsonProperty("e_mail")
    public String email;
    @JsonProperty("url_foto")
    public String urlFoto;
    @JsonProperty("id_direccion")
    public Long idDireccion;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}

