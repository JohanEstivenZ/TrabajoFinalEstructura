package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LimpiezaDatos {
    public static List<Solicitud> eliminarDuplicados(List<Solicitud> solicitudes) {
        Set<Solicitud> solicitudSet = new HashSet<>(solicitudes);
        return new ArrayList<>(solicitudSet);
    }
}


