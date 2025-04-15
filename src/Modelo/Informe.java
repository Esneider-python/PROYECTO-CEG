package Modelo;

import java.sql.Timestamp;

public class Informe {
    private int idInforme;
    private String tipoInforme;
    private Timestamp fechaGeneracion;
    private int usuarioGenerador;

    // Constructor completo
    public Informe(int idInforme, String tipoInforme, Timestamp fechaGeneracion, int usuarioGenerador) {
        this.idInforme = idInforme;
        this.tipoInforme = tipoInforme;
        this.fechaGeneracion = fechaGeneracion;
        this.usuarioGenerador = usuarioGenerador;
    }

    // Constructor sin ID ni fecha (para insertar nuevos)
    public Informe(String tipoInforme, int usuarioGenerador) {
        this.tipoInforme = tipoInforme;
        this.usuarioGenerador = usuarioGenerador;
    }

    // Getters y Setters
    public int getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(int idInforme) {
        this.idInforme = idInforme;
    }

    public String getTipoInforme() {
        return tipoInforme;
    }

    public void setTipoInforme(String tipoInforme) {
        this.tipoInforme = tipoInforme;
    }

    public Timestamp getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Timestamp fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getUsuarioGenerador() {
        return usuarioGenerador;
    }

    public void setUsuarioGenerador(int usuarioGenerador) {
        this.usuarioGenerador = usuarioGenerador;
    }

    @Override
    public String toString() {
        return "Informe{" +
                "idInforme=" + idInforme +
                ", tipoInforme='" + tipoInforme + '\'' +
                ", fechaGeneracion=" + fechaGeneracion +
                ", usuarioGenerador=" + usuarioGenerador +
                '}';
    }
}
