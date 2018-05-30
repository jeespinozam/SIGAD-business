/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigad.sigad.app.repartos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import com.sigad.sigad.app.controller.LoginController;
import com.sigad.sigad.business.Vehiculo;
import static com.sigad.sigad.deposito.helper.DepositoHelper.session;
import com.sigad.sigad.validations.SIGADValidations;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author cfoch
 */
public class VehiculoCrearController implements Initializable {
    public static enum Modo {
        CREAR,
        EDITAR
    };
    public static final String VIEW_PATH =
            "/com/sigad/sigad/repartos/view/VehiculoCrear.fxml";

    @FXML
    JFXTextField nombreTxt;
    @FXML
    JFXTextField placaTxt;
    @FXML
    JFXListView<Label> tipoListView;
    @FXML
    JFXTextArea descripcionTxtArea;
    @FXML
    StackPane stackPane;

    private JFXButton crearButton;
    private Modo modo;
    private Long currentVehiculoId;

    private Map<Label, Vehiculo.Tipo> tipoListViewItems;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setModo(Modo.CREAR);
        setCurrentVehiculoId(null);

        tipoListViewItems = new HashMap<>();
        tipoListView.setVisible(true);
        try {
            populateTipoComboData();
        } catch (Exception ex) {
            Logger.getLogger(VehiculoCrearController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        populateTipoCombo();
        setupValidators();
    }

    private void populateTipoCombo() {
        tipoListViewItems.forEach((label, unused_value) -> {
            tipoListView.getItems().add(label);
        });
    }

    private void populateTipoComboData() throws Exception {
        Query query;
        String hql;
        ArrayList<Vehiculo.Tipo> objs;
        hql = "from Vehiculo$Tipo";

        System.out.println("FILLINF FCOMOBOOOBOBO");
        tipoListViewItems.clear();
        session = LoginController.serviceInit();
        query = session.createQuery(hql);
        objs = (ArrayList<Vehiculo.Tipo>) query.list();
        objs.forEach((obj) -> {
            System.out.println("Filling data for: " + obj.getNombre());
            Label label = new Label(obj.getNombre());
            tipoListViewItems.put(label, obj);
        });
    }

    private void setupValidators() {
        RequiredFieldValidator validatorRequiredPlaca;
        RequiredFieldValidator validatorRequiredNombre;
        RequiredFieldValidator validatorRequiredTipo;
        ValidationFacade comboValidationFacade = new ValidationFacade();
        ReadOnlyBooleanProperty prop;

        validatorRequiredPlaca = new RequiredFieldValidator();
        validatorRequiredPlaca.setMessage(SIGADValidations.MSG.REQUIRED);
        validatorRequiredNombre = new RequiredFieldValidator();
        validatorRequiredNombre.setMessage(SIGADValidations.MSG.REQUIRED);
        validatorRequiredTipo = new RequiredFieldValidator();
        validatorRequiredTipo.setMessage(SIGADValidations.MSG.REQUIRED);

        placaTxt.getValidators().add(validatorRequiredPlaca);
        nombreTxt.getValidators().add(validatorRequiredNombre);
        comboValidationFacade.setControl(tipoListView);

        SIGADValidations.setupSimpleValidationListener(placaTxt);
        SIGADValidations.setupSimpleValidationListener(nombreTxt);
        comboValidationFacade.getValidators().add(validatorRequiredTipo);
    }

    private void editar(Vehiculo obj) {
        obj.setNombre(nombreTxt.getText());
        obj.setPlaca(placaTxt.getText());
        obj.setDescripcion(placaTxt.getText());
        // obj.setTipo(tipo);
    }

    private Vehiculo nuevo() {
        Vehiculo ret;
        ret = new Vehiculo();
        editar(ret);
        return ret;
    }

    private boolean isValid() {
        return nombreTxt.validate() && placaTxt.validate() &&
                ValidationFacade.validate(tipoListView);
    }

    private Vehiculo queryData(Long id, Session session) throws Exception {
        Query query;
        String hql;
        Vehiculo obj;

        hql = "from Vehiculo$Tipo where id = :id";
        if (session == null){
            session = LoginController.serviceInit();
        }
        query = session.createQuery(hql).setParameter("id", id);
        obj = (Vehiculo) query.getSingleResult();
        return obj;
    }

    public void setData(Long id) throws Exception {
        Session session;
        Query query;
        Vehiculo obj;
        /*
        setCurrentVehiculoId(id);

        obj = queryData(id, null);
        nombreTxt.setText(vehiculoTipo.getNombre());

        capacidadTxt.setText(Double.toString(vehiculoTipo.getCapacidad()));
        marcaTxt.setText(vehiculoTipo.getMarca());
        modeloTxt.setText(vehiculoTipo.getModelo());
        descripcionTxtArea.setText(vehiculoTipo.getDescripcion());
        */
    }

    private void onEditarButtonClicked(ActionEvent e) {
        /*
        if (isValid()) {
            Configuration config;
            Session session;
            Transaction transaction = null;
            Vehiculo.Tipo vehiculoTipo;
            System.out.println("Updating tx");

            session = LoginController.serviceInit();
            try {
                transaction = session.beginTransaction();

                vehiculoTipo = queryData(getCurrentVehiculoId(), session);
                editar(vehiculoTipo);
                System.out.println("update vehicule tipo");
                session.update(vehiculoTipo);
                transaction.commit();
                showMsg(stackPane,
                                Dialogs.HEADINGS.EXITO,
                                Dialogs.MESSAGES.CRUD_UPDATE_SUCCESS,
                                Dialogs.BUTTON.ACEPTAR);
            } catch (Exception ex) {
                Logger.getLogger(VehiculoCrearController.class.getName())
                        .log(Level.SEVERE, null, ex);
                if (transaction != null) {
                    transaction.rollback();
                }
                showMsg(stackPane,
                        Dialogs.HEADINGS.ERROR,
                        Dialogs.MESSAGES.DB_GENERIC_ERROR,
                        Dialogs.BUTTON.ACEPTAR);
            } finally {
                session.close();
            }
        }
        */
    }

    private void onCrearButtonClicked(ActionEvent e) {
        /*
        if (isValid()) {
            Configuration config;
            Session session;
            Transaction transaction = null;
            Vehiculo.Tipo vehiculoTipo;

            session = LoginController.serviceInit();
            try {
                transaction = session.beginTransaction();

                vehiculoTipo = nuevo();
                session.save(vehiculoTipo);
                transaction.commit();
                showMsg(stackPane,
                                Dialogs.HEADINGS.EXITO,
                                Dialogs.MESSAGES.CRUD_CREATE_SUCCESS,
                                Dialogs.BUTTON.ACEPTAR);
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                showMsg(stackPane,
                        Dialogs.HEADINGS.ERROR,
                        Dialogs.MESSAGES.DB_GENERIC_ERROR,
                        Dialogs.BUTTON.ACEPTAR);
            } finally {
                session.close();
            }
        }
        */
    }

    /**
     * @return the crearButton
     */
    public JFXButton getCrearButton() {
        return crearButton;
    }

    /**
     * @param crearButton the crearButton to set
     */
    public void setCrearButton(JFXButton crearButton) {
        this.crearButton = crearButton;
        this.crearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (modo == Modo.CREAR) {
                    onCrearButtonClicked(e);
                } else if (modo == Modo.EDITAR) {
                    onEditarButtonClicked(e);
                }
            }
        });

    }

    /**
     * @return the modo
     */
    public Modo getModo() {
        return modo;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(Modo modo) {
        this.modo = modo;
    }

    /**
     * @return the currentVehiculoId
     */
    public Long getCurrentVehiculoId() {
        return currentVehiculoId;
    }

    /**
     * @param currentVehiculoId the currentVehiculoId to set
     */
    public void setCurrentVehiculoId(Long currentVehiculoId) {
        this.currentVehiculoId = currentVehiculoId;
    }
}
