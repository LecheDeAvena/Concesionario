package ch.makery.address.view;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Tarea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AsignarTareaController {

	@FXML
	private TableView<Tarea> tareasTable;
	@FXML
	private TableColumn<Tarea, String> tareaCol;
	@FXML
	private TableColumn<Tarea, String> clienteCol;
	@FXML
	private TableColumn<Tarea, String> vehiculoCol;
	@FXML
	private TableColumn<Tarea, String> fEnCol;
	@FXML
	private TableColumn<Tarea, String> costeCol;
	@FXML
	private Button asignarTareaBtn;
	@FXML
	private Label errorLbl;

	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;
	ObservableList<Tarea> lista;

	@FXML
	private void initialize() {
		crearTablaTarea();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método genera una tabla con información de la tarea
	 */
	private void crearTablaTarea() {

		// Definir una sentencia y guardar el resultado en una lista
		String hql = "FROM Tarea t WHERE t.codTar NOT IN (SELECT mtt.tarea.codTar FROM Mecanico_Tiene_Tarea mtt)";
		query = session.createQuery(hql);
		List<Tarea> listaResultado = query.list();

		// Pasar la información de una lista a otra
		lista = FXCollections.observableArrayList(listaResultado);

		// Asignar los valores a las celdas
		
		tareaCol.setCellValueFactory(cellData -> cellData.getValue().nomTarProperty());
		clienteCol.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().getCliente().nomAndApeCliProperty());
		vehiculoCol.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().cocheNameProperty());
		fEnCol.setCellValueFactory(cellData -> cellData.getValue().fechIniProperty());
		costeCol.setCellValueFactory(cellData -> cellData.getValue().cosTarProperty());
		
		tareasTable.setItems(lista);
	}
	
	@FXML
	private void asignarTarea() {
		
		Tarea selectedItem=tareasTable.getSelectionModel().getSelectedItem();
		errorLbl.setText("");
		
		if(selectedItem!=null) {
			
			MecanicosPopup mp=new MecanicosPopup();
			mp.displayPopup(tareasTable.getSelectionModel().getSelectedItem());
		}else {
			errorLbl.setText("No se ha seleccionado ninguna tarea");
		}
	}
	
	public void update() {
		
		String hql = "FROM Tarea t WHERE t.codTar NOT IN (SELECT mtt.tarea.codTar FROM Mecanico_Tiene_Tarea mtt)";
		query = session.createQuery(hql);
		List<Tarea> listaTemp = query.list();

		Iterator<Tarea> iTemp = listaTemp.iterator();
		
		lista = FXCollections.observableArrayList();
		for (int i = 0; i < listaTemp.size(); i++) {
			lista.add(listaTemp.get(i));
		}
		
		tareasTable.refresh();
	}
}
