/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.helpers.cargaMasiva;

import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;

/**
 *
 * @author paul
 */
public class CargaMasivaHelper {
    
    private final static Logger LOGGER = Logger.getLogger(CargaMasivaHelper.class.getName());
    
    public void generarCargaMasivaTemplate(String destinoTemplate) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("CargaMasivaTemplate");
            // definimos cabecera, es decir, las columnas
            HSSFRow rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Nombre");
            rowhead.createCell(1).setCellValue("Descripcion");
            //
            FileOutputStream fileOut = new FileOutputStream(destinoTemplate);
            workbook.write(fileOut);
            fileOut.close();
            LOGGER.log(Level.INFO, "Archivo creado con exito");
        }
        catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "Error en generar template de carga masiva");
            System.out.print(ex);
        }
    }
    
}
