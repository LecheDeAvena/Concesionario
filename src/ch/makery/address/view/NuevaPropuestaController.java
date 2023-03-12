package ch.makery.address.view;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Cliente;
import ch.makery.address.models.Empleado;
import ch.makery.address.models.Vehiculo;
import ch.makery.address.models.Ventas_Vende_Vehiculo;
import ch.makery.address.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

public class NuevaPropuestaController {

	@FXML
	private SplitMenuButton clienteMenu, vehiculoMenu;
	@FXML
	private Button crearBoton;
	@FXML
	private Label errorCampo;

	private String codCliente, selectedCliente=null, placeholderCod, codVehiculo, selectedVehiculo;
	private Empleado empleado;

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session = sessionFactory.openSession();
	Query query = null;

	@FXML
	private void initialize() {
		setSplitMenu();
		setSplitMenu2();
		setUser();
	}

	/**
	 * Este método asigna la información de usuario obteniéndola de la toolbar
	 */
	private void setUser() {
		ToolBarController tbc = new ToolBarController();
		empleado = tbc.getUserInfo();
	}

	@FXML
	/**
	 * Este método genera un vehículo en la base de datos
	 */
	private void crearPropuesta() {
		errorCampo.setText("");
		// Si los datos están bien:
		if (comprobarDatos()) {

			// Tomamos el momento en el que hemos buscado la información
			Date date = new Date();

			// Hacmos una consulta y guardamos los datos de la propuesta
			String hql = "FROM Cliente WHERE codCli= " + codCliente;
			query = session.createQuery(hql);
			Cliente cliente = (Cliente) query.list().get(0);
			hql = "FROM Vehiculo WHERE codVeh= " + codVehiculo;
			query = session.createQuery(hql);
			Vehiculo vehiculo = (Vehiculo) query.list().get(0);

			// Definimos un nuevo vehiculo y le asignamos los valores correspondientes
			Ventas_Vende_Vehiculo ventas_Vende_Vehiculo = new Ventas_Vende_Vehiculo();

			ventas_Vende_Vehiculo.setCliente(cliente);
			ventas_Vende_Vehiculo.setEmpleado(empleado);
			ventas_Vende_Vehiculo.setFecVen(date);
			ventas_Vende_Vehiculo.setVehiculo(vehiculo);
			ventas_Vende_Vehiculo.setVenta(false);

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session sesion = sessionFactory.openSession();
			
			try {
				Transaction transaction = sesion.beginTransaction();

				// Guardamos el vehículo
				sesion.save(ventas_Vende_Vehiculo);

				// Lo subimos a la base de datos
				transaction.commit();
			} catch (Exception e) {
				errorCampo.setText("Campos ya insertados");
			}
			sesion.close();
		} else {
			if (errorCampo.getText().isEmpty())
				errorCampo.setText("Algunos campos no están seleccionados");
		}
	}

	/**
	 * Este método comprueba si los campos están llenos
	 * 
	 * @return
	 */
	private boolean comprobarDatos() {
		// Si el campo está vacío:
		if (selectedCliente==null) {
			errorCampo.setText("No se ha seleccionado cliente");
			return false;
		}
		// Si el campo está vacío:
		if (vehiculoMenu.getText().isEmpty()) {
			errorCampo.setText("No se ha seleccionado vehiculo");
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método sirve para la implementación del botón que abre el abanico de
	 * clientes
	 */
	private void setSplitMenu() {

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
			clienteMenu.setText("-Selecciona cliente-");
		});

		clienteMenu.getItems().addAll(nuevoItem);

		// Se le asigna la información a los elementos
		for (Object[] item : datosCliente) {
			placeholderName = item[1] + " " + item[2];
			nuevoItem = new MenuItem(placeholderName);
			nuevoItem.setOnAction(a -> {
				selectedCliente = ((MenuItem) a.getSource()).getText();
				clienteMenu.setText(selectedCliente);
				codCliente = String.valueOf(item[0]);
				System.out.println(codCliente);
			});

			clienteMenu.getItems().addAll(nuevoItem);
		}

	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método sirve para la implementación del botón que abre el abanico de
	 * clientes
	 */
	private void setSplitMenu2() {

		// Hacemos una consulta y guardamos los datos en una lista
		String hql = "SELECT codVeh, marca, modVeh FROM Vehiculo";
		query = session.createQuery(hql);
		List<Object[]> datosVehiculo = query.list();

		// Definimos nuevos elementos
		MenuItem nuevoItem;
		String placeholderName;

		nuevoItem = new MenuItem("-Selecciona vehiculo-");
		nuevoItem.setOnAction(a -> {
			selectedVehiculo = "";
			codVehiculo = "";
			vehiculoMenu.setText("-Selecciona vehiculo-");
		});

		vehiculoMenu.getItems().addAll(nuevoItem);

		// Se le asigna la información a los elementos
		for (Object[] item : datosVehiculo) {
			placeholderName = item[1] + " " + item[2];
			nuevoItem = new MenuItem(placeholderName);
			nuevoItem.setOnAction(a -> {
				selectedVehiculo = ((MenuItem) a.getSource()).getText();
				vehiculoMenu.setText(selectedVehiculo);
				codVehiculo =String.valueOf(item[0]);
			});
			placeholderCod = "";

			vehiculoMenu.getItems().addAll(nuevoItem);
		}

	}
}
