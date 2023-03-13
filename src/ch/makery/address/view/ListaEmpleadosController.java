package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Empleado;
import ch.makery.address.models.Mecanico;
import ch.makery.address.models.Mecanico_Tiene_Tarea;
import ch.makery.address.models.Ventas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class ListaEmpleadosController {

	@FXML
	private TableView<Empleado> ventasTable;
	@FXML
	private TableColumn<Empleado, String> nameColumn;
	@FXML
	private TableColumn<Empleado, String> dniColumn;
	@FXML
	private TableColumn<Empleado, String> gmailColumn;
	@FXML
	private TableColumn<Empleado, String> tlfColumn;
	@FXML
	private TableColumn<Empleado, String> dirColumn;
	@FXML
	private TableColumn<Empleado, String> salColumn;
	@FXML
	private TableColumn<Empleado, String> trabaColumn;
	
	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	ObservableList<Empleado> empelados;
	List<Mecanico> mecanicos;
	List<Ventas> ventas;
	
	@FXML
	private void initialize() {
		getMecanicos();
		getVentas();
		crearTabla();
	}    
	
	private void getMecanicos() {
		String hql = "FROM Mecanico";
		query = session.createQuery(hql);
		 mecanicos= query.list();
	}
	private void getVentas() {
		String hql = "FROM Ventas";
		query = session.createQuery(hql);
		ventas= query.list();
	}
	
	private SimpleStringProperty getPuesto(int codE) {
		for (Mecanico m:mecanicos) {
			if(m.getEmpleado().getCodEmp()==codE) {
				return new SimpleStringProperty("Mecanico");
			}
		}
		
		for (Ventas v:ventas) {
			if(v.getEmpleado().getCodEmp()==codE) {
				return new SimpleStringProperty("Ventas");
			}
		}
		return new SimpleStringProperty("Jefe");
	}
	
	private void crearTabla() {

		// Se hace una consulta y se guarda la información en una lista
		String hql = "FROM Empleado";
		query = session.createQuery(hql);
		List<Empleado> res = query.list();

		// Se pasa la información de una lista a otra
		empelados= FXCollections.observableArrayList(res);

		// Se asignan los valores de la lista a las celdas correspondientes
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nomAndApeEmpProperty());
		dniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
		gmailColumn.setCellValueFactory(cellData -> cellData.getValue().gmailProperty());
		tlfColumn.setCellValueFactory(cellData -> cellData.getValue().tlfProperty());
		dirColumn.setCellValueFactory(cellData -> cellData.getValue().dirProperty());
		salColumn.setCellValueFactory(cellData -> cellData.getValue().salProperty());
		trabaColumn.setCellValueFactory(cellData -> getPuesto(cellData.getValue().getCodEmp()));
		ventasTable.setItems(empelados);
	}
}
