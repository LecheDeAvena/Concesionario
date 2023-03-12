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
import javafx.scene.control.TextField;

public class TareasMecanicoJefeController {

	@FXML
	private TableView<Mecanico_Tiene_Tarea> tareasTable;
	@FXML
	private Button nuevaTareaBtn, refrescarBtn;
	@FXML
	private TextField buscadorTF;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> vehiculoCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> clienteCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> fEnCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> tareaCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> costeCol;
	@FXML
	private TableColumn<Mecanico_Tiene_Tarea, String> MecanicoCol;

	Empleado user;
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;

	@FXML
	public void initialize() {
		setUser();
		crearTabla();
	}

	/**
	 * Este método asigna la información de usuario obteniéndola de la toolbar
	 */
	private void setUser() {
		ToolBarController tbc = new ToolBarController();
		user = tbc.getUserInfo();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método crea la tabla
	 */
	private void crearTabla() {

		// Se hace una consulta y se guarda la información en una lista
		String hql = "FROM Mecanico_Tiene_Tarea";
		query = session.createQuery(hql);
		List<Mecanico_Tiene_Tarea> res = query.list();

		// Se pasa la información de una lista a otra
		ObservableList<Mecanico_Tiene_Tarea> array = FXCollections.observableArrayList(res);

		// Se asignan los valores de la lista a las celdas correspondientes
		tareaCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().nomTarProperty());
		clienteCol.setCellValueFactory(
				cellData -> cellData.getValue().getTarea().getVehiculo().getCliente().nomAndApeCliProperty());
		vehiculoCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().getVehiculo().cocheNameProperty());
		fEnCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().fechIniProperty());
		costeCol.setCellValueFactory(cellData -> cellData.getValue().getTarea().cosTarProperty());
		MecanicoCol.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().nomAndApeEmpProperty());
		tareasTable.setItems(array);
	}
}
