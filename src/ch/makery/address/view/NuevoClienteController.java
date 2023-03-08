package ch.makery.address.view;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ch.makery.address.models.Cliente;
import ch.makery.address.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NuevoClienteController {

	@FXML
	private TextField nameField, surnameField, tlfField, mailField, dirField;
	@FXML
	private Button sendBtn, resetBtn;
	@FXML
	private Label errorLbl;

	@FXML
	private void initialize() {
	}

	@FXML
	/**
	 * Este método crea un cliente si están llenos todos los campos
	 */
	private void crearCliente() {
		errorLbl.setText("");
		if (camposLlenos()) {

			Cliente cliente = new Cliente();
			cliente.setNomCli(nameField.getText());
			cliente.setApeCli(surnameField.getText());
			cliente.setDirCli(dirField.getText());
			cliente.setGmaCli(mailField.getText());
			cliente.setTlfCli(tlfField.getText());

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session sesion = sessionFactory.openSession();

			Transaction transaction = sesion.beginTransaction();

			sesion.save(cliente);

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
	 * Este método vacía los campos
	 */
	private void resetFields() {
		nameField.setText("");
		surnameField.setText("");
		tlfField.setText("");
		mailField.setText("");
		dirField.setText("");
		errorLbl.setText("");
	}

	/**
	 * Este método comprueba si los campos están llenos
	 * @return
	 */
	private boolean camposLlenos() {

		if (nameField.getText().isEmpty()) {
			return false;
		}
		if (surnameField.getText().isEmpty()) {
			return false;
		}
		if (tlfField.getText().isEmpty()) {
			return false;
		}
		if (mailField.getText().isEmpty()) {
			return false;
		}
		if (dirField.getText().isEmpty()) {
			return false;
		}
		return true;
	}
}
