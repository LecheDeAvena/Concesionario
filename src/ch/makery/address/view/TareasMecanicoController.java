package ch.makery.address.view;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import ch.makery.address.models.Empleado;
import ch.makery.address.models.Mecanico_Tiene_Tarea;
import ch.makery.address.models.Tarea;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class TareasMecanicoController {

	@FXML
	private TableView<Mecanico_Tiene_Tarea> tareasTable;
	@FXML
	private Button terminarTareaBtn;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> tareaCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> clienteCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> vehiculoCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> fEnCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> costeCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> infoCol;
	@FXML
	private Button toPendientesBtn;
	@FXML
	private Button toTerminadasBtn;

	Empleado user;
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	FilteredList<Mecanico_Tiene_Tarea> pendientes;
	FilteredList<Mecanico_Tiene_Tarea> terminadas;

	@FXML
	public void initialize() {
		
		setUser();
		setTabla();
		getDatos();
		changeToPendientes();
	}

	/**
	 * Este método sirve para obtener la información del usuario de la toolbar
	 */
	private void setUser() {
		ToolBarController tbc = new ToolBarController();
		user = tbc.getUserInfo();
	}
	
	private void setTabla() {
		tareasTable.setRowFactory( tv -> {
		    TableRow<Mecanico_Tiene_Tarea> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	Mecanico_Tiene_Tarea rowData = row.getItem();
		        	mostrarTarea(rowData.getTarea());
		        }
		    });
		    return row ;
		});
	}

	private void getDatos() {
		String hql = "FROM Mecanico_Tiene_Tarea mtt WHERE mtt.empleado.codEmp=" + user.getCodEmp();
		query = session.createQuery(hql);
		List<Mecanico_Tiene_Tarea> res = query.list();

		// Se pasa la información a una lista diferente
		ObservableList<Mecanico_Tiene_Tarea> data = FXCollections.observableArrayList(res);

		pendientes=new FilteredList<>(data, e -> e.getTarea().getFecFin()==(null));
		terminadas=new FilteredList<>(data, e -> e.getTarea().getFecFin()!=(null));
	}
	
	private void crearTablaPendientes() {
		
		tareaCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().nomTarProperty());
		clienteCol.setCellValueFactory(
				cellData -> cellData.getValue().getTarea().getVehiculo().getCliente().nomAndApeCliProperty());
		vehiculoCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().getVehiculo().cocheNameProperty());
		fEnCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().fechIniProperty());
		costeCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().cosTarProperty());
		infoCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().fechFinProperty());
		
		tareasTable.setItems(pendientes);
	}
	
	private void crearTablaFinalizadas() {
		
		tareaCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().nomTarProperty());
		clienteCol.setCellValueFactory(
				cellData -> cellData.getValue().getTarea().getVehiculo().getCliente().nomAndApeCliProperty());
		vehiculoCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().getVehiculo().cocheNameProperty());
		fEnCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().fechIniProperty());
		costeCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().cosTarProperty());
		infoCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().fechFinProperty());
		
		tareasTable.setItems(terminadas);
	}
	
	private void mostrarTarea(Tarea tarea) {
		TareasPopup tp=new TareasPopup();
		tp.displayPopup(tarea);
	}
	
	private void finishTareaBtn() {

		terminarTareaBtn.setDisable(false);
		terminarTareaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	finalizarTarea();
            }
        });
	}
	private void disableFinishTareaBtn() {

		terminarTareaBtn.setDisable(true);
	}
	
	private void finalizarTarea() {
		
		if(tareasTable.getSelectionModel().getSelectedItem()!=null) {
			
			Tarea selectedItem=tareasTable.getSelectionModel().getSelectedItem().getTarea();

			Date date = new Date();
			selectedItem.setFecFin(date);
			
			if (!session.getTransaction().equals(TransactionStatus.ACTIVE)) {
				session.getTransaction().begin();
			}
			session.update(selectedItem);
			session.getTransaction().commit();
			
		}else {
			
		}
	}
	
	@FXML
	private void changeToTerminadas() {

		toPendientesBtn.setDisable(false);
		toTerminadasBtn.setDisable(true);
		
		disableFinishTareaBtn();
		
		crearTablaFinalizadas();
	}
	
	@FXML
	private void changeToPendientes() {

		toPendientesBtn.setDisable(true);
		toTerminadasBtn.setDisable(false);
		
		finishTareaBtn();
		
		crearTablaPendientes();
	}
	
	/*
	private void cancelarFinTarea() {
		
		Tarea selectedItem=tareasTable.getSelectionModel().getSelectedItem().getTarea();
		selectedItem.setFecFin(null);
		
		session.update(selectedItem);
		session.getTransaction().commit();
	}*/
	
}