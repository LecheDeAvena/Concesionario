package ch.makery.address.view;

import java.io.IOException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import ch.makery.address.MainApp;
import ch.makery.address.models.Empleado;
import ch.makery.address.models.Mecanico;
import ch.makery.address.models.Mecanico_Tiene_Tarea;
import ch.makery.address.models.Tarea;
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

public class TareasPopup {
	@FXML
	Button closeBtn;
	@FXML
	Label errorLbl;
	@FXML
	Label nameLbl;
	@FXML
	Label iniLbl;
	@FXML
	Label costLbl;
	@FXML
	Label finLbl;
	@FXML
	Label clientLbl;
	@FXML
	Label vehLbl;
	@FXML
	TextArea descArea;
	@FXML
	TextArea matArea;
	
	Stage popupwindow;
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	Tarea tarea;
	
	public TareasPopup() {
	}
	
	@FXML
	private void initialize() {
	}
	
	private void initData(Tarea tar,Stage sta) {
		this.tarea=tar;
		this.popupwindow=sta;
		
		setData();
	}
	
	private void setData() {
		nameLbl.setText(tarea.getNomTar());
		iniLbl.setText(tarea.fechIniProperty().getValue());
		costLbl.setText(String.valueOf(tarea.getCosTar()));
		finLbl.setText(tarea.fechFinProperty().getValue());
		clientLbl.setText(tarea.getVehiculo().getCliente().nomAndApeCliProperty().getValue());
		vehLbl.setText(tarea.getVehiculo().cocheNameProperty().getValue());
		descArea.setText(tarea.getDesTar());
		matArea.setText(tarea.getMatTar());
	}
	
	@FXML
	private void cerrarPopup() {
		
		popupwindow.close();
	}
	
	public void displayPopup(Tarea tar){
		popupwindow=new Stage();
		      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Informacion de la tarea");
		
		FXMLLoader loader=null;
		Pane newPane=null;

		loader = new FXMLLoader(MainApp.class.getResource("view/TareasInfo_popup.fxml"));
		
		try {
			newPane = loader.load();
			TareasPopup tp=loader.getController();
			tp.initData(tar,popupwindow);
		} catch (IOException e) {
			System.out.println("Error con otra mierda del Scenebuilder que mal va");
		}
		
		Scene scene1= new Scene(newPane);
		      
		popupwindow.setScene(scene1);
		      
		popupwindow.showAndWait();
		       
	}

}
