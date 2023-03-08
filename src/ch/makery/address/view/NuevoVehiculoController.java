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

	private String codCliente, selectedCliente, placeholderCod;

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
	 * Este método genera un vehículo
	 */
	private void crearVehiculo() {
		errorLbl.setText("");
		if (comprobarDatos()) {

			String hql = "FROM Cliente WHERE codCli= '" + codCliente + "'";
			query = session.createQuery(hql);
			Cliente cliente = (Cliente) query.list().get(0);

			long milisegundos = System.currentTimeMillis();
			java.util.Date date = new java.util.Date(milisegundos);

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

			sesion.save(vehiculo);

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

		if (modeloField.getText().isEmpty()) {
			return false;
		}
		if (precioField.getText().isEmpty() || !esNumero(precioField.getText())) {
			if (!esNumero(precioField.getText())) {
				errorLbl.setText("El precio debe tener un valor numerico");
			}
			return false;
		}
		if (nPuertasField.getText().isEmpty() || !esNumero(nPuertasField.getText())) {
			if (!esNumero(nPuertasField.getText())) {
				errorLbl.setText("El numero de puertas debe tener un valor numerico entero");
			}
			return false;
		}
		if (marcaField.getText().isEmpty()) {
			return false;
		}
		if (matriculaField.getText().isEmpty()) {
			return false;
		}
		if (colorField.getText().isEmpty()) {
			return false;
		}
		if (combustibleField.getText().isEmpty()) {
			return false;
		}
		if (selectedCliente.isEmpty()) {
			errorLbl.setText("No se ha seleccionado cliente");
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private void setSplitMenus() {

		String hql = "SELECT codCli,nomCli,apeCli FROM Cliente";
		query = session.createQuery(hql);
		List<Object[]> datosCliente = query.list();
		MenuItem nuevoItem;
		String placeholderName;

		nuevoItem = new MenuItem("-Selecciona cliente-");
		nuevoItem.setOnAction(a -> {
			selectedCliente = "";
			codCliente = "";
			clienteField.setText("-Selecciona cliente-");
		});

		clienteField.getItems().addAll(nuevoItem);

		for (Object[] item : datosCliente) {
			placeholderName = item[1] + " " + item[2];
			placeholderCod = item[0].toString();
			nuevoItem = new MenuItem(placeholderName);
			nuevoItem.setOnAction(a -> {
				selectedCliente = ((MenuItem) a.getSource()).getText();
				clienteField.setText(selectedCliente);
				codCliente = placeholderCod;
				System.out.println(codCliente);
			});
			placeholderCod = "";

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
			//double numeroDouble = Double.parseDouble(numero);
			Double.parseDouble(numero);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
