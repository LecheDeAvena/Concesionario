package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Cliente;
import ch.makery.address.models.Vehiculo;
import ch.makery.address.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

public class NuevoVehiculoController {

	@FXML
	private TextField modeloField;
	@FXML
	private TextField matriculaField;
	@FXML
	private TextField marcaField;
	@FXML
	private TextField precioField;
	@FXML
	private TextField nPuertasField;
	@FXML
	private TextField colorField;
	@FXML
	private SplitMenuButton clienteField, combustibleField;
	@FXML
	private Button sendBtn, resetBtn;
	@FXML
	private Label errorLbl;

	private String codCliente, selectedCliente;

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session = sessionFactory.openSession();
	Query query = null;

	@FXML
	private void initialize() {
		setSplitMenus();
	}

	@FXML
	/**
	 * Este método vacía los campos
	 */
	private void resetFields() {
		modeloField.setText("");
		matriculaField.setText("");
		marcaField.setText("");
		precioField.setText("");
		nPuertasField.setText("");
		colorField.setText("");
		selectedCliente = "";
		clienteField.setText("-Selecciona cliente-");
		combustibleField.setText("-Selecciona tipo-");
		codCliente = "";
		errorLbl.setText("");
	}

	@FXML
	/**
	 * Este método genera un vehículo en la base de datos
	 */
	private void crearVehiculo() {
		errorLbl.setText("");
		// Si los datos están bien:
		if (comprobarDatos()) {

			// Hacmos una consulta y guardamos los datos en un cliente
			String hql = "FROM Cliente WHERE codCli= " + codCliente;
			query = session.createQuery(hql);
			Cliente cliente = (Cliente) query.list().get(0);

			// Tomamos el momento en el que hemos buscado la información
			long milisegundos = System.currentTimeMillis();
			// A ese tiempo le damos formato de fecha
			java.util.Date date = new java.util.Date(milisegundos);

			// Definimos un nuevo vehiculo y le asignamos los valores correspondientes
			Vehiculo vehiculo = new Vehiculo();

			vehiculo.setCliente(cliente);
			vehiculo.setColor(colorField.getText());
			vehiculo.setFecLle(date);
			vehiculo.setMarca(marcaField.getText());
			vehiculo.setMatVeh(matriculaField.getText());
			vehiculo.setModVeh(modeloField.getText());
			vehiculo.setPreVeh(Integer.parseInt(precioField.getText()));
			vehiculo.setTipGas(combustibleField.getText());

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session sesion = sessionFactory.openSession();

			Transaction transaction = sesion.beginTransaction();

			// Guardamos el vehículo
			sesion.save(vehiculo);

			// Lo subimos a la base de datos
			transaction.commit();

			sesion.close();
			resetFields();
		} else {
			if (errorLbl.getText().isEmpty())
				errorLbl.setText("Algunos campos están vacios");
		}
	}

	/**
	 * Este método comprueba si los campos están llenos y con datos del tipo
	 * correspondiente
	 * 
	 * @return
	 */
	private boolean comprobarDatos() {

		// Si el campo está vacío:
		if (modeloField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío o el tipo de dato es incorrecto:
		if (precioField.getText().isEmpty() || !esNumero(precioField.getText())) {
			// Si el tipo de dato es incorrecto:
			if (!esNumero(precioField.getText())) {
				errorLbl.setText("El precio debe tener un valor numerico");
			}
			return false;
		}
		// Si el campo está vacío o el tipo de dato es incorrecto:
		if (nPuertasField.getText().isEmpty() || !esNumero(nPuertasField.getText())) {
			// Si el tipo de dato es incorrecto:
			if (!esNumero(nPuertasField.getText())) {
				errorLbl.setText("El numero de puertas debe tener un valor numerico entero");
			}
			return false;
		}
		// Si el campo está vacío:
		if (marcaField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (matriculaField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (colorField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (combustibleField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (selectedCliente.isEmpty()) {
			errorLbl.setText("No se ha seleccionado cliente");
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método sirve para la implementación del botón que abre el abanico de clientes
	 */
	private void setSplitMenus() {

		// Hacemos una consulta y guardamos los datos en una lista
		String hql = "SELECT codCli,nomCli,apeCli FROM Cliente";
		query = session.createQuery(hql);
		List<Object[]> datosCliente = query.list();
		
		// Definimos nuevos elementos
		MenuItem nuevoItem;
		String placeholderName;

		nuevoItem = new MenuItem("-Selecciona cliente-");
		nuevoItem.setOnAction(a -> {
			selectedCliente = "";
			codCliente = "";
			clienteField.setText("-Selecciona cliente-");
		});

		clienteField.getItems().addAll(nuevoItem);

		// Se le asigna la información a los elementos
		for (Object[] item : datosCliente) {
			placeholderName = item[1] + " " + item[2];
			nuevoItem = new MenuItem(placeholderName);
			nuevoItem.setOnAction(a -> {
				selectedCliente = ((MenuItem) a.getSource()).getText();
				clienteField.setText(selectedCliente);
				codCliente = String.valueOf(item[0]);
				System.out.println(codCliente);
			});

			clienteField.getItems().addAll(nuevoItem);
		}

		TipoGas[] listaDeCombustibles = TipoGas.values();

		for (int i = 0; i < listaDeCombustibles.length; i++) {
			nuevoItem = new MenuItem(listaDeCombustibles[i].name());
			nuevoItem.setOnAction(a -> {
				String tituloItem = ((MenuItem) a.getSource()).getText();
				combustibleField.setText(tituloItem);
			});

			combustibleField.getItems().addAll(nuevoItem);
		}

	}

	private enum TipoGas {
		GASOIL, DIESEL, OTRO, MOMSTER
	}

	/**
	 * Este método comprueba si el String es un números double
	 * 
	 * @param numero
	 * @return
	 */
	private boolean esNumero(String numero) {
		if (numero == null) {
			return false;
		}
		try {
			// double numeroDouble = Double.parseDouble(numero);
			Double.parseDouble(numero);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
