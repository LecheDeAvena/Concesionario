package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Empleado;
import ch.makery.address.models.Mecanico_Tiene_Tarea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TareasMecanicoController {

	@FXML
	private TableView<Mecanico_Tiene_Tarea> tareasTable;
	@FXML
	private Button terminarTareaBtn;
	@FXML 
	TableColumn<Mecanico_Tiene_Tarea,String> tareaCol;
	@FXML 
	TableColumn<Mecanico_Tiene_Tarea,String> clienteCol;
	@FXML 
	TableColumn<Mecanico_Tiene_Tarea,String> vehiculoCol;
	@FXML 
	TableColumn<Mecanico_Tiene_Tarea,String> fEnCol;
	@FXML 
	TableColumn<Mecanico_Tiene_Tarea,String> costeCol;
	@FXML 
	TableColumn<Mecanico_Tiene_Tarea,String> infoCol;
	
	Empleado user;
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	
	@FXML
	public void initialize() {
	}
	
	private void setUser() {
		ToolBarController tbc = new ToolBarController();
		user = tbc.getUserInfo();
	}
	
	private void crearTabla() {

		String hql = "FROM Mecanico_Tiene_Tarea mtt WHERE mtt.empleado.codEmp="+user.getCodEmp();
		query = session.createQuery(hql);
		List<Mecanico_Tiene_Tarea> res = query.list();

		ObservableList<Mecanico_Tiene_Tarea> array = FXCollections.observableArrayList(res);

		tareaCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().nomTarProperty());
		clienteCol.setCellValueFactory(
				cellData -> cellData.getValue().getTarea().getVehiculo().getCliente().nomAndApeCliProperty());
		vehiculoCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().getVehiculo().cocheNameProperty());
		fEnCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().fechIniProperty());
		costeCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().cosTarProperty());
		infoCol.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().nomAndApeEmpProperty());

		tareasTable.setItems(array);
	}
}