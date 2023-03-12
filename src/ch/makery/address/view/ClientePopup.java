package ch.makery.address.view;

import java.io.IOException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import ch.makery.address.MainApp;
import ch.makery.address.models.Cliente;
import ch.makery.address.models.Empleado;
import ch.makery.address.models.Mecanico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientePopup {
	@FXML
	Label nombreCampo;
	@FXML
	Label apellidosCampo;
	@FXML
	Label direccionCampo;
	@FXML
	Label gmailCampo;
	@FXML
	Label telefonoCampo;
	@FXML
	Button closeBtn;

	Stage popupwindow;
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	Cliente cliente;

	public ClientePopup() {
	}

	@FXML
	private void initialize() {
	}

	private void initData(Cliente cliente, Stage stage) {
		this.cliente = cliente;
		this.popupwindow = stage;

		setData();
	}

	private void setData() {
		nombreCampo.setText(cliente.getNomCli());
		apellidosCampo.setText(cliente.getApeCli());
		direccionCampo.setText(cliente.getDirCli());
		gmailCampo.setText(cliente.getGmaCli());
		telefonoCampo.setText(cliente.getTlfCli());
	}

	@FXML
	private void cerrarPopup() {

		popupwindow.close();
	}

	public void displayPopup(Cliente cliente) {
		popupwindow = new Stage();

		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Informacion del cliente");

		FXMLLoader loader = null;
		Pane newPane = null;

		loader = new FXMLLoader(MainApp.class.getResource("view/ClienteInfo_popup.fxml"));

		try {
			newPane = loader.load();
			ClientePopup tp = loader.getController();
			tp.initData(cliente, popupwindow);
		} catch (IOException e) {
			System.out.println("Error con el Scenebuilder");
		}

		Scene scene1 = new Scene(newPane);

		popupwindow.setScene(scene1);

		popupwindow.showAndWait();

	}

}