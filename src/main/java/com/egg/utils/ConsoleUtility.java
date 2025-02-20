package com.egg.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public final class ConsoleUtility {

    private ConsoleUtility(){
        // Private constructor to prevent instantiation
    }

    public static void cleanScreen(){
        // Limpia la consola (ANSI escape code)
        System.out.print("\033[H\033[2J");   
        System.out.flush();
    }

    public static void waitPressEnterKey(Scanner scanner) {
        //Para pausar la ejecución del programa
        System.out.print("\nPresione ENTER para volver al menú..."); //Mensaje en pantalla
        scanner.nextLine(); //Ahora el programa se detiene hasta que se pulse ENTER
    }
    
    public static void waitPressEnterKey(Scanner scanner, String message) {
        //Para pausar la ejecución del programa
        System.out.print(message); //Mensaje en pantalla
        scanner.nextLine(); //Ahora el programa se detiene hasta que se pulse ENTER
    }

    public static void sleepProccess(long millisec) throws Exception {
        try {
            // Detiene la ejecución por milisegundos
            // Ej: 1000 milisegundos = 1 seg
            Thread.sleep(millisec);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Método para imprimir listados de objetos en forma de tabla
    public static <T> void imprimirListado(List<T> lista, String... atributos) {
        if (lista.isEmpty()) {
            System.out.println("La lista está vacía.");
            return;
        }

        // Calcular anchos de columna
        int[] anchos = new int[atributos.length];
        for (int i = 0; i < atributos.length; i++) {
            anchos[i] = atributos[i].length(); // Longitud mínima: nombre del atributo
        }

        String[] tempAtributos = atributos.clone();

        // Obtener valores y ajustar anchos
        String[][] valores = new String[lista.size()][tempAtributos.length];
        for (int i = 0; i < lista.size(); i++) {
            T objeto = lista.get(i);
            for (int j = 0; j < tempAtributos.length; j++) {
                try {
                    Object valor = getMethodValue(objeto, tempAtributos[j]);
                    if (tempAtributos[j].contains(".")) {
                        String[] atr = tempAtributos[j].split("\\.");
                        atr[0] = StrUtility.ucfirstStr(atr[0]);
                        atr[1] = StrUtility.ucfirstStr(atr[1]);
                        atributos[j] = String.join(" - ", atr);
                        anchos[j] = atributos[j].length(); // Actualizamos Longitud mínima
                    } else {
                        atributos[j] = StrUtility.ucfirstStr(tempAtributos[j]);
                    }
                    valores[i][j] = valor != null ? valor.toString() : "N/A";
                } catch (Exception e) {
                    valores[i][j] = "N/A";
                }
                anchos[j] = Math.max(anchos[j], valores[i][j].length());
            }
        }

        // Imprimir encabezado
        StringBuilder formato = new StringBuilder();
        for (int ancho : anchos) {
            formato.append("| %-" + ancho + "s ");
        }
        formato.append("|%n");

        String header = String.format(formato.toString(), (Object[]) atributos);
        System.out.println();
        System.out.println("-".repeat(header.length() - 2));
        System.out.print(header);
        System.out.println("-".repeat(header.length() - 2));

        // Imprimir filas
        for (String[] fila : valores) {
            System.out.printf(formato.toString(), (Object[]) fila);
        }

        System.out.println("-".repeat(header.length() - 2));
        System.out.println();
    }
    
    // Método para imprimir entidad en forma de tabla
    public static <T> void imprimirEntidad(T objeto, String... atributos) {
        if (objeto == null) {
            System.out.println("La entidad está vacía.");
            return;
        }

        // Calcular anchos de columna
        int[] anchos = new int[atributos.length];
        for (int i = 0; i < atributos.length; i++) {
            anchos[i] = atributos[i].length(); // Longitud mínima: nombre del atributo
        }

        String[] tempAtributos = atributos.clone();

        // Obtener valores y ajustar anchos
        String[] valores = new String[tempAtributos.length];

        for (int j = 0; j < tempAtributos.length; j++) {
            try {
                Object valor = getMethodValue(objeto, tempAtributos[j]);
                if (tempAtributos[j].contains(".")) {
                    String[] atr = tempAtributos[j].split("\\.");
                    atr[0] = StrUtility.ucfirstStr(atr[0]);
                    atr[1] = StrUtility.ucfirstStr(atr[1]);
                    atributos[j] = String.join(" - ", atr);
                    anchos[j] = atributos[j].length(); // Actualizamos Longitud mínima
                } else {
                    atributos[j] = StrUtility.ucfirstStr(tempAtributos[j]);
                }
                valores[j] = valor != null ? valor.toString() : "N/A";
            } catch (Exception e) {
                valores[j] = "N/A";
            }
            anchos[j] = Math.max(anchos[j], valores[j].length());
        }

        // Imprimir encabezado
        StringBuilder formato = new StringBuilder();
        for (int ancho : anchos) {
            formato.append("| %-" + ancho + "s ");
        }
        formato.append("|%n");

        String header = String.format(formato.toString(), (Object[]) atributos);
        System.out.println();
        System.out.println("-".repeat(header.length() - 2));
        System.out.print(header);
        System.out.println("-".repeat(header.length() - 2));

        // Imprimir fila
        System.out.printf(formato.toString(), (Object[]) valores);

        System.out.println("-".repeat(header.length() - 2));
        System.out.println();
    }

    private static Object getMethodValue(Object objeto, String atributo) throws Exception {
        if (atributo.contains(".")) {
            String[] atr = atributo.split("\\.");
            String nombreMetodo = "get" + StrUtility.ucfirstStr(atr[0]);
            Method metodo = objeto.getClass().getMethod(nombreMetodo);
            return getMethodValue(metodo.invoke(objeto), atr[1]);
        }
        String nombreMetodo = "get" + StrUtility.ucfirstStr(atributo);
        Method metodo = objeto.getClass().getMethod(nombreMetodo);
        return metodo.invoke(objeto);
    }
}
