/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.business.helpers;

import java.text.DecimalFormat;

/**
 *
 * @author Alexandra
 */
public class GeneralHelper {

    public static Double roundTwoDecimals(double d) {
         DecimalFormat twoDForm = new DecimalFormat("#.##");
       String temp = twoDForm.format(d).replaceAll(",", ".");
       return Double.valueOf(temp);
    }

    public static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // s is not numeric
            return false;
        }
    }

}
