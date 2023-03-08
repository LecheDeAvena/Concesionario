package ch.makery.address.view;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Mecanico;
import ch.makery.address.models.Mecanico_Tiene_Tarea;
import ch.makery.address.models.Tarea;
import ch.makery.address.util.HibernateUtil;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AsignarTareaController {

	@FXML
	private TableView<Tarea> tablaTareas;
	@FXML
	private TableView<Mecanico> tablaMecanicos;
	@FXML
	private TableColumn<Tarea, String> tareasCol;
	@FXML
	private TableColumn<Tarea, String> nombreTareaCol;
	@FXML
	private TableColumn<Tarea, String> fechaEntradaCol;
	@FXML
	private TableColumn<Tarea, String> vehiculoCol;
	@FXML
	private TableColumn<Mecanico, String> nombreMecanicoCol;
	@FXML
	private TableColumn<Mecanico, Number> nTareasCol;
	@FXML
	private Button sendBtn;
	@FXML
	private Label errorLbl;

	SessionFactory sf = new Configuration().configure().buildSessionFactory();
	Session session = sf.openSession();
	Query query = null;

	@FXML
	private void initialize() {
		crearTablaTarea();
		crearTablaMecanico();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método genera una tabla con información de la tarea
	 */
	private void crearTablaTarea() {

		String hql = "FROM Tarea t WHERE t.codTar NOT IN (SELECT mtt.tarea.codTar FROM Mecanico_Tiene_Tarea mtt)";
		query = session.createQuery(hql);
		List<Tarea> listaResultado = query.list();

		ObservableList<Tarea> lista;
		lista = FXCollections.observableArrayList(listaResultado);

		nombreTareaCol.setCellValueFactory(cellData -> cellData.getValue().nomTarProperty());
		vehiculoCol.setCellValueFactory(cellData -> cellData.getValue().getVehiculo().cocheNameProperty());
		fechaEntradaCol.setCellValueFactory(cellData -> cellData.getValue().fechIniProperty());

		tablaTareas.setItems(lista);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método genera una tabla para las tareas del mecánico
	 */
	private void crearTablaMecanico() {

		String hql = "FROM Mecanico";
		query = session.createQuery(hql);
		List<Mecanico> listaResultado = query.list();

		ObservableList<Mecanico> lista = FXCollections.observableArrayList(listaResultado);

		hql = "FROM Mecanico_Tiene_Tarea";
		query = session.createQuery(hql);
		List<Mecanico_Tiene_Tarea> listaResultadoTareas = query.list();

		ObservableList<Mecanico_Tiene_Tarea> arrayTareas = FXCollections.observableArrayList(listaResultadoTareas);

		nombreMecanicoCol.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().nomAndApeEmpProperty());
		nTareasCol
				.setCellValueFactory(
						cellData -> Bindings.createLongBinding(
								() -> arrayTareas.stream()
										.filter(tar -> tar.getEmpleado().getCodEmp() == cellData.getValue()
												.getEmpleado().getCodEmp())
										.collect(Collectors.counting()),
								arrayTareas));
		tablaMecanicos.setItems(lista);
	}

	/**
	 * Este método comprueba los datos de la tabla no están vacíos
	 * 
	 * @return
	 */
	private boolean datosLlenos() {
		try {
			if (tablaTareas.getSelectionModel().getSelectedItem().equals(null)) {
				return false;
			}
			if (tablaMecanicos.getSelectionModel().getSelectedItem().equals(null)) {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	@FXML
	/**
	 * Este método inserta datos a las tablas
	 */
	private void insertDatos() {
		errorLbl.setText("");
		if (datosLlenos()) {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session sesion = sf.openSession();

			Transaction transaction = sesion.beginTransaction();

			Tarea tarea = tablaTareas.getSelectionModel().getSelectedItem();
			Mecanico mecanico = tablaMecanicos.getSelectionModel().getSelectedItem();

			Mecanico_Tiene_Tarea mecanicoTieneTarea = new Mecanico_Tiene_Tarea();
			mecanicoTieneTarea.setEmpleado(mecanico.getEmpleado());
			mecanicoTieneTarea.setTarea(tarea);

			sesion.save(mecanicoTieneTarea);

			transaction.commit();

			sesion.close();
			tablaTareas.refresh();
			tablaMecanicos.refresh();
		} else {
			errorLbl.setText("Se necesita seleccionar tarea y mecanico");
		}
	}
}
