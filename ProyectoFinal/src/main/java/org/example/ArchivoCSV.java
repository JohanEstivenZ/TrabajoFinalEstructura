package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoCSV {
    private String rutaArchivo;

    public ArchivoCSV(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public List<Solicitud> cargarSolicitudes() {
        List<Solicitud> solicitudes = new ArrayList<>();
        List<String> lineasRestantes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            // Lee cada línea del archivo CSV
            while ((linea = br.readLine()) != null) {
                // Divide la línea por comas (si el separador es diferente, ajústalo aquí)
                String[] datos = linea.split(",");

                // Verifica que la línea tenga el número correcto de columnas
                if (datos.length == 4) {
                    // Crea una nueva solicitud y agrégala a la lista
                    Solicitud solicitud = new Solicitud(datos[0], datos[1], datos[2], datos[3]);
                    solicitudes.add(solicitud);
                } else {
                    // Si la línea no es válida, la agregamos a la lista de líneas restantes
                    lineasRestantes.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        // Reescribe el archivo sin las líneas eliminadas
        reescribirArchivo(lineasRestantes);

        return solicitudes;
    }

    private void reescribirArchivo(List<String> lineasRestantes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (String linea : lineasRestantes) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
