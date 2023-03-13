package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Empleado;
import ch.makery.address.models.Mecanico;
import ch.makery.address.models.Tarea;
import ch.makery.address.models.Vehiculo;
import ch.makery.address.models.Ventas;
import ch.makery.address.models.Ventas_Vende_Vehiculo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListaVehiculosController {
	@FXML
	private TableView<Vehiculo> vehTable;
	@FXML
	TableColumn<Vehiculo, String> modelColumn;
	@FXML
	TableColumn<Vehiculo, String> marcaColumn;
	@FXML
	TableColumn<Vehiculo, String> fecColumn;
	@FXML
	TableColumn<Vehiculo, String> estadoColumn;
	
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	ObservableList<Vehiculo> listaVehiculos;
	List<Tarea> tareas;
	List<Ventas_Vende_Vehiculo> ventas;
	
	@FXML
	private void initialize() {
		getTareas();
		getVentas();
		crearTabla();
	}    
	
	private void getTareas() {
		String hql = "FROM Tarea";
		query = session.createQuery(hql);
		tareas= query.list();
	}
	private void getVentas() {
		String hql = "FROM Ventas_Vende_Vehiculo";
		query = session.createQuery(hql);
		ventas= query.list();
	}
	
	private SimpleStringProperty getEstado(int codV) {
		for (Tarea t:tareas) {
			if(t.getVehiculo().getCodVeh()==codV) {
				return new SimpleStringProperty("En mantenimiento");
			}
		}
		
		for (Ventas_Vende_Vehiculo v:ventas) {
			if(v.getVehiculo().getCodVeh()==codV) {
				if(v.getVenta()) {
					return new SimpleStringProperty("Vendido");
				}else {
					return new SimpleStringProperty("Propuesto");
				}
			}
		}
		return new SimpleStringProperty("A vender");
	}
	
	private void crearTabla() {

		// Se hace una consulta y se guarda la información en una lista
		String hql = "FROM Vehiculo";
		query = session.createQuery(hql);
		List<Vehiculo> res = query.list();

		// Se pasa la información de una lista a otra
		listaVehiculos= FXCollections.observableArrayList(res);

		// Se asignan los valores de la lista a las celdas correspondientes
		fecColumn.setCellValueFactory(cellData -> cellData.getValue().fechProperty());
		marcaColumn.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
		modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
		estadoColumn.setCellValueFactory(cellData -> getEstado(cellData.getValue().getCodVeh()));
		vehTable.setItems(listaVehiculos);
	}
}