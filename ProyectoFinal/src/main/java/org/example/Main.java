package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Ruta de la carpeta que contiene los archivos de entrada
        String rutaCarpetaEntrada = "SolicitudesEntrantes"; // Carpeta donde están los archivos CSV
        // Ruta de la carpeta donde se guardarán los archivos procesados
        String rutaCarpetaSalida = "SolicitudesEnProcesamiento"; // Carpeta donde se guardarán los archivos procesados

        // Crear una instancia del Sistema para gestionar las solicitudes
        Sistema sistema = new Sistema();

        // Obtener todos los archivos CSV en la carpeta de entrada
        File carpetaEntrada = new File(rutaCarpetaEntrada);
        File[] archivos = carpetaEntrada.listFiles((dir, name) -> name.endsWith(".csv")); // Filtrar solo archivos .csv

        // Verificar si se encontraron archivos
        if (archivos != null) {
            for (File archivo : archivos) {
                // Cargar solicitudes desde cada archivo CSV
                sistema.cargarArchivo(archivo.getAbsolutePath());
            }
        } else {
            System.out.println("No se encontraron archivos CSV en la carpeta: " + rutaCarpetaEntrada);
        }

        // Crear la carpeta de salida si no existe
        File carpetaSalida = new File(rutaCarpetaSalida);
        if (!carpetaSalida.exists()) {
            carpetaSalida.mkdirs();
        }

        // Definir las rutas de los archivos de salida dentro de la carpeta "SolicitudesEnProcesamiento"
        String rutaEmbargarDeclararRentaSi = rutaCarpetaSalida + "/embargar_declarar_renta.csv";
        String rutaEmbargarNoDeclararRenta = rutaCarpetaSalida + "/embargar_no_declarar_renta.csv";
        String rutaInhabilitarDeclararRentaSi = rutaCarpetaSalida + "/inhabilitar_declarar_renta.csv";
        String rutaInhabilitarNoDeclararRenta = rutaCarpetaSalida + "/inhabilitar_no_declarar_renta.csv";

        // Guardar las solicitudes en los 4 archivos separados por los criterios
        sistema.guardarSolicitudesPorCriterios(rutaEmbargarDeclararRentaSi,
                rutaEmbargarNoDeclararRenta,
                rutaInhabilitarDeclararRentaSi,
                rutaInhabilitarNoDeclararRenta,
                rutaCarpetaEntrada); // Paso la ruta de los archivos de entrada

        System.out.println("Proceso completado. Los archivos de salida han sido actualizados.");
    }
}