package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Cliente;
import ch.makery.address.models.Vehiculo;
import ch.makery.address.models.Ventas_Vende_Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClientesVentasController {

	@FXML
	private TableView<Cliente> customerTable;
	@FXML
	private TableView<Ventas_Vende_Vehiculo> suggestionTable;
	@FXML
	private TableColumn<Cliente, String> clientNameColumn;
	@FXML
	private TableColumn<Cliente, String> clientTlfColumn;
	@FXML
	private TableColumn<Cliente, String> clientMailColumn;
	@FXML
	private TableColumn<Cliente, String> clientDirColumn;
	@FXML
	private TableColumn<Ventas_Vende_Vehiculo, String> cliNombreColumn;
	@FXML
	private TableColumn<Ventas_Vende_Vehiculo, String> vehModelColumn;
	@FXML
	private TableColumn<Ventas_Vende_Vehiculo, String> vehCostColumn;
	@FXML
	private TableColumn<Ventas_Vende_Vehiculo, String> vehFechColumn;
	@FXML
	private TableColumn<Ventas_Vende_Vehiculo, String> vehVenColumn;

	@FXML
	private Button editBtn;
	@FXML
	private Button resetBtn;
	@FXML
	private Button searchBtn;
	@FXML
	private Button searchBtn2;
	@FXML
	private TextField searchTextField;
	@FXML
	private TextField searchTextField2;

	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;

	@FXML
	public void initialize() {
		setCustomers();
		elegirCliente();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método asigna a la tabla los datos correspondientes de clientes
	 */
	private void setCustomers() {

		// Definir una sentencia y guardar el resultado en una lista
		String hql = "FROM Cliente";
		query = session.createQuery(hql);
		List<Cliente> res = query.list();

		// Pasar la información de una lista a otra
		ObservableList<Cliente> array = FXCollections.observableArrayList(res);

		// Asignar los valores a las celdas
		clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().nomAndApeCliProperty());
		clientTlfColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		clientMailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
		clientDirColumn.setCellValueFactory(cellData -> cellData.getValue().dirProperty());
		customerTable.setItems(array);
	}

	/**
	 * Este método refresca la tabla u la rellena aplicando un filtro por el que se
	 * ha buscado
	 */
	@FXML
	private void searchClientes() {

		String searchText = searchTextField.getText();
		// Definir una sentencia y guardar el resultado en una lista
		String hql = "FROM Cliente c WHERE c.nomCli = '" + searchText + "' or c.apeCli = '" + searchText
				+ "' or c.gmaCli = '" + searchText + "' or c.tlfCli = '" + searchText + "' or c.dirCli = '"
				+ searchText;
		query = session.createQuery(hql);
		List<Cliente> res = query.list();

		// Pasar la información de una lista a otra
		ObservableList<Cliente> array = FXCollections.observableArrayList(res);

		// Asignar los valores a las celdas
		clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().nomAndApeCliProperty());
		clientTlfColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		clientMailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
		clientDirColumn.setCellValueFactory(cellData -> cellData.getValue().dirProperty());
		customerTable.setItems(array);
	}

	/**
	 * Este método llama a setPropuestas cuando se clicka sobre un cliente
	 */
	private void elegirCliente() {
		customerTable.setRowFactory(tv -> {
			TableRow<Cliente> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					Cliente rowData = row.getItem();
					setPropuestas(rowData);
				}
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Cliente rowData = row.getItem();
					ClientePopup cp = new ClientePopup();
					cp.displayPopup(rowData);
				}
			});
			return row;
		});
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método asigna a la tabla los datos correspondientes de ventas
	 */
	private void setPropuestas(Cliente cliente) {

		// Definir una sentencia y guardar el resultado en una lista
		String hql = "FROM Ventas_Vende_Vehiculo vvv WHERE vvv.cliente.codCli = " + cliente.getCodCli();
		query = session.createQuery(hql);
		List<Ventas_Vende_Vehiculo> res = query.list();

		// Pasar la información de una lista a otra
		ObservableList<Ventas_Vende_Vehiculo> array = FXCollections.observableArrayList(res);

		// Asignar los valores a las celdas
		cliNombreColumn.setCellValueFactory(cellData -> cellData.getValue().getCliente().nomAndApeCliProperty());
		vehModelColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().modelProperty());
		vehCostColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().precioProperty());
		vehFechColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().fechProperty());
		vehVenColumn.setCellValueFactory(cellData -> cellData.getValue().venProperty());
		suggestionTable.setItems(array);
	}

	/**
	 * Este método refresca la tabla u la rellena aplicando un filtro por el que se
	 * ha buscado
	 */
	@FXML
	private void searchPropuestas() {

		String searchText = searchTextField2.getText();
		// Definir una sentencia y guardar el resultado en una lista
		String hql = "FROM Ventas_Vende_Vehiculo vvv WHERE vvv.cliente.nomCli = '" + searchText
				+ "' or vvv.vehiculo.modVeh = '" + searchText + "' or vvv.vehiculo.preVeh = " + searchText;
		query = session.createQuery(hql);
		List<Cliente> res = query.list();

		// Pasar la información de una lista a otra
		ObservableList<Cliente> array = FXCollections.observableArrayList(res);

		// Asignar los valores a las celdas
		clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().nomAndApeCliProperty());
		clientTlfColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		clientMailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
		clientDirColumn.setCellValueFactory(cellData -> cellData.getValue().dirProperty());
		customerTable.setItems(array);
	}
}