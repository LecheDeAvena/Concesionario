package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Mecanico_Tiene_Tarea;
import ch.makery.address.models.Ventas_Vende_Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ResumenVentasController {

	@FXML
	TableView<Ventas_Vende_Vehiculo> ventasTable;
	@FXML
	TableColumn<Ventas_Vende_Vehiculo, String> modelColumn;
	@FXML
	TableColumn<Ventas_Vende_Vehiculo, String> marcaColumn;
	@FXML
	TableColumn<Ventas_Vende_Vehiculo, String> fecColumn;
	@FXML
	TableColumn<Ventas_Vende_Vehiculo, String> beneColumn;
	@FXML
	Label benefVenLbl;
	@FXML
	Label benefMecLbl;
	@FXML
	Label benefLbl;

	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;

	@FXML
	private void initialize() {
		
		crearTabla();
		setBeneficios();
	}

	private void crearTabla() {

		// Se hace una consulta y se guarda la información en una lista
		String hql = "FROM Ventas_Vende_Vehiculo";
		query = session.createQuery(hql);
		List<Ventas_Vende_Vehiculo> res = query.list();

		// Se pasa la información de una lista a otra
		ObservableList<Ventas_Vende_Vehiculo> array = FXCollections.observableArrayList(res);

		// Se asignan los valores de la lista a las celdas correspondientes
		fecColumn.setCellValueFactory(cellData -> cellData.getValue().fecVenProperty());
		marcaColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().marcaProperty());
		modelColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().modelProperty());
		beneColumn.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().precioProperty());

		ventasTable.setItems(array);
	}
	
	private void setBeneficios() {
		
		String hql = "SELECT SUM(vehiculo.preVeh) FROM Ventas_Vende_Vehiculo WHERE venta=true";
		query = session.createQuery(hql);
		int preVenta=Integer.parseInt(String.valueOf(query.uniqueResult()));
		benefVenLbl.setText(String.valueOf(query.uniqueResult())+" €");
		
		hql = "SELECT SUM(preVeh) FROM Vehiculo WHERE codVeh IN (SELECT vehiculo.codVeh FROM Tarea)";
		query = session.createQuery(hql);
		benefMecLbl.setText(String.valueOf(query.uniqueResult())+" €");
		int preMec=Integer.parseInt(String.valueOf(query.uniqueResult()));
		
		benefLbl.setText(String.valueOf(preVenta+preMec)+" €");
	}	
	
}
