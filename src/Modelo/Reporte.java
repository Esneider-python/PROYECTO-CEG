package Modelo;

import java.sql.Timestamp;

public class Reporte {
    private int idReporte;
    private Timestamp fechaHoraReporte;
    private String descripcion;
    private int elementoReportado;
    private int usuarioReporta;

    // Constructor sin ID ni fecha (para insertar)
    public Reporte(String descripcion, int elementoReportado, int usuarioReporta) {
        this.descripcion = descripcion;
        this.elementoReportado = elementoReportado;
        this.usuarioReporta = usuarioReporta;
    }

    // Getters y setters
    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public Timestamp getFechaHoraReporte() {
        return fechaHoraReporte;
    }

    public void setFechaHoraReporte(Timestamp fechaHoraReporte) {
        this.fechaHoraReporte = fechaHoraReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getElementoReportado() {
        return elementoReportado;
    }

    public void setElementoReportado(int elementoReportado) {
        this.elementoReportado = elementoReportado;
    }

    public int getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(int usuarioReporta) {
        this.usuarioReporta = usuarioReporta;
    }
}
