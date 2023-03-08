package ch.makery.address.view;

import java.sql.Statement;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Cliente;
import ch.makery.address.models.Mecanico_Tiene_Tarea;
import ch.makery.address.models.Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClientesVentasController {

	@FXML
	private TableView<Cliente> customerTable;
	@FXML
	private TableView<Vehiculo> suggestionTable;
	@FXML
	private TableColumn<Cliente, String> clientNameColumn;
	@FXML
	private TableColumn<Cliente, String> clientTlfColumn;
	@FXML
	private TableColumn<Cliente, String> clientMailColumn;
	@FXML
	private TableColumn<Cliente, String> clientDirColumn;
	@FXML
	private TableColumn<Vehiculo, String> vehMarColumn;
	@FXML
	private TableColumn<Vehiculo, String> vehModelColumn;
	@FXML
	private TableColumn<Vehiculo, String> vehCostColumn;
	@FXML
	private TableColumn<Vehiculo, String> vehFechColumn;
	
	@FXML
	private Button editBtn;
	@FXML
	private Button resetBtn;
	@FXML
	private Button searchBtn;
	@FXML
	private TextField searchTextField;
	
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	
	@FXML
	public void initialize(){
		setCustomers();
		setVentas();
	}
	
	@SuppressWarnings("unused")
	private void setCustomers() {
		
		String hql = "FROM Cliente";
		query = session.createQuery(hql);
		List<Cliente> res = query.list();
		
		ObservableList<Cliente> array = FXCollections.observableArrayList(res);
		
		clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().nomAndApeCliProperty());
		clientTlfColumn.setCellValueFactory(cellData-> cellData.getValue().phoneProperty());
		clientMailColumn.setCellValueFactory(cellData-> cellData.getValue().mailProperty());
		clientDirColumn.setCellValueFactory(cellData-> cellData.getValue().dirProperty());
		
		customerTable.setItems(array);
	}
	
	private void setVentas() {
		
		String hql = "FROM Vehiculo ";
		query = session.createQuery(hql);
		List<Vehiculo> res = query.list();
		
		ObservableList<Vehiculo> array = FXCollections.observableArrayList(res);
		
		vehMarColumn.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
		vehModelColumn.setCellValueFactory(cellData-> cellData.getValue().modelProperty());
		vehCostColumn.setCellValueFactory(cellData-> cellData.getValue().precioProperty());
		vehFechColumn.setCellValueFactory(cellData-> cellData.getValue().fechProperty());

		suggestionTable.setItems(array);
	}
}