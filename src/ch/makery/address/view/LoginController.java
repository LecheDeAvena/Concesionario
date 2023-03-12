package ch.makery.address.view;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.MainApp;
import ch.makery.address.models.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Label errorField;
	@FXML
	private Button btnLogin;

	private Empleado emple;

	private int codUser = -1;

	public LoginController() {

	}

	@FXML
	private void initialize() {
	}

	@FXML
	/**
	 * Este método comprueba los datos de los campos
	 * @param event
	 */
	public void comprobarDatos(ActionEvent event) {
		// Si los campos están bien:
		if (comprobarCampos()) {
			// Y las credenciales también:
			if (comprobarSesion()) {
				// Asignamos valores e iniciamos sesión
				emple = new Empleado();
				emple.setNomEmp(usernameField.getText());
				emple.setCodEmp(codUser);
				entrarApp(event);
			} else {
				mensajeError("El usuario y la contraseña no coinciden");
			}
		}
	}

	/**
	 * Este método sirve para entrar en la app
	 * @param event
	 */
	private void entrarApp(ActionEvent event) {
		// Tomamos información de las pantallas
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			// Definimos una ventana
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/toolbar.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			// Usamos la información obtenida anteriormente
			stage.setUserData(emple);
			ToolBarController controller = loader.getController();
			controller.recoverUserInfo(stage);

			// Carga la aplicación
			stage.setTitle("Aplicacion");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			mensajeError("Error al entrar en la aplicacion");
			e.printStackTrace();
		}
	}

	/**
	 * Este método comprueba si los campos están llenos
	 * @return
	 */
	private boolean comprobarCampos() {
		String errorText = "";

		// Si el campo del nombre de usuario está vacío:
		if (usernameField.getText().equals(null) || usernameField.getText().length() <= 0) {
			// Añadimos información al texto de error
			errorText += "usuario";
		}
		// Si el campo de la contraseña está vacío:
		if (passwordField.getText().equals(null) || passwordField.getText().length() <= 0) {
			// Si ya había un error antes:
			if (errorText.length() != 0) {
				// Añadimos información al texto de error
				errorText += " y ";
			}
			// Añadimos información al texto de error
			errorText += "contraseña";
		}

		// Si no hay errores se devuelve true
		if (errorText.length() == 0) {
			return true;
		}
		// Si no, se informa
		else {
			mensajeError("Falta por rellenar el campo " + errorText);
			return false;
		}
	}

	/**
	 * Este método recibe un texto para imprimirlo por pantalla como error
	 * @param text
	 */
	private void mensajeError(String text) {
		errorField.setText(text);
	}

	/**
	 * Este método recibe un título, un encabezado y un mensaje de error para mostar un mensaje de alerta
	 * @param titulo
	 * @param encabezado
	 * @param mensajeError
	 */
	private void alerta(String titulo, String encabezado, String mensajeError) {
		// Definimos la alerta y le asignamos los parámetros a su información
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(encabezado + "\n");
		alert.setContentText(mensajeError);

		alert.showAndWait();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Este método se usa para comprobar si los credenciales mandados son correctos
	 * @return
	 */
	private boolean comprobarSesion() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Query query = null;
		try {
			// Hacemos la consulta
			session.getTransaction().begin();
			String sentencia = "SELECT e.codEmp FROM Empleado e WHERE e.nomEmp='" + usernameField.getText()
					+ "' AND e.conEmp='" + passwordField.getText() + "'";

			query = session.createQuery(sentencia);

			// Guardamos el resultado en una lista
			List<Integer> emp = query.list();
			// Tomamos información de la lista
			codUser = emp.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			alerta("Error en conexion", "Error al conectarse en la base de datos", "");
			return false;
		} finally {
			session.close();
			sf.close();
		}
		if (codUser <= 0) {
			return false;
		} else {
			return true;
		}
	}
}