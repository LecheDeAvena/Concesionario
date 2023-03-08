package ch.makery.address.view.repe;
/*
//package ch.makery.address.view;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.models.Empleado;
import ch.makery.address.models.Tarea;
import ch.makery.address.util.MesItem;
import ch.makery.address.view.ToolBarController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TareasMecanicoController {

	@FXML
	private Label monthLbl;
	@FXML
	private GridPane cuadriculas;
	@FXML
	private GridPane diasSemana;
	@FXML
	private Button btnSiguienteMes;
	@FXML
	private Button btnAnteriorMes;
	@FXML
	private Label tPendientesLbl;
	@FXML
	private VBox tareasContainer;

	private ArrayList<Button> days = new ArrayList<>();

	private ArrayList<Tarea> tareasPendientes = new ArrayList<>();
	private ArrayList<Tarea> tareasTerminadas = new ArrayList<>();
	private int tPendientes;

	MesItem mesController = new MesItem();

	private Empleado user;

	@FXML
	public void initialize() {

		setUser();

		createCalendar();

		setTareas();
	}

	public TareasMecanicoController() {

	}

	private void createCalendar() {

		ArrayList<String> mes = mesController.generaMes();

		monthLbl.setText(mesController.getMes());
		Label diaName;
		Button dia;
		for (int i = 0; i < 7; i++) {
			diaName = new Label();
			diaName.setText(mesController.getDias()[i]);
			diasSemana.add(diaName, i, 0);
		}

		int cont = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				dia = new Button();
				dia.setText(mes.get(cont));
				dia.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
				if (mes.get(cont) == "") {
					dia.setDisable(true);
				}
				days.add(dia);
				cuadriculas.add(days.get(days.size() - 1), j, i);
				cont++;
			}
		}
	}

	private void editCalendar() {

		monthLbl.setText(mesController.getMes());
		ArrayList<String> month = mesController.generaMes();

		for (int i = 0; i < days.size(); i++) {
			if (month.get(i) != "") {
				days.get(i).setDisable(false);
				;
			} else {
				days.get(i).setDisable(true);
			}
			days.get(i).setText(month.get(i));
		}
	}

	@FXML
	public void mesSiguiente() {
		mesController.siguienteMes();
		editCalendar();
	}

	@FXML
	public void mesAnterior() {
		mesController.anteriorMes();
		editCalendar();
	}

	private void setUser() {

		ToolBarController tbc = new ToolBarController();
		user = tbc.getUserInfo();

		getTareasEmpleado();
	}

	@SuppressWarnings("unchecked")
	private void getTareasEmpleado() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Query query = null;

		String hql = "SELECT t FROM Mecanico_Tiene_Tarea mt INNER JOIN mt.tarea t INNER JOIN mt.empleado e "
				+ "WHERE e.codEmp=" + user.getCodEmp() + " AND t.fecFin is null";

		session.getTransaction().begin();
		query = session.createQuery(hql);
		List<Tarea> res = query.list();
		for (Tarea tar : res) {
			tareasPendientes.add(tar);
		}

		hql = "SELECT t FROM Mecanico_Tiene_Tarea mt INNER JOIN mt.tarea t INNER JOIN mt.empleado e "
				+ "WHERE e.codEmp=" + user.getCodEmp() + " AND t.fecFin is not null";
		query = session.createQuery(hql);
		res.clear();
		res = query.list();
		for (Tarea t : res) {
			tareasTerminadas.add(t);
		}
		tPendientes = tareasPendientes.size();
	}

	private void setTareas() {

		tPendientesLbl.setText(tPendientesLbl.getText() + tPendientes);
		Hyperlink placeholder;
		for (Tarea tarea : tareasPendientes) {
			placeholder = new Hyperlink();
			placeholder.setText(tarea.getNomTar());
			tareasContainer.getChildren().add(placeholder);
		}
	}
}*/
