package com.smontiel.mandaos_api.producto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Salvador Montiel on 23/mar/2018.
 */
public class Producto {
    public Long id;
    @JsonProperty("codigo_barras")
    public String codigoBarras;
    public String nombre;
    public String descripcion;
    @JsonProperty("url_imagen")
    public String urlImagen;
    public Long precio;
    public Boolean disponible;
    public Formato formato;
    @JsonProperty("id_tienda")
    public Long idTienda;

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
}
