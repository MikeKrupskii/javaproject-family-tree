package familyTree.service;

import java.util.HashMap;
import java.util.List;

import familyTree.model.Member;

public class FamilyTreeService {
	/**
	 * Service used by FamilyTreeController that is needed to perform CRUD
	 * operations on the Tree - Create, Read, Update, Delete
	 * 
	 */

	/**
	 * Static property - lastID, represtended with integer
	 */
	private static int lastID;
	/**
	 * HashMap of all members in the tree, where key is the ID of the member
	 */
	private static HashMap<Integer, Member> allMembers = new HashMap<Integer, Member>();

	/**
	 * 
	 * @return HashMap
	 */
	public static HashMap<Integer, Member> getAllMembers() {
		return allMembers;
	}

	/**
	 * 
	 * @param id
	 *            int ID
	 * @return Member
	 */
	public static Member getMember(int id) {
		return allMembers.get(id);
	}

	/**
	 * 
	 * @param root
	 *            Member root of the tree
	 */
	public static void addRoot(Member root) {
		allMembers.put(root.getId(), root);
	}

	/**
	 * 
	 * @param newMember
	 *            Member to add
	 * @param parent
	 *            Parent member
	 */
	public static void addOrUpdateMember(Member newMember, Member parent) {
		// adding new member to the allMember list
		allMembers.put(newMember.getId(), newMember);

		// perparing a new list of successors for the parent of the new member
		List<Member> newSuccessors = allMembers.get(parent.getId()).getSuccessors();
		newSuccessors.add(newMember);

		// updating the parent in the list
		parent.setSuccessors(newSuccessors);
		allMembers.put(parent.getId(), parent);
	}

	/**
	 * 
	 * @param memberToDelete
	 *            Member that needs to be deleted
	 */
	public static void removeMember(Member memberToDelete) {
		allMembers.remove(memberToDelete.getId());
		Member parent = findParent(memberToDelete);
		List<Member> newSuccessors = parent.getSuccessors();
		newSuccessors.remove(memberToDelete);
		parent.setSuccessors(newSuccessors);
		allMembers.put(parent.getId(), parent);
	}

	/**
	 * 
	 * @param member
	 *            Member to find parent for
	 * @return Member
	 */
	public static Member findParent(Member member) {
		Member parent = null;

		for (Member m : allMembers.values()) {
			if (m.getSuccessors().contains(member)) {
				parent = m;
			}
		}

		return parent;
	}

	/**
	 * 
	 * @return int
	 * 
	 */
	public static int getLastID() {
		return lastID;
	}

	/**
	 * 
	 * @param lastID
	 *            int last ID
	 */
	public static void setLastID(int lastID) {
		FamilyTreeService.lastID = lastID;
	}
}
