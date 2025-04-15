package Modelo;

import java.sql.Timestamp;

public class CambioIdentificador {
    private int idCambio;
    private int idElemento;
    private String identificadorAnterior;
    private String tipoIdentificadorAnterior;
    private String identificadorNuevo;
    private String tipoIdentificadorNuevo;
    private int usuarioModifica;
    private Timestamp fechaModificacion;

    // Constructor vacío
    public CambioIdentificador() {
    }

    // Constructor completo
    public CambioIdentificador(int idCambio, int idElemento, String identificadorAnterior,
            String tipoIdentificadorAnterior,
            String identificadorNuevo, String tipoIdentificadorNuevo,
            int usuarioModifica, Timestamp fechaModificacion) {
        this.idCambio = idCambio;
        this.idElemento = idElemento;
        this.identificadorAnterior = identificadorAnterior;
        this.tipoIdentificadorAnterior = tipoIdentificadorAnterior;
        this.identificadorNuevo = identificadorNuevo;
        this.tipoIdentificadorNuevo = tipoIdentificadorNuevo;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
    }

    // Getters y Setters
    public int getIdCambio() {
        return idCambio;
    }

    public void setIdCambio(int idCambio) {
        this.idCambio = idCambio;
    }

    public int getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(int idElemento) {
        this.idElemento = idElemento;
    }

    public String getIdentificadorAnterior() {
        return identificadorAnterior;
    }

    public void setIdentificadorAnterior(String identificadorAnterior) {
        this.identificadorAnterior = identificadorAnterior;
    }

    public String getTipoIdentificadorAnterior() {
        return tipoIdentificadorAnterior;
    }

    public void setTipoIdentificadorAnterior(String tipoIdentificadorAnterior) {
        this.tipoIdentificadorAnterior = tipoIdentificadorAnterior;
    }

    public String getIdentificadorNuevo() {
        return identificadorNuevo;
    }

    public void setIdentificadorNuevo(String identificadorNuevo) {
        this.identificadorNuevo = identificadorNuevo;
    }

    public String getTipoIdentificadorNuevo() {
        return tipoIdentificadorNuevo;
    }

    public void setTipoIdentificadorNuevo(String tipoIdentificadorNuevo) {
        this.tipoIdentificadorNuevo = tipoIdentificadorNuevo;
    }

    public int getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(int usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
