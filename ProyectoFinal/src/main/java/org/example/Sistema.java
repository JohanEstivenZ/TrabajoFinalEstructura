package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sistema {
    private List<Solicitud> solicitudes;

    public Sistema() {
        this.solicitudes = new ArrayList<>();
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    // Cargar solicitudes desde archivos CSV
    public void cargarArchivo(String rutaArchivo) {
        ArchivoCSV archivoCSV = new ArchivoCSV(rutaArchivo);
        List<Solicitud> solicitudesArchivo = archivoCSV.cargarSolicitudes();
        solicitudes.addAll(solicitudesArchivo);
    }

    // Guardar solicitudes por criterios: embargar / inhabilitar y declarar renta / no declarar renta
    public void guardarSolicitudesPorCriterios(String rutaEmbargarDeclararRentaSi,
                                               String rutaEmbargarNoDeclararRenta,
                                               String rutaInhabilitarDeclararRentaSi,
                                               String rutaInhabilitarNoDeclararRenta,
                                               String rutaCarpetaEntrada) {
        // Filtrar solicitudes por caracterización "embargar"
        List<Solicitud> solicitudesEmbargar = obtenerSolicitudesPorCaracterizacion("embargar");

        // Filtrar solicitudes por caracterización "inhabilitar"
        List<Solicitud> solicitudesInhabilitar = obtenerSolicitudesPorCaracterizacion("inhabilitar");

        // Separar las solicitudes de embargar según declarar renta (Sí/No)
        List<Solicitud> solicitudesEmbargarDeclararRentaSi = obtenerSolicitudesPorRenta(solicitudesEmbargar, true);
        List<Solicitud> solicitudesEmbargarNoDeclararRenta = obtenerSolicitudesPorRenta(solicitudesEmbargar, false);

        // Separar las solicitudes de inhabilitar según declarar renta (Sí/No)
        List<Solicitud> solicitudesInhabilitarDeclararRentaSi = obtenerSolicitudesPorRenta(solicitudesInhabilitar, true);
        List<Solicitud> solicitudesInhabilitarNoDeclararRenta = obtenerSolicitudesPorRenta(solicitudesInhabilitar, false);

        // Limitar a los primeros 100 registros de cada lista
        solicitudesEmbargarDeclararRentaSi = solicitudesEmbargarDeclararRentaSi.stream().limit(100).collect(Collectors.toList());
        solicitudesEmbargarNoDeclararRenta = solicitudesEmbargarNoDeclararRenta.stream().limit(100).collect(Collectors.toList());
        solicitudesInhabilitarDeclararRentaSi = solicitudesInhabilitarDeclararRentaSi.stream().limit(100).collect(Collectors.toList());
        solicitudesInhabilitarNoDeclararRenta = solicitudesInhabilitarNoDeclararRenta.stream().limit(100).collect(Collectors.toList());

        // Guardar las solicitudes en los archivos correspondientes
        guardarEnArchivo(rutaEmbargarDeclararRentaSi, solicitudesEmbargarDeclararRentaSi);
        guardarEnArchivo(rutaEmbargarNoDeclararRenta, solicitudesEmbargarNoDeclararRenta);
        guardarEnArchivo(rutaInhabilitarDeclararRentaSi, solicitudesInhabilitarDeclararRentaSi);
        guardarEnArchivo(rutaInhabilitarNoDeclararRenta, solicitudesInhabilitarNoDeclararRenta);

        // Guardar los registros restantes en los archivos de origen
        guardarRegistrosRestantesEnArchivo(rutaCarpetaEntrada, solicitudesEmbargar, solicitudesInhabilitar);
    }

    private List<Solicitud> obtenerSolicitudesPorCaracterizacion(String caracterizacion) {
        return solicitudes.stream()
                .filter(s -> s.getCaracterizacion().equalsIgnoreCase(caracterizacion))
                .sorted((s1, s2) -> Integer.compare(s1.getEdad(), s2.getEdad())) // Prioridad por edad
                .collect(Collectors.toList());
    }

    private List<Solicitud> obtenerSolicitudesPorRenta(List<Solicitud> solicitudes, boolean obligadoDeclararRenta) {
        return solicitudes.stream()
                .filter(s -> s.isObligadoDeclararRenta() == obligadoDeclararRenta)
                .sorted((s1, s2) -> Integer.compare(s1.getEdad(), s2.getEdad())) // Prioridad por edad
                .collect(Collectors.toList());
    }

    // Guardar solicitudes en archivo con codificación UTF-8
    private void guardarEnArchivo(String rutaArchivo, List<Solicitud> solicitudes) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaArchivo, true), "UTF-8"))) {
            for (Solicitud solicitud : solicitudes) {
                writer.write(solicitud.toCSV() + "\n");
            }
            System.out.println("Archivo guardado: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo " + rutaArchivo + ": " + e.getMessage());
        }
    }

    private void guardarRegistrosRestantesEnArchivo(String rutaCarpetaEntrada, List<Solicitud> solicitudesEmbargar, List<Solicitud> solicitudesInhabilitar) {
        // Filtrar las solicitudes que no fueron procesadas en los primeros 100
        List<Solicitud> solicitudesRestantes = solicitudesEmbargar.stream().skip(100).collect(Collectors.toList());
        solicitudesRestantes.addAll(solicitudesInhabilitar.stream().skip(100).collect(Collectors.toList()));

        // Guardar los registros restantes de nuevo en sus archivos de origen
        for (Solicitud solicitud : solicitudesRestantes) {
            String archivoOrigen = obtenerArchivoDeOrigen(solicitud); // Obtener la ruta del archivo de origen según la solicitud
            guardarEnArchivo(archivoOrigen, List.of(solicitud)); // Guardar solo el registro restante
        }
    }

    private String obtenerArchivoDeOrigen(Solicitud solicitud) {
        // Lógica para devolver el archivo de origen adecuado
        switch (solicitud.getCaracterizacion().toLowerCase()) {
            case "embargar":
                return "SolicitudesEntrantes/Procuraduria.csv";  // Ajusta según corresponda
            case "inhabilitar":
                return "SolicitudesEntrantes/Fiscalia.csv"; // Ajusta según corresponda
            default:
                return "SolicitudesEntrantes/Contraloria.csv"; // Ajusta según corresponda
        }
    }
}

