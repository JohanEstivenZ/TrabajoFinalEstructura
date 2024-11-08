package org.example;

public class Validador {
    public void validar(Solicitud solicitud) {
        if ("embargar".equalsIgnoreCase(solicitud.getCaracterizacion())) {
            solicitud.setEstado("Embargado");
        } else if ("inhabilitar".equalsIgnoreCase(solicitud.getCaracterizacion())) {
            solicitud.setEstado("Inhabilitado");
        } else {
            solicitud.setEstado("Caracterización inválida");
        }
    }
}
