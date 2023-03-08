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
	public void comprobarDatos(ActionEvent event) {
		if (comprobarCampos()) {
			if (comprobarSesion()) {
				emple = new Empleado();
				emple.setNomEmp(usernameField.getText());
				emple.setCodEmp(codUser);
				entrarApp(event);
			} else {
				mensajeError("El usuario y la contraseña no coinciden");
			}
		}
	}

	private void entrarApp(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/toolbar.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			stage.setUserData(emple);
			ToolBarController controller = loader.getController();
			controller.recoverUserInfo(stage);

			stage.setTitle("Aplicacion");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			mensajeError("Error al entrar en la aplicacion");
			e.printStackTrace();
		}
	}

	private boolean comprobarCampos() {
		String errorText = "";

		if (usernameField.getText().equals(null) || usernameField.getText().length() <= 0) {
			errorText += "usuario";
		}
		if (passwordField.getText().equals(null) || passwordField.getText().length() <= 0) {
			if (errorText.length() != 0) {
				errorText += " y ";
			}
			errorText += "contraseña";
		}

		if (errorText.length() == 0) {
			return true;
		} else {
			mensajeError("Falta por rellenar el campo " + errorText);
			return false;
		}
	}

	private void mensajeError(String text) {
		errorField.setText(text);
	}

	private void alerta(String titulo, String header, String mensajeError) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(header + "\n");
		alert.setContentText(mensajeError);

		alert.showAndWait();
	}

	@SuppressWarnings("unchecked")
	private boolean comprobarSesion() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Query query = null;
		try {

			session.getTransaction().begin();
			String str = "SELECT e.codEmp FROM Empleado e WHERE e.nomEmp='" + usernameField.getText()
					+ "' AND e.conEmp='" + passwordField.getText() + "'";

			query = session.createQuery(str);

			List<Integer> emp = query.list();
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