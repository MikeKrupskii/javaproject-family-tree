package familyTree.model;

import java.time.LocalDate;
import java.util.List;

import familyTree.service.FamilyTreeService;
import javafx.collections.FXCollections;

public class Member {

	/**
	 * Class that describes the model of the member class, including its ID, first
	 * and last name, gender, birthday and list of successors.
	 */

	/**
	 * Id of the member represented with int
	 */
	private int id;
	/**
	 * Member firstName represented with String
	 */
	private String firstName;
	/**
	 * Member lastName
	 */
	private String lastName;
	/**
	 * 
	 */
	private LocalDate birthDate;
	private String gender;
	private List<Member> successors = FXCollections.observableArrayList();

	/**
	 * Parameter less constructor
	 */
	public Member() {

	}

	/**
	 * 
	 * @param firstName
	 *            String firstName
	 * @param lastName
	 *            String lastName
	 * @param birthDate
	 *            LocalDate date of birth
	 * @param gender
	 *            String gender
	 */
	public Member(String firstName, String lastName, LocalDate birthDate, String gender) {
		this.id = FamilyTreeService.getLastID() + 1;
		FamilyTreeService.setLastID(this.id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Member> getSuccessors() {
		return successors;
	}

	public void setSuccessors(List<Member> successors) {
		this.successors = successors;
	}

	@Override
	public String toString() {
		return getFirstName() + " " + getLastName();
	}
}
