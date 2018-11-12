package familyTree;

import java.time.LocalDate;

import javax.naming.InsufficientResourcesException;

import familyTree.model.Member;
import familyTree.service.FamilyTreeService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppStart extends Application {

	FamilyTreeService service = new FamilyTreeService();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/familyTree/view/mainView.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) throws InsufficientResourcesException {
		FamilyTreeService.setLastID(0);
		Member root = new Member("RootFirstName", "RootLastName", LocalDate.of(1900, 1, 1), "Female");
		FamilyTreeService.addRoot(root);
		launch(args);
	}
}
