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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
            String linea;
            boolean esPrimeraLinea = true;

            while ((linea = br.readLine()) != null) {
                // Ignorar la cabecera
                if (esPrimeraLinea) {
                    esPrimeraLinea = false;
                    continue;
                }

                // Limpieza extra de la línea (eliminar posibles espacios)
                linea = linea.trim();

                // Verificación del formato
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    try {
                        String tipoDocumento = datos[0].trim();
                        String documento = datos[1].trim();
                        String nombreCompleto = datos[2].trim();
                        String caracterizacion = datos[3].trim();
                        int edad = Integer.parseInt(datos[4].trim());

                        // Asegúrate de que 'Si' se interprete correctamente
                        boolean declaracionRenta = datos[5].trim().equalsIgnoreCase("Si");

                        Solicitud solicitud = new Solicitud(tipoDocumento, documento, nombreCompleto, caracterizacion, edad, declaracionRenta);
                        solicitudes.add(solicitud);
                    } catch (NumberFormatException e) {
                        System.err.println("Error en formato numérico en la línea: " + linea);
                    } catch (Exception e) {
                        System.err.println("Error procesando la línea: " + linea + ", " + e.getMessage());
                    }
                } else {
                    System.err.println("Línea con formato incorrecto: " + linea);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        // Vaciar el archivo después de leer los datos
        vaciarArchivo();

        return solicitudes;
    }


    private void vaciarArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Se abre el archivo en modo de escritura y no se escribe nada, esto lo vacía
            writer.write("");  // Vacía el archivo
        } catch (IOException e) {
            System.err.println("Error al vaciar el archivo: " + e.getMessage());
        }
    }
}
