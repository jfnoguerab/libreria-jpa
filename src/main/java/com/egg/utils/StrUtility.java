package com.egg.utils;

public final class StrUtility {
    private StrUtility(){
        // Private constructor to prevent instantiation
    }

    public static boolean isEmptyStr(String str) {
        return str == null || str.isEmpty() || str.isBlank();
     }
 
     public static String ucfirstStr(String str) {
         if (str.length() > 0) {
             str = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
         }
         return str;
     }
 
     public static String cleanStr(String str) {
        if (str != null) {
            return str.replaceAll("\s{2,}", " ") // Remplazamos por un espacio si se hay 2 o más espacios juntos
                      .trim(); // Eliminamos los espacios al inicio y al final de la cadena
        }
        return str;
     }
}
