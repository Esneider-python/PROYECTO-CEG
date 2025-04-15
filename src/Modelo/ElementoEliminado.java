package Modelo;

import java.sql.Timestamp;

public class ElementoEliminado {
    private int idElementoEliminado;
    private int elementoId;
    private String motivoEliminacion;
    private Timestamp fechaHoraEliminacion; // generado automáticamente en la base de datos
    private int usuarioElimino;

    // Constructor vacío
    public ElementoEliminado() {}

    // Constructor para insertar (sin ID ni fecha)
    public ElementoEliminado(int elementoId, String motivoEliminacion, int usuarioElimino) {
        this.elementoId = elementoId;
        this.motivoEliminacion = motivoEliminacion;
        this.usuarioElimino = usuarioElimino;
    }

    // Constructor completo
    public ElementoEliminado(int idElementoEliminado, int elementoId, String motivoEliminacion, Timestamp fechaHoraEliminacion, int usuarioElimino) {
        this.idElementoEliminado = idElementoEliminado;
        this.elementoId = elementoId;
        this.motivoEliminacion = motivoEliminacion;
        this.fechaHoraEliminacion = fechaHoraEliminacion;
        this.usuarioElimino = usuarioElimino;
    }

    // Getters y Setters
    public int getIdElementoEliminado() {
        return idElementoEliminado;
    }

    public void setIdElementoEliminado(int idElementoEliminado) {
        this.idElementoEliminado = idElementoEliminado;
    }

    public int getElementoId() {
        return elementoId;
    }

    public void setElementoId(int elementoId) {
        this.elementoId = elementoId;
    }

    public String getMotivoEliminacion() {
        return motivoEliminacion;
    }

    public void setMotivoEliminacion(String motivoEliminacion) {
        this.motivoEliminacion = motivoEliminacion;
    }

    public Timestamp getFechaHoraEliminacion() {
        return fechaHoraEliminacion;
    }

    public void setFechaHoraEliminacion(Timestamp fechaHoraEliminacion) {
        this.fechaHoraEliminacion = fechaHoraEliminacion;
    }

    public int getUsuarioElimino() {
        return usuarioElimino;
    }

    public void setUsuarioElimino(int usuarioElimino) {
        this.usuarioElimino = usuarioElimino;
    }

    @Override
    public String toString() {
        return "ElementoEliminado{" +
                "idElementoEliminado=" + idElementoEliminado +
                ", elementoId=" + elementoId +
                ", motivoEliminacion='" + motivoEliminacion + '\'' +
                ", fechaHoraEliminacion=" + fechaHoraEliminacion +
                ", usuarioElimino=" + usuarioElimino +
                '}';
    }
}
