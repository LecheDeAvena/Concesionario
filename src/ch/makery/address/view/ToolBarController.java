package ch.makery.address.view;

import java.io.IOException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ch.makery.address.MainApp;
import ch.makery.address.models.Empleado;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ToolBarController {

	@FXML
	private AnchorPane root;

	@FXML
	private Hyperlink usernameLbl;

	@FXML
	private VBox rootLayout;

	@FXML
	private ImageView logoEmpresa;

	@FXML
	private ImageView userImage;

	@FXML
	private HBox containerBotones;

	private SessionFactory sf = new Configuration().configure().buildSessionFactory();
	private Session session = sf.openSession();
	private Query query = null;

	private Hyperlink btns[];

	private static Empleado user;

	public void initialize() {
	}

	public void recoverUserInfo(Stage event) {

		Stage stage = event;

		user = (Empleado) stage.getUserData();
		setUserSurname();

		usernameLbl.setText(user.getNomEmp() + " " + user.getApeEmp());
		setFirstScene();
	}

	public Empleado getUserInfo() {
		return user;
	}

	private void setUserSurname() {

		String hql = "SELECT apeEmp " + "FROM Empleado " + "WHERE codEmp='" + user.getCodEmp() + "'";

		session.getTransaction().begin();
		query = session.createQuery(hql);
		user.setApeEmp((String) query.uniqueResult());
	}

	private void setFirstScene() {

		FXMLLoader loader = null;
		Pane newPane = null;
		/*if (isMechanic()) {
			loader = new FXMLLoader(MainApp.class.getResource("view/tareasMecanico_view.fxml"));
			mechanicButtons();
		}else*/ if (isMechanicChief()) {
			loader = new FXMLLoader(MainApp.class.getResource("view/tareasMecanicoJefe_view.fxml"));
			mechanicBossButtons();
		} else if (isSales()) {
			loader = new FXMLLoader(MainApp.class.getResource("view/clientesVentas_view.fxml"));
			salesButtons();
		} else {
			loader = new FXMLLoader(MainApp.class.getResource("view/tareasMecanico_view.fxml"));
			mechanicButtons();
		}
		setButtons();

		try {
			newPane = loader.load();
			newPane.prefHeightProperty().bind(rootLayout.heightProperty());
			newPane.prefWidthProperty().bind(rootLayout.widthProperty());
			;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		rootLayout.getChildren().add(newPane);
	}

	private void setButtons() {
		for (int i = 0; i < btns.length; i++) {
			containerBotones.getChildren().addAll(btns[i]);
		}
	}

	private void changeScene(String file) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(file));
			Pane newPane = loader.load();
			rootLayout.getChildren().remove(rootLayout.getChildren().size() - 1);
			rootLayout.getChildren().add(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isMechanic() {

		String hql = "SELECT m.empleado.codEmp " + "FROM Mecanico m " + "WHERE NOT m.jefMec=0  AND m.empleado.codEmp = "
				+ user.getCodEmp();

		session.getTransaction().begin();
		query = session.createQuery(hql);
		return query.uniqueResult() != null;
	}

	private boolean isMechanicChief() {

		String hql = "SELECT m.empleado.codEmp " + "FROM Mecanico m " + "WHERE m.jefMec=0  AND m.empleado.codEmp = "
				+ user.getCodEmp();

		session.getTransaction().begin();
		query = session.createQuery(hql);

		return query.uniqueResult() != null;
	}

	private boolean isSales() {

		String hql = "SELECT e.codEmp " + "FROM Ventas v INNER JOIN v.empleado e " + "WHERE e.codEmp = "
				+ user.getCodEmp();

		session.getTransaction().begin();
		query = session.createQuery(hql);
		if (query.uniqueResult() != null) {
			return true;
		} else {
			return false;
		}
	}

	private void mechanicButtons() {
		btns = new Hyperlink[1];
		for (int i = 0; i < btns.length; i++) {
			btns[i] = new Hyperlink();
		}
		btns[0].setText("Inicio");
		btns[0].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/tareasMecanico_view.fxml.fxml"));
		btns[0].setDisable(true);
	}

	private void mechanicBossButtons() {
		btns = new Hyperlink[4];
		for (int i = 0; i < btns.length; i++) {
			btns[i] = new Hyperlink();
		}
		btns[0].setText("Tareas");
		btns[0].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/tareasMecanicoJefe_view.fxml"));
		btns[0].setDisable(true);
		btns[1].setText("Nueva Tarea");
		btns[1].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/nuevaTarea_view.fxml"));
		btns[2].setText("Nuevo vehiculo");
		btns[2].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/nuevoCoche_view.fxml"));
		btns[3].setText("Nuevo Cliente");
		btns[3].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/nuevoCliente_view.fxml"));
	}

	private void salesButtons() {
		btns = new Hyperlink[3];
		for (int i = 0; i < btns.length; i++) {
			btns[i] = new Hyperlink();
		}
		btns[0].setText("Clientes");
		btns[0].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/clientesVentas_view.fxml"));
		btns[0].setDisable(true);
		btns[1].setText("Coches");
		btns[1].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/cochesVentas_view.fxml"));
		btns[2].setText("Ventas");
		btns[2].addEventHandler(MouseEvent.MOUSE_CLICKED, btnEventHandler("view/relacionarClientesVentas_view.fxml"));
	}

	private EventHandler<MouseEvent> btnEventHandler(String scene) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeScene(scene);
				for (Node n : containerBotones.getChildren()) {
					if (e.getSource().equals(n)) {
						n.setDisable(true);
					} else {
						n.setDisable(false);
					}
				}
			}
		};
		return eventHandler;
	}
}
