/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.helpers.cargaMasiva;

import com.sigad.sigad.business.ProductoCategoria;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author paul
 */
public class CargaMasivaHelper {
    
    private final static Logger LOGGER = Logger.getLogger(CargaMasivaHelper.class.getName());
    
    public void generarCargaMasivaTemplate(String tablaCarga, String destinoTemplate) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(tablaCarga);
        HSSFRow rowhead = sheet.createRow(0);
        int rowIndex = 0;
        // Definimos las cabeceras
        switch(tablaCarga) {
            case CargaMasivaConstantes.TABLA_PRODUCTOCATEGORIA:
                rowhead.createCell(rowIndex).setCellValue("Nombre");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                break;
            case CargaMasivaConstantes.TABLA_VEHICULOSTIPO:
                rowhead.createCell(rowIndex).setCellValue("Nombre");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Capacidad-Volumen (m3)");
                break;
            // agregar aqui el resto de casos
            default:
                LOGGER.log(Level.WARNING, "Tabla no reconocida, abortando ....");
                return;
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(destinoTemplate);
            workbook.write(fileOut);
            fileOut.close();
            LOGGER.log(Level.INFO, String.format("Plantilla de %s creada con exito", tablaCarga));
        }
        catch(Exception ex) {
            LOGGER.log(Level.SEVERE, String.format("Error al generar plantilla de ", tablaCarga));
            System.out.print(ex);
        }
    }
    
    // implementacion de logica para cada tipo de tabla a cargar en bd, por cada registro a escanear
    private boolean SubirRegistroBD(String tablaCarga, Iterator<Cell> cellIterator, DataFormatter dataFormatter, Session session) {
        Cell cell = null;
        switch(tablaCarga) {
            case CargaMasivaConstantes.TABLA_PRODUCTOCATEGORIA:
                ProductoCategoria nuevoProdCat = new ProductoCategoria();
                nuevoProdCat.setActivo(true);   // logica de negocio
                cell = cellIterator.next();
                nuevoProdCat.setNombre(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoProdCat.setDescripcion(dataFormatter.formatCellValue(cell));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevoProdCat);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevoProdCat.getNombre()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevoProdCat.getNombre()));
                    System.out.print(he);
                    return false;
                }
            // colocar aqui los demas casos para el resto de tablas de carga masiva
            default:
                return false;
        }
    }
    
    public void CargaMasivaProceso(String archivoRuta) {
        try {
            DataFormatter dataFormatter = new DataFormatter();
            Workbook workbook = WorkbookFactory.create(new File(archivoRuta));
            Sheet sheet = workbook.getSheetAt(0);   // coge siempre la primera hoja del archivo
            Iterator<Row> rowIterator = sheet.rowIterator();
            // nos saltamos la cabecera
            rowIterator.next();
            Row row = null;
            // Abrir conexion bd
            Configuration config;
            SessionFactory sessionFactory;
            Session session;
            config = new Configuration();
            config.configure("hibernate.cfg.xml");
            sessionFactory = config.buildSessionFactory();
            session = sessionFactory.openSession();
            int casosExitosos = 0;
            int casosFallidos = 0;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (this.SubirRegistroBD(sheet.getSheetName(), cellIterator, dataFormatter, session)) casosExitosos++;
                else casosFallidos++;
            }
            session.close();
            sessionFactory.close();
            workbook.close();
            LOGGER.log(Level.INFO, "Procesamiento Finalizado");
            LOGGER.log(Level.INFO, String.format("Casos Exitosos %d", casosExitosos));
            LOGGER.log(Level.INFO, String.format("Casos Fallidos %d", casosFallidos));
        }
        catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al cargar masivamente");
            System.out.print(ex);
        }
    }
    
}
