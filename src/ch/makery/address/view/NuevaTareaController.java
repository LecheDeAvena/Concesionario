package ch.makery.address.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Tarea;
import ch.makery.address.models.Vehiculo;
import ch.makery.address.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NuevaTareaController {

	@FXML
	private TextField nameField, costField;
	@FXML
	private TextArea desField, materialField;
	@FXML
	private SplitMenuButton vehiculoField;
	@FXML
	private DatePicker tiempoField;
	@FXML
	private Button sendBtn, resetBtn;
	@FXML
	private Label errorLbl;

	private String selectedVehiculo;
	private String matricula = "";

	private ArrayList<MenuItem> vehiculoItems = new ArrayList<>();

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session = sessionFactory.openSession();
	Query query = null;

	@FXML
	private void initialize() {
		setSplitMenu();
	}

	@FXML
	/**
	 * Este método crea una tarea
	 */
	private void crearTarea() {
		errorLbl.setText("");
		// Si los datos están bien:
		if (comprobarDatos()) {
			// Definimos la consulta y guardamos la información en un vehículo
			String hql = "FROM Vehiculo WHERE matVeh= '" + matricula + "'";
			query = session.createQuery(hql);
			Vehiculo vehiculo = (Vehiculo) query.list().get(0);

			// Obtenemos el tiempo de la consulta
			long milisegundos = System.currentTimeMillis();
			// Le damos formato a los milisegundos para guardarlos como una fecha
			Date date = new Date();

			// Definimos una tarea y le asignamos la información correspondiente a cada campo
			Tarea tarea = new Tarea();
			tarea.setCosTar(Integer.parseInt(costField.getText()));
			tarea.setDesTar(desField.getText());
			tarea.setFecIni(date);
			tarea.setMatTar(materialField.getText());
			tarea.setNomTar(nameField.getText());
			tarea.setVehiculo(vehiculo);

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session sesion = sessionFactory.openSession();

			Transaction transaction = sesion.beginTransaction();

			// Guardamos la tarea
			sesion.save(tarea);

			// La subimos a la base de datos
			transaction.commit();

			sesion.close();
			resetFields();
		} else {
			if (errorLbl.getText().isEmpty())
				errorLbl.setText("Algunos campos están vacios");
		}
	}

	@FXML
	/**
	 * Este método vacía los campos de la tabla
	 */
	private void resetFields() {

		nameField.setText("");
		costField.setText("");
		desField.setText("");
		materialField.setText("");
		selectedVehiculo = "";
		vehiculoField.setText("-Selecciona vehiculo-");
		matricula = "";
		errorLbl.setText("");
	}

	/**
	 * Este método comprueba si los datos están bien y los campos llenos
	 * 
	 * @return
	 */
	private boolean comprobarDatos() {

		// Si el campo está vacío:
		if (nameField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío o el tipo de dato es incorrecto:
		if (costField.getText().isEmpty() || !esDouble(costField.getText())) {
			// Si el campo está lleno pero el tipo de dato es incorrecto:
			if (!esDouble(costField.getText())) {
				errorLbl.setText("El coste debe tener un valor numerico");
			}
			return false;
		}
		// Si el campo está vacío:
		if (desField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (matricula.isEmpty()) {
			errorLbl.setText("No se ha seleccionado vehiculo");
			return false;
		}
		// Si el campo está vacío:
		if (materialField.getText().isEmpty()) {
			materialField.setText("");
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método asigna al menú los valores
	 */
	private void setSplitMenu() {

		// Definir una sentencia y guardar el resultado en una lista
		String hql = "SELECT matVeh,marca,modVeh FROM Vehiculo";
		query = session.createQuery(hql);
		List<Object[]> datosVehiculo = query.list();
		
		// Se definen nuevos elementos
		MenuItem nuevoItem;
		String placeHolder;

		nuevoItem = new MenuItem("-Selecciona vehiculo-");
		nuevoItem.setOnAction(a -> {
			selectedVehiculo = "";
			matricula = "";
			vehiculoField.setText("-Selecciona vehiculo-");
		});

		// Se asigna la información a los elementos
		vehiculoField.getItems().addAll(nuevoItem);

		for (Object[] item : datosVehiculo) {
			placeHolder = item[0] + " - " + item[1] + item[2];
			nuevoItem = new MenuItem(placeHolder);
			nuevoItem.setOnAction(a -> {
				selectedVehiculo = ((MenuItem) a.getSource()).getText();
				vehiculoField.setText(selectedVehiculo);
				matricula = selectedVehiculo.substring(0, selectedVehiculo.lastIndexOf("-"));
			});

			vehiculoField.getItems().addAll(nuevoItem);
			vehiculoItems.add(nuevoItem);
		}

	}

	/**
	 * Este método comprueba si el número pasado como String es un double
	 * 
	 * @param numero
	 * @return
	 */
	private boolean esDouble(String numero) {
		if (numero == null) {
			return false;
		}
		// Intenta convertir el String a un double
		try {
			// double numeroDouble = Double.parseDouble(numero);
			Double.parseDouble(numero);
		} catch (NumberFormatException e) {
			// Si no funciona es false
			return false;
		}
		// Si no hay problema es true
		return true;
	}
}
