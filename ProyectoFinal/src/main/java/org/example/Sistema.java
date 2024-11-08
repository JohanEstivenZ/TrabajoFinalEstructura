package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sistema {
    private List<Solicitud> solicitudes;

    public Sistema() {
        this.solicitudes = new ArrayList<>();
    }
    public void cargarArchivo(String rutaArchivo) {
        ArchivoCSV archivoCSV = new ArchivoCSV(rutaArchivo);
        List<Solicitud> solicitudesArchivo = archivoCSV.cargarSolicitudes();
        System.out.println("Cargando archivo: " + rutaArchivo);
        //for (Solicitud solicitud : solicitudesArchivo) {
        //    System.out.println("Cargada solicitud: " + solicitud);
        //}
        solicitudes.addAll(solicitudesArchivo);
    }

    public void validarSolicitudes() {
        Validador validador = new Validador();
        for (Solicitud solicitud : solicitudes) {
            validador.validar(solicitud);
        }
    }
    public void guardarSolicitudesEmbargo(String rutaArchivo) {
        List<Solicitud> solicitudesEmbargar = solicitudes.stream()
                .filter(s -> "embargar".equalsIgnoreCase(s.getCaracterizacion()))
                .collect(Collectors.toList());

        //System.out.println("Guardando solicitudes para embargar, total: " + solicitudesEmbargar.size());
        //for (Solicitud solicitud : solicitudesEmbargar) {
        //    System.out.println(solicitud);
        //}

        guardarEnArchivo(rutaArchivo, solicitudesEmbargar);
    }


    // Método para guardar solicitudes con caracterización "inhabilitar"
    public void guardarSolicitudesInhabilitar(String rutaArchivo) {
        List<Solicitud> solicitudesInhabilitar = solicitudes.stream()
                .filter(s -> "inhabilitar".equalsIgnoreCase(s.getCaracterizacion()))
                .collect(Collectors.toList());

        System.out.println("Guardando solicitudes para inhabilitar, total: " + solicitudesInhabilitar.size());
       // for (Solicitud solicitud : solicitudesInhabilitar) {
       //     System.out.println(solicitud);
        //}

        guardarEnArchivo(rutaArchivo, solicitudesInhabilitar);
    }
    private void guardarEnArchivo(String rutaArchivo, List<Solicitud> solicitudes) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            // Escribir cabecera
            writer.write("TipoDocumento,Documento,NombreCompleto,Caracterizacion\n");

            // Escribir cada solicitud
            for (Solicitud solicitud : solicitudes) {
                writer.write(solicitud.getTipoDocumento() + "," +
                        solicitud.getDocumento() + "," +
                        solicitud.getNombreCompleto() + "," +
                        solicitud.getCaracterizacion() + "\n");
                //System.out.println("Escribiendo solicitud en archivo: " + solicitud);
            }
            System.out.println("Archivo guardado: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo " + rutaArchivo + ": " + e.getMessage());
        }
    }

    public void generarReporte() {
        for (Solicitud solicitud : solicitudes) {
            System.out.println("Documento: " + solicitud.getDocumento() +
                    ", Nombre: " + solicitud.getNombreCompleto() +
                    ", Estado: " + solicitud.getEstado());
        }
    }
}
