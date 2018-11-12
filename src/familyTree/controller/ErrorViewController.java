package familyTree.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorViewController {

	/**
	 * Class that manages the errors occured during the user-view interaction.
	 */

	@FXML
	Label warningLabel;

	private String errorMessage;

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void errorManager() {
		warningLabel.setText(errorMessage);
	}

}
