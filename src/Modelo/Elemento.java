package Modelo;
import java.sql.Timestamp;

public class Elemento {
    private int idElemento;
    private String nombre;
    private String estado;
    private int usuarioRegistra;
    private int aulaId;
    private String identificadorUnico;
    private String tipoIdentificador;
    private Timestamp fechaCreacion;

    public Elemento() {}

    public Elemento(int idElemento, String nombre, String estado, int usuarioRegistra,
                    int aulaId, String identificadorUnico, String tipoIdentificador, Timestamp fechaCreacion) {
        this.idElemento = idElemento;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioRegistra = usuarioRegistra;
        this.aulaId = aulaId;
        this.identificadorUnico = identificadorUnico;
        this.tipoIdentificador = tipoIdentificador;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public int getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(int idElemento) {
        this.idElemento = idElemento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(int usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }

    public int getAulaId() {
        return aulaId;
    }

    public void setAulaId(int aulaId) {
        this.aulaId = aulaId;
    }

    public String getIdentificadorUnico() {
        return identificadorUnico;
    }

    public void setIdentificadorUnico(String identificadorUnico) {
        this.identificadorUnico = identificadorUnico;
    }

    public String getTipoIdentificador() {
        return tipoIdentificador;
    }

    public void setTipoIdentificador(String tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
