package org.example;

public class Solicitud {
    private String tipoDocumento;
    private String documento;
    private String nombreCompleto;
    private String caracterizacion; // Puede ser "embargar" o "inhabilitar"
    private String estado;

    public Solicitud(String tipoDocumento, String documento, String nombreCompleto, String caracterizacion) {
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.caracterizacion = caracterizacion;
        this.estado = "Pendiente";
    }

    // Getters y setters
    public String getTipoDocumento() { return tipoDocumento; }
    public String getDocumento() { return documento; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCaracterizacion() { return caracterizacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
