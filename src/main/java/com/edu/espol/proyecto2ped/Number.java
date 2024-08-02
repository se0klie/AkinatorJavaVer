
package com.edu.espol.proyecto2ped;

/**
 *
 * @author hailiejimenez
 */
public class Number {
    public static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }
    
    
}
