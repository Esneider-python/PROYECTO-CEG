package Modelo;

public class ElementoEliminado {
    private int elementoId;
    private String motivoEliminacion;
    private int usuarioElimino;

    // Constructor
    public ElementoEliminado(int elementoId, String motivoEliminacion, int usuarioElimino) {
        this.elementoId = elementoId;
        this.motivoEliminacion = motivoEliminacion;
        this.usuarioElimino = usuarioElimino;
    }

    // Getters
    public int getElementoId() { return elementoId; }
    public String getMotivoEliminacion() { return motivoEliminacion; }
    public int getUsuarioElimino() { return usuarioElimino; }
}
