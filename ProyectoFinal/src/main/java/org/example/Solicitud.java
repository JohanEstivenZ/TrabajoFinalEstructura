package org.example;

public class Solicitud {
    private String tipoDocumento;
    private String documento;
    private String nombreCompleto;
    private String caracterizacion;
    private int edad;
    private boolean obligadoDeclararRenta;
    private String estado;

    public Solicitud(String tipoDocumento, String documento, String nombreCompleto, String caracterizacion, int edad, boolean obligadoDeclararRenta) {
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.caracterizacion = caracterizacion;
        this.edad = edad;
        this.obligadoDeclararRenta = obligadoDeclararRenta;
    }

    // Getters y Setters
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCaracterizacion() {
        return caracterizacion;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isObligadoDeclararRenta() {
        return obligadoDeclararRenta;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Convertir la solicitud a CSV
    public String toCSV() {
        // Asegurarse de que el valor se convierte correctamente a "Sí" o "No"
        String declaracionRenta = obligadoDeclararRenta ? "Sí" : "No";
        return tipoDocumento + "," + documento + "," + nombreCompleto + "," +
                caracterizacion + "," + edad + "," + declaracionRenta;
    }
}
