package Modelo;

import java.sql.Timestamp;

public class HistorialMovimiento {
    private int idHistorial;
    private String tipoElemento; // "mobiliario" o "tecnologico"
    private int aulaOrigen;
    private int aulaDestino;
    private int usuarioMovio;
    private Timestamp fechaMovimiento;

    // Constructor vacío
    public HistorialMovimiento() {}

    // Constructor completo
    public HistorialMovimiento(int idHistorial, String tipoElemento, int aulaOrigen, int aulaDestino, int usuarioMovio, Timestamp fechaMovimiento) {
        this.idHistorial = idHistorial;
        this.tipoElemento = tipoElemento;
        this.aulaOrigen = aulaOrigen;
        this.aulaDestino = aulaDestino;
        this.usuarioMovio = usuarioMovio;
        this.fechaMovimiento = fechaMovimiento;
    }

    // Constructor sin ID ni fecha (para insertar nuevos)
    public HistorialMovimiento(String tipoElemento, int aulaOrigen, int aulaDestino, int usuarioMovio) {
        this.tipoElemento = tipoElemento;
        this.aulaOrigen = aulaOrigen;
        this.aulaDestino = aulaDestino;
        this.usuarioMovio = usuarioMovio;
    }

    // Getters y Setters
    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getTipoElemento() {
        return tipoElemento;
    }

    public void setTipoElemento(String tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    public int getAulaOrigen() {
        return aulaOrigen;
    }

    public void setAulaOrigen(int aulaOrigen) {
        this.aulaOrigen = aulaOrigen;
    }

    public int getAulaDestino() {
        return aulaDestino;
    }

    public void setAulaDestino(int aulaDestino) {
        this.aulaDestino = aulaDestino;
    }

    public int getUsuarioMovio() {
        return usuarioMovio;
    }

    public void setUsuarioMovio(int usuarioMovio) {
        this.usuarioMovio = usuarioMovio;
    }

    public Timestamp getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Timestamp fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    @Override
    public String toString() {
        return "HistorialMovimiento{" +
                "idHistorial=" + idHistorial +
                ", tipoElemento='" + tipoElemento + '\'' +
                ", aulaOrigen=" + aulaOrigen +
                ", aulaDestino=" + aulaDestino +
                ", usuarioMovio=" + usuarioMovio +
                ", fechaMovimiento=" + fechaMovimiento +
                '}';
    }
}
