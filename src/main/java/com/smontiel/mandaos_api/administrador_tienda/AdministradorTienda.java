package com.smontiel.mandaos_api.administrador_tienda;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 23/mar/2018.
 */
public class AdministradorTienda {
    public Long id;
    @JsonProperty("id_usuario")
    public Long idUsuario;
    public String telefono;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
