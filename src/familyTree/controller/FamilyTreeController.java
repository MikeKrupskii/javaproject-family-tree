package familyTree.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import familyTree.model.Member;
import familyTree.service.FamilyTreeService;
import familyTree.service.SerializationService;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FamilyTreeController implements Initializable {

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private ChoiceBox<String> genderChoiceBox;

	@FXML
	private DatePicker datePickerBox;

	@FXML
	private Button addNewButton;

	@FXML
	private AnchorPane treeScrollPane;

	TreeView<Member> tree = new TreeView<>();

	TreeItem<Member> root = new TreeItem<>(FamilyTreeService.getMember(1));

	ErrorViewController controller = new ErrorViewController();

	@FXML
	private StackPane treePane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeGenderChoiceBox();
		loadTreeView();
		tree.getSelectionModel().selectedItemProperty().addListener(treeSelectionListener);

	}

	private final ChangeListener<TreeItem<Member>> treeSelectionListener = (ov, oldValue, newValue) -> {
		TreeItem<Member> treeItem = newValue;

		if (newValue != null) {
			firstNameField.setText(newValue.getValue().getFirstName());
			lastNameField.setText(newValue.getValue().getLastName());
			datePickerBox.setValue(newValue.getValue().getBirthDate());
			genderChoiceBox.setValue(newValue.getValue().getGender());
		}
	};

	public void loadTreeView() {

		root.setExpanded(true);
		tree.setRoot(root);
		tree.setPrefWidth(650);
		tree.setPrefHeight(650);
		treeScrollPane.getChildren().add(tree);

	}

	@FXML
	public void addNewMember() throws IOException {
		TreeItem<Member> parent = tree.getSelectionModel().getSelectedItem();

		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String gender = genderChoiceBox.getSelectionModel().getSelectedItem();
		LocalDate birthday = datePickerBox.getValue();
		if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()
				|| datePickerBox.getValue() == null) {

			manageErrors("Validation error: empty fields!");

		} else {
			Member newMember = new Member(firstName, lastName, birthday, gender);
			try {
				FamilyTreeService.addOrUpdateMember(newMember, parent.getValue());
				TreeItem<Member> newMemberTreeItem = new TreeItem<>(newMember);
				parent.getChildren().add(newMemberTreeItem);
				parent.setExpanded(true);
			} catch (NullPointerException ex) {
				manageErrors("You need to select parent first!");
			}

		}

		clear();
	}

	@FXML
	public void deleteMember() throws IOException {
		TreeItem<Member> memberToDelete = tree.getSelectionModel().getSelectedItem();
		try {
			if (memberToDelete.getValue() == root.getValue()) {
				manageErrors("Can not delete root of the tree, but you can update it");
			} else {
				FamilyTreeService.removeMember(memberToDelete.getValue());
				memberToDelete.getParent().getChildren().remove(memberToDelete);
			}
		} catch (NullPointerException ex) {
			manageErrors("Select at least one tree member to delete!");
		}
	}

	@FXML
	public void updateMember() throws IOException {
		TreeItem<Member> member = tree.getSelectionModel().getSelectedItem();
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String gender = genderChoiceBox.getSelectionModel().getSelectedItem();
		LocalDate birthday = datePickerBox.getValue();
		if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()
				|| datePickerBox.getValue() == null) {

			manageErrors("Validation error: empty fields!");

		} else {
			member.getValue().setFirstName(firstName);
			member.getValue().setLastName(lastName);
			member.getValue().setBirthDate(birthday);
			member.getValue().setGender(gender);
			tree.refresh();
		}

		clear();
	}

	public void clear() {
		firstNameField.setText("");
		lastNameField.setText("");
	}

	public void initializeGenderChoiceBox() {
		genderChoiceBox.getItems().add("Male");
		genderChoiceBox.getItems().add("Female");
		genderChoiceBox.getSelectionModel().selectFirst();
	}

	public void manageErrors(String errorMessage) throws IOException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/familyTree/view/errorView.fxml"));
		Parent root = loader.load();
		controller = (ErrorViewController) loader.getController();
		controller.setErrorMessage(errorMessage);
		controller.errorManager();
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	public void saveToXML() throws JsonGenerationException, JsonMappingException, IOException {
		for (Member member : FamilyTreeService.getAllMembers().values()) {
			SerializationService.serialize(member);
		}
	}
}
