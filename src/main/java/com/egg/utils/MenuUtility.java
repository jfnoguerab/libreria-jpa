package com.egg.utils;

import java.util.Scanner;

public final class MenuUtility {

    private MenuUtility(){
        // Private constructor to prevent instantiation
    }

    public static void header(String title) {
        System.out.println("\n- " + title + " -\n");
    }

    public static Integer createMenuAndGetOption(Scanner scanner, String[] menu, String label) {
        
        if (menu.length > 0 && menu != null) {
            for (int i = 0; i < menu.length; i++) {
                System.out.println("  "+(i+1)+". "+menu[i]);
            }

            return solicitaNumeroMenu(scanner, label, 1, menu.length);
        }

        return null;
    }

    public static Character solicitaCharacter(Scanner scanner, String label, Character[] opcionesArr) {
        boolean isValidChar;
        Character opcionUsu = null;
        do {
            isValidChar = false;
            try {
                System.out.println(label);
                opcionUsu = scanner.nextLine().toUpperCase().charAt(0);
                if (!containsCharacter(opcionesArr, opcionUsu)) {
                    System.out.println("Opción inválida, inténtelo nuevamente.");
                } else {
                    isValidChar = true;
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Opción inválida, inténtelo nuevamente.");
            }
            
        } while (!isValidChar);

        return opcionUsu;
    }

    private static boolean containsCharacter(Character[] opcionesArr, Character chr) {
        for (Character opcion : opcionesArr) {
            if (Character.toUpperCase(opcion) == Character.toUpperCase(chr)){
                return true;
            }
        }
        return false;
    }

    public static String solicitarCadena(Scanner scanner, String label) {
        boolean isValidCadena;
        String cadena = null;
        do {
            isValidCadena = true;
            System.out.println(label);
            cadena = scanner.nextLine()
                                  .replaceAll("\s{2,}", " ") // Remplazamos por un espacio si se encuentran 2 o más espacios juntos
                                  .trim();

            // Validamos que la cadena solo contenga letras y espacios
            for (int i = 0; i < cadena.length(); i++) {
                if (!Character.isLetter(cadena.charAt(i)) && 
                    !Character.isWhitespace(cadena.charAt(i))) {
                        isValidCadena = false;
                    break;
                }
            }

            if(!isValidCadena) {
                System.out.println("Lo sentimos, el campo solo debe contener letras y espacios, NO se aceptan caracteres especiales (ñ, tildes, etc), inténtelo nuevamente.");
            }

            if (cadena.isBlank() || cadena.isEmpty()) {
                System.out.println("El campo no puede estar vacío, inténtelo nuevamente.");
                isValidCadena = false;
            }
        } while (!isValidCadena);

        return cadena;
    }
    
    public static String solicitarFechaCadena(Scanner scanner, String label) {
        boolean isValidCadena;
        String cadena = null;
        do {
            isValidCadena = true;
            System.out.println(label);
            cadena = scanner.nextLine()
                                  .replaceAll("\s{2,}", " ") // Remplazamos por un espacio si se encuentran 2 o más espacios juntos
                                  .trim();

            // Validamos que la cadena solo contenga letras y espacios
            for (int i = 0; i < cadena.length(); i++) {
                if (!Character.isDigit(cadena.charAt(i)) &&
                    cadena.charAt(i) != '-' && 
                    !Character.isWhitespace(cadena.charAt(i))) {
                        isValidCadena = false;
                    break;
                }
            }

            if(!isValidCadena) {
                System.out.println("Lo sentimos, el campo solo debe contener letras y espacios, NO se aceptan caracteres especiales (ñ, tildes, etc), inténtelo nuevamente.");
            }

            if (cadena.isBlank() || cadena.isEmpty()) {
                System.out.println("El campo no puede estar vacío, inténtelo nuevamente.");
                isValidCadena = false;
            }
        } while (!isValidCadena);

        return cadena;
    }

    public static int solicitaNumeroMenu(Scanner scanner, String label, int min, int max) {
        boolean isValidNumber;
        int numUsu = 0;
        do {
            isValidNumber = false;
            try {
                System.out.print(label);
                numUsu = Integer.parseInt(scanner.nextLine());
                if (numUsu > (min - 1) && numUsu < (max + 1)) {
                    isValidNumber = true;
                } else {
                    System.out.println("Opción inválida, solo números entre "+min+" y "+max+", inténtelo nuevamente");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, solo se aceptan números enteros, inténtelo nuevamente");
            }
        } while (!isValidNumber);

        return numUsu;
    }

    public static Double solicitaNumeroDouble(Scanner scanner, String label) {
        boolean isValidNumber;
        Double numUsu = null;
        do {
            isValidNumber = false;
            try {
                System.out.print(label);
                numUsu = Double.valueOf(scanner.nextLine());
                if (numUsu < 0) {
                    System.out.println("Valor inválido, solo se aceptan números positivos, inténtelo nuevamente");
                } else {
                    isValidNumber = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, inténtelo nuevamente");
            }
        } while (!isValidNumber);

        return numUsu;
    }
    
    public static Long solicitaNumeroLong(Scanner scanner, String label) {
        boolean isValidNumber;
        Long numUsu = null;
        do {
            isValidNumber = false;
            try {
                System.out.print(label);
                numUsu = Long.valueOf(scanner.nextLine());
                if (numUsu < 0) {
                    System.out.println("Valor inválido, solo se aceptan números positivos, inténtelo nuevamente");
                } else {
                    isValidNumber = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, inténtelo nuevamente");
            }
        } while (!isValidNumber);

        return numUsu;
    }
    
    public static Integer solicitaNumeroInteger(Scanner scanner, String label) {
        boolean isValidNumber;
        Integer numUsu = null;
        do {
            isValidNumber = false;
            try {
                System.out.print(label);
                numUsu = Integer.valueOf(scanner.nextLine());
                if (numUsu < 0) {
                    System.out.println("Valor inválido, solo se aceptan números positivos, inténtelo nuevamente");
                } else {
                    isValidNumber = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, inténtelo nuevamente");
            }
        } while (!isValidNumber);

        return numUsu;
    }

    public static boolean exit() throws Exception{
        System.out.println("\nSaliendo...\n");

        // Detiene la ejecución definido por 1 seg
        ConsoleUtility.sleepProccess(1000);

        return false;
    }
    
    public static boolean exit(String label, long millisec) throws Exception{
        System.out.println("\n "+label+" \n");

        // Detiene la ejecución definido por millisec
        ConsoleUtility.sleepProccess(millisec);

        return false;
    }

}
