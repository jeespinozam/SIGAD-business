/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.helpers.cargaMasiva;

import com.sigad.sigad.business.Insumo;
import com.sigad.sigad.business.Perfil;
import com.sigad.sigad.business.Permiso;
import com.sigad.sigad.business.ProductoCategoria;
import com.sigad.sigad.business.Proveedor;
import com.sigad.sigad.business.Tienda;
import com.sigad.sigad.business.TipoMovimiento;
import com.sigad.sigad.business.Usuario;
import com.sigad.sigad.business.Vehiculo;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Criteria;
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
    
    public static void generarCargaMasivaTemplate(String tablaCarga, String destinoTemplate) {
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
            case CargaMasivaConstantes.TABLA_PERFILES:
                rowhead.createCell(rowIndex).setCellValue("Nombre");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                break;
            case CargaMasivaConstantes.TABLA_USUARIOS:
                rowhead.createCell(rowIndex).setCellValue("Nombre(s)");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Apellido Paterno");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Apellido Materno");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Perfil");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Telefono Fijo");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("DNI");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Celular");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Correo Electronico");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Intereses");
                break;
            case CargaMasivaConstantes.TABLA_PROVEEDORES:
                rowhead.createCell(rowIndex).setCellValue("Nombre");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Ruc");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                break;
            case CargaMasivaConstantes.TABLA_INSUMOS:
                rowhead.createCell(rowIndex).setCellValue("Nombre");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Tiempo de Vida");
                break;
            case CargaMasivaConstantes.TABLA_TIENDAS:
                rowhead.createCell(rowIndex).setCellValue("Direccion");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Ubicacion, Eje X");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Ubicacion, Eje Y");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Capacidad en peso");
                break;
            case CargaMasivaConstantes.TABLA_TIPOMOV:
                rowhead.createCell(rowIndex).setCellValue("Nombre");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                break;
            case CargaMasivaConstantes.TABLA_PERMISOS:
                rowhead.createCell(rowIndex).setCellValue("Opcion");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Descripcion");
                break;
            case CargaMasivaConstantes.TABLA_PERFILXPERMISO:
                rowhead.createCell(rowIndex).setCellValue("Nombre de Perfil");
                rowIndex++;
                rowhead.createCell(rowIndex).setCellValue("Opcion de Permiso");
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
    
    // solo retornara el primero que encuentre
    private static Object busquedaGeneral(Session session, String nombreEntidad, String [] condiciones, String [] valoresCondiciones) {
        String hqlQuery = String.format("from %s where %s='%s'", nombreEntidad, condiciones[0], valoresCondiciones[0]);
        for (int i=1;i<condiciones.length;i++)
            hqlQuery += String.format(" and %s='%s'", condiciones[i], valoresCondiciones[i]);
        List<Object> resultadoBusqueda = session.createQuery(hqlQuery).list();
        return resultadoBusqueda.get(0);
    }
    
    private static Perfil buscarPerfil(Session session, String nombre) {
        String hqlQuery = String.format("from Perfil where nombre = '%s'", nombre);
        List<Perfil> busquedaPerfil= session.createQuery(hqlQuery).list();
        return busquedaPerfil.get(0);
    }
    
    // implementacion de logica para cada tipo de tabla a cargar en bd, por cada registro a escanear
    private static boolean SubirRegistroBD(String tablaCarga, Iterator<Cell> cellIterator, DataFormatter dataFormatter, Session session) {
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
            case CargaMasivaConstantes.TABLA_PERFILES:
                Perfil nuevoPerfil = new Perfil();
                nuevoPerfil.setActivo(true);    // logica de negocio
                cell = cellIterator.next();
                nuevoPerfil.setNombre(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoPerfil.setDescripcion(dataFormatter.formatCellValue(cell));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevoPerfil);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevoPerfil.getNombre()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevoPerfil.getNombre()));
                    System.out.print(he);
                    return false;
                }
            case CargaMasivaConstantes.TABLA_USUARIOS:
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setActivo(true);   // logica de negocio
                cell = cellIterator.next();
                nuevoUsuario.setNombres(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoUsuario.setApellidoPaterno(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoUsuario.setApellidoMaterno(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                String perfilNombre = dataFormatter.formatCellValue(cell);
                // Buscando el perfil elegido
                String hqlQuery = String.format("from Perfil where nombre = '%s'", perfilNombre);
                List<Perfil> busquedaPerfil= session.createQuery(hqlQuery).list();
                nuevoUsuario.setPerfil(busquedaPerfil.get(0));
                cell = cellIterator.next();
                nuevoUsuario.setTelefono(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoUsuario.setDni(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoUsuario.setCelular(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoUsuario.setCorreo(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoUsuario.setIntereses(dataFormatter.formatCellValue(cell));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevoUsuario);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevoUsuario.getNombres()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevoUsuario.getNombres()));
                    System.out.print(he);
                    return false;
                }
            case CargaMasivaConstantes.TABLA_PROVEEDORES:
                Proveedor nuevoProv = new Proveedor();
                cell = cellIterator.next();
                nuevoProv.setNombre(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoProv.setRuc(Integer.valueOf(dataFormatter.formatCellValue(cell)));
                cell = cellIterator.next();
                nuevoProv.setDescripcion(dataFormatter.formatCellValue(cell));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevoProv);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevoProv.getNombre()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevoProv.getNombre()));
                    System.out.print(he);
                    return false;
                }
            case CargaMasivaConstantes.TABLA_INSUMOS:
                Insumo nuevoInsumo = new Insumo();
                nuevoInsumo.setActivo(true);    // logica de negocio
                nuevoInsumo.setStock(0);        // logica de negocio
                nuevoInsumo.setVolumen(true);   // no estoy seguro para q sirve esta webada
                cell = cellIterator.next();
                nuevoInsumo.setNombre(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoInsumo.setDescripcion(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoInsumo.setTiempoVida(Integer.valueOf(dataFormatter.formatCellValue(cell)));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevoInsumo);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevoInsumo.getNombre()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevoInsumo.getNombre()));
                    System.out.print(he);
                    return false;
                }
            case CargaMasivaConstantes.TABLA_TIENDAS:
                Tienda nuevaTienda = new Tienda();
                cell = cellIterator.next();
                nuevaTienda.setDireccion(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevaTienda.setCooXDireccion(Float.valueOf(dataFormatter.formatCellValue(cell)));
                cell = cellIterator.next();
                nuevaTienda.setCooYDireccion(Float.valueOf(dataFormatter.formatCellValue(cell)));
                cell = cellIterator.next();
                nuevaTienda.setDescripcion(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevaTienda.setCapacidad(Float.valueOf(dataFormatter.formatCellValue(cell)));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevaTienda);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevaTienda.getDireccion()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevaTienda.getDireccion()));
                    System.out.print(he);
                    return false;
                }
            case CargaMasivaConstantes.TABLA_TIPOMOV:
                TipoMovimiento nuevoTipoMov = new TipoMovimiento();
                cell = cellIterator.next();
                nuevoTipoMov.setNombre(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoTipoMov.setDescripcion(dataFormatter.formatCellValue(cell));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevoTipoMov);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevoTipoMov.getNombre()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevoTipoMov.getNombre()));
                    System.out.print(he);
                    return false;
                }
            case CargaMasivaConstantes.TABLA_PERMISOS:
                Permiso nuevoPermiso = new Permiso();
                cell = cellIterator.next();
                nuevoPermiso.setOpcion(dataFormatter.formatCellValue(cell));
                cell = cellIterator.next();
                nuevoPermiso.setDescripcion(dataFormatter.formatCellValue(cell));
                try{
                    Transaction tx = null;
                    tx = session.beginTransaction();
                    session.save(nuevoPermiso);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga unitaria %s, exitosa", nuevoPermiso.getOpcion()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de %s", nuevoPermiso.getOpcion()));
                    System.out.print(he);
                    return false;
                }
            case CargaMasivaConstantes.TABLA_PERFILXPERMISO:
                cell = cellIterator.next();
                String perfilNombreAux = dataFormatter.formatCellValue(cell);
                String permisoOpcionAux = null;
                Perfil perfilAsociado = (Perfil) CargaMasivaHelper.busquedaGeneral(session, "Perfil", new String [] {"nombre"}, new String [] {perfilNombreAux});
                if (perfilAsociado!=null) { // si el perfil mencionado fue encontrado entonces se continua con el proceso
                    LOGGER.log(Level.INFO, String.format("Perfil %s encontrado con exito", perfilNombreAux));
                    Set<Permiso> permisosAsociados = new HashSet<>();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        permisoOpcionAux = dataFormatter.formatCellValue(cell);
                        Permiso permisoAux = (Permiso) CargaMasivaHelper.busquedaGeneral(session, "Permiso", new String [] {"opcion"}, new String [] {permisoOpcionAux});
                        if (permisoAux!=null) {
                            LOGGER.log(Level.INFO, String.format("Permiso %s encontrado con exito", permisoOpcionAux));
                            permisosAsociados.add(permisoAux);
                        }
                        else
                            LOGGER.log(Level.WARNING, String.format("Permiso %s no encontrado, este permiso no sera considerado", permisoOpcionAux));
                    }
                    perfilAsociado.setPermisos(permisosAsociados);
                }
                else {
                    LOGGER.log(Level.SEVERE, String.format("Perfil %s no encontrado, cancelando operacion", perfilNombreAux));
                    return false;
                }
                try{
                    Transaction tx = session.beginTransaction();
                    session.update(perfilAsociado);
                    tx.commit();
                    LOGGER.log(Level.INFO, String.format("Carga relacion perfil x permisos %s, exitosa", perfilAsociado.getNombre()));
                    return true;
                }
                catch(HibernateException he) {
                    LOGGER.log(Level.SEVERE, String.format("Error en carga de relaciones perfil x persmisos de %s", perfilAsociado.getNombre()));
                    System.out.print(he);
                    return false;
                }
            // colocar aqui los demas casos para el resto de tablas de carga masiva
            default:
                return false;
        }
    }
    
    public static void CargaMasivaProceso(String tablaCarga, String archivoRuta) {
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
                if (CargaMasivaHelper.SubirRegistroBD(tablaCarga, cellIterator, dataFormatter, session)) casosExitosos++;
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
