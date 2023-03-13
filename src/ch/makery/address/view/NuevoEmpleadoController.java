package ch.makery.address.view;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mysql.cj.Query;

import ch.makery.address.models.Cliente;
import ch.makery.address.models.Concesionario;
import ch.makery.address.models.Empleado;
import ch.makery.address.models.Jefe;
import ch.makery.address.models.Mecanico;
import ch.makery.address.models.Ventas;
import ch.makery.address.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

public class NuevoEmpleadoController {

	@FXML
	private TextField nameField; 
	@FXML
	private TextField surnameField; 
	@FXML
	private TextField tlfField; 
	@FXML
	private TextField mailField; 
	@FXML
	private TextField dirField;
	@FXML
	private TextField dniField;
	@FXML
	private SplitMenuButton puestoField;
	@FXML
	private Button sendBtn;
	@FXML
	private Button resetBtn;
	@FXML
	private Label errorLbl;
	
	String puesto="";
	Concesionario con;

	@FXML
	private void initialize() {
		SetSplitMenus();
		getCon();
	}
	
	private void getCon() {
		
		String hql = "FROM Concesionario WHERE codCon=1";
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		org.hibernate.Query query = null;
		query = session.createQuery(hql);
		con = (Concesionario) query.uniqueResult();
	}

	@FXML
	/**
	 * Este método crea un cliente en la base de datos
	 */
	private void crearEmpleado() {
		errorLbl.setText("");
		// Si los campos están llenos:
		if (camposLlenos()) {
			
			Date date=new Date();
			// Definimos el cliente y le asignamos la información necesaria
			Empleado empleado = new Empleado();
			empleado.setNomEmp(nameField.getText());
			empleado.setApeEmp(surnameField.getText());
			empleado.setDirEmp(dirField.getText());
			empleado.setGmaEmp(mailField.getText());
			empleado.setTlfEmp(tlfField.getText());
			empleado.setDniEmp(dniField.getText());
			empleado.setConEmp("");
			empleado.setConcesionario(con);
			empleado.setFecIni(date);

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session sesion = sessionFactory.openSession();

			Transaction transaction = sesion.beginTransaction();

			// Guardamos el cliente
			sesion.save(empleado);
			

			if(puesto.equals("Mecanico")) {
				Mecanico mec=new Mecanico();
				mec.setEmpleado(empleado);
				mec.setJefMec(false);
				sesion.save(mec);
				
			}else if(puesto.equals("MecanicoJefe")) {
				Mecanico mec=new Mecanico();
				mec.setEmpleado(empleado);
				mec.setJefMec(true);
				sesion.save(mec); 
				
			}else if(puesto.equals("Venta")) {
				Ventas ven=new Ventas();
				ven.setEmpleado(empleado);
				sesion.save(ven);
				
			}else if(puesto.equals("Jefe")) {
				Jefe jef=new Jefe();
				jef.setEmpleado(empleado);
				sesion.save(jef);
			}

			// Lo subimos a la base de datos
			transaction.commit();

			sesion.close();
			resetFields();
		} else {
			if (errorLbl.getText().isEmpty())
				errorLbl.setText("Algunos campos están vacios");
		}
	}
	
	private void SetSplitMenus() {
		TipoPuesto[] listaPuestos = TipoPuesto.values();
		MenuItem nuevoItem;

		for (int i = 0; i < listaPuestos.length; i++) {
			nuevoItem = new MenuItem(listaPuestos[i].name());
			nuevoItem.setOnAction(a -> {
				String tituloItem = ((MenuItem) a.getSource()).getText();
				puestoField.setText(tituloItem);
				puesto=tituloItem;
			});

			puestoField.getItems().addAll(nuevoItem);
		}
	}
	
	private enum TipoPuesto {
		Jefe,Mecanico,Venta,MecanicoJefe
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
		dniField.setText("");
		puestoField.setText("");
		puesto="";
	}

	/**
	 * Este método comprueba si los campos están llenos
	 * 
	 * @return
	 */
	private boolean camposLlenos() {

		// Si el campo está vacío:
		if (nameField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (surnameField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (tlfField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (mailField.getText().isEmpty()) {
			return false;
		}
		// Si el campo está vacío:
		if (dirField.getText().isEmpty()) {
			return false;
		}
		if(puesto.isEmpty()) {
			return false;
		}
		if(dniField.getText().isEmpty()) {
			
		}
		return true;
	}
}