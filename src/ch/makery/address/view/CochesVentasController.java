package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Cliente;
import ch.makery.address.models.Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CochesVentasController {

	@FXML
	private TableView<Vehiculo> vehiculo1Table;
	@FXML
	private TableView<Vehiculo> vehiculo2Table;
	@FXML
	private TableColumn<Vehiculo, String> imagen1NameColumn;
	@FXML
	private TableColumn<Vehiculo, String> modelo1Column;
	@FXML
	private TableColumn<Vehiculo, String> marca1Column;
	@FXML
	private TableColumn<Vehiculo, String> imagen2NameColumn;
	@FXML
	private TableColumn<Vehiculo, String> modelo2Column;
	@FXML
	private TableColumn<Vehiculo, String> marca2Column;

	@FXML
	private Button refrescarBtn;

	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;

	@FXML
	public void initialize() {
		setVehiculos1();
		setVehiculos2();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método asigna a la tabla los datos correspondientes de vehiculos
	 */
	private void setVehiculos1() {

		// Definir una sentencia y guardar el resultado en una lista
		String hql = "FROM Vehiculo v WHERE v.concesionario.codCon = 1";
		query = session.createQuery(hql);
		List<Vehiculo> res = query.list();

		// Pasar la información de una lista a otra
		ObservableList<Vehiculo> array = FXCollections.observableArrayList(res);

		// Asignar los valores a las celdas
		modelo1Column.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
		marca1Column.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
		vehiculo1Table.setItems(array);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método asigna a la tabla los datos correspondientes de vehiculos
	 */
	private void setVehiculos2() {

		// Definir una sentencia y guardar el resultado en una lista
		String hql = "FROM Vehiculo v WHERE v.concesionario.codCon != 1";
		query = session.createQuery(hql);
		List<Vehiculo> res = query.list();

		// Pasar la información de una lista a otra
		ObservableList<Vehiculo> array = FXCollections.observableArrayList(res);

		// Asignar los valores a las celdas
		modelo2Column.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
		marca2Column.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
		vehiculo2Table.setItems(array);
	}
	
	/**
	 * Este método refresca la tabla
	 */
	@FXML
	private void refresh() {
		setVehiculos1();
		setVehiculos2();
	}
}