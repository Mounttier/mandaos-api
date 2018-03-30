package com.smontiel.mandaos_api.administrador_tienda;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smontiel.mandaos_api.usuario.UsuarioResponse;

/**
 * Created by Salvador Montiel on 30/mar/2018.
 */
public class AdministradorTiendaResponse {
    public Long id;
    public UsuarioResponse usuario;
    public String telefono;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
