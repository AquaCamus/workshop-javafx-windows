package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{

	private DepartmentService service;
	
	@FXML
	private Button btNew;
	
	@FXML
	private TableView<Department> tableView;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	
	/*Departamentos precisam ser carregados nessa ObservableLIst*/
	private ObservableList<Department> obsList;
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	public void initializeNodes() {
		
		//Inicia o comportamento das colunas ta tabela
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		/*Guardamos uma referência ao Stage
		Main.getMainScene().getWindow() -> Acessamos a as propriedades da janela de cena principal e pegamos
		a referência para o tamanho dessa janela.
		É necessário fazer um downcasting de Window para Stage. Window é superclasse de Stage*/
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		/*Definimos as propriedades PREFERENCIAIS de altura da tableView
		O método bind "liga" as propriedades da tableView e do stage
		No caso, aqui estamos ligando as propriedades de altura, fazendo com o que a tableview tenha
		a mesma altura do stage*/ 
		tableView.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	/*Este método será responsável por acessar o serviço, carregar os departamentos e passá-los para a obsList
	 * Só então a obsLIst será associada á tableView e os dados mostrados na lista.*/
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service Was Null");
		}
		
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableView.setItems(obsList);
		
	}
	
}
