package com.smontiel.mandaos_api.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smontiel.mandaos_api.direccion.Direccion;

/**
 * Created by Salvador Montiel on 29/mar/2018.
 */
public class UsuarioResponse {
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
    public Direccion direccion;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
