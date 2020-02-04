package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentListView.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	//Abre uma nova tela
	private <T> void loadView(String abosluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(abosluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			
			/*Guardamos em mainVBox o estado atual do VBox da classe MainView
			mainScene.getRoot() pega o primeiro elemento xml de MainView que é ScrollPane
			acessando internamente esse ScrollPane com .getContent() pegamos o conteúdo interno do mesmo
			que nesse caso é um VBox. Fazemos o casting disso tudo para um ScrollPane e depois um casting para VBox
			para poder ser guardado em mainVBox*/
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			//Agora, precisamos remover os "filhos" do nodo do mainVBox e adicionar novamente junto aos filhos do nodo da aboutview

			//Pegamos o e guardamos o mainMenu que é o primeiro filho do mainVBox
			Node mainMenu = mainVBox.getChildren().get(0); 
			
			//Limpamos o mainVBox
			mainVBox.getChildren().clear();
			
			//Adicionamos novamente o mainMenu ao mainVBox
			mainVBox.getChildren().add(mainMenu);
			
			//Adicionamos o about ao mainVBox
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
			
		}
	}
	
	//Abre uma nova tela
		private void loadView2(String abosluteName) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(abosluteName));
				VBox newVBox = loader.load();
				
				Scene mainScene = Main.getMainScene();
				
				/*Guardamos em mainVBox o estado atual do VBox da classe MainView
				mainScene.getRoot() pega o primeiro elemento xml de MainView que é ScrollPane
				acessando internamente esse ScrollPane com .getContent() pegamos o conteúdo interno do mesmo
				que nesse caso é um VBox. Fazemos o casting disso tudo para um ScrollPane e depois um casting para VBox
				para poder ser guardado em mainVBox*/
				VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
				
				//Agora, precisamos remover os "filhos" do nodo do mainVBox e adicionar novamente junto aos filhos do nodo da aboutview

				//Pegamos o e guardamos o mainMenu que é o primeiro filho do mainVBox
				Node mainMenu = mainVBox.getChildren().get(0); 
				
				//Limpamos o mainVBox
				mainVBox.getChildren().clear();
				
				//Adicionamos novamente o mainMenu ao mainVBox
				mainVBox.getChildren().add(mainMenu);
				
				//Adicionamos o about ao mainVBox
				mainVBox.getChildren().addAll(newVBox.getChildren());
				
				/*loader.getController(); pega a referência do controller da classe que o chamou
				 * E guarda no objeto controller.*/
				DepartmentListController controller = loader.getController();
				
				//Injeta a dependência do service na classe controller
				controller.setDepartmentService(new DepartmentService());
				
				controller.updateTableView();
				
			} catch (IOException e) {
				Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
				
			}
		}

}
