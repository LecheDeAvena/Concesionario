package ch.makery.address.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MecanicosPopup {
	
	@FXML
	Button asignarBtn;
	@FXML
	Button cancelBtn;
	@FXML
	Label errorLbl;
	@FXML
	ListView<Mecanico> mecanicosList;
	
	Stage popupwindow;
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	Tarea tarea;
	ObservableList<Mecanico> listaResultado;
	
	public MecanicosPopup() {
	}
	
	@FXML
	private void initialize() {
		createList();
	}
	
	private void initData(Tarea tar,Stage sta) {
		this.tarea=tar;
		this.popupwindow=sta;
	}
	
	private void createList() {
		
		String hql = "FROM Mecanico";
		query = session.createQuery(hql);
		listaResultado = FXCollections.observableArrayList(query.list());
		mecanicosList.setItems(listaResultado);
		
		mecanicosList.setCellFactory(param -> new ListCell<Mecanico>(){
            @Override
            protected void updateItem(Mecanico item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getEmpleado().nomAndApeEmpProperty().getValue().toString());
                }
            }
        });
	}
	
	@FXML
	private void EnviarDatos() {
		
		
		if(mecanicosList.getSelectionModel().getSelectedItem()!=null) {

			Empleado selec=mecanicosList.getSelectionModel().getSelectedItem().getEmpleado();
			
			Mecanico_Tiene_Tarea mtt=new Mecanico_Tiene_Tarea();
			mtt.setEmpleado(selec);
			mtt.setTarea(tarea);

			
			if (!session.getTransaction().equals(TransactionStatus.ACTIVE)) {
				session.getTransaction().begin();
			}
			
			session.save(mtt);
			session.flush();
			session.getTransaction().commit();
			
			popupwindow.close();
			
		}else {
			errorLbl.setText("No se ha seleccionado ningun mecanico");
		}
	}
	
	@FXML
	private void cerrarPopup() {
		
		popupwindow.close();
	}
	
	public void displayPopup(Tarea tar){
		popupwindow=new Stage();
		      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Asignar mecanico");
		
		FXMLLoader loader=null;
		Pane newPane=null;
		try {
			loader = new FXMLLoader(MainApp.class.getResource("view/AsignarMecanicos_popup.fxml"));
			newPane = loader.load();
			MecanicosPopup mp=loader.getController();
			mp.initData(tar,popupwindow);
		} catch (IOException e) {
		}
		
		Scene scene1= new Scene(newPane);
		      
		popupwindow.setScene(scene1);
		      
		popupwindow.showAndWait();
		       
	}

}