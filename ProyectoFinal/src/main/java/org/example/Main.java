package org.example;

public class Main {
    public static void main(String[] args) {

        Sistema sistema = new Sistema();

        // Rutas de los archivos .csv
        sistema.cargarArchivo("Contraloria.csv");
        sistema.cargarArchivo("Fiscalia.csv");
        sistema.cargarArchivo("Procuraduria.csv");

        // Validar las solicitudes
        sistema.validarSolicitudes();

        sistema.guardarSolicitudesEmbargo("solicitudes_embargo.csv");       // Archivo para "embargar"
        sistema.guardarSolicitudesInhabilitar("solicitudes_inhabilitar.csv");
    }
}