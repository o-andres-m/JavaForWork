package com.example.validators;


public class Validators {


    public boolean isNif(String value) {
 
        boolean isValid = false;
        int i = 0;
        int caracterASCII = 0;
        char letter = ' ';
        int miDNI = 0;
        int resto = 0;
        char[] assignLetter = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X','B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        
        if(value == null) return true;
 
        if(value.length() == 9 && Character.isLetter(value.charAt(8))) {
 
            do {
                caracterASCII = value.codePointAt(i);
                isValid = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            } 
            while(i < value.length() - 1 && isValid);     
        }
 
        if(isValid) {
            letter = Character.toUpperCase(value.charAt(8));
            miDNI = Integer.parseInt(value.substring(0,8));
            resto = miDNI % 23;
            isValid = (letter == assignLetter[resto]);
        }
 
        return isValid;
    }
    
    public boolean isNotNif(String value) {
    	return !isNif(value);
    }


}
