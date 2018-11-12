package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import familyTree.model.Member;
import familyTree.service.FamilyTreeService;

public class TestTreeMember {

	Member newMember = null;
	Member parent = null;

	@Before
	public void before() {
		FamilyTreeService.setLastID(0);
		newMember = new Member("MemberName", "MemberLastName", LocalDate.of(1993, 1, 1), "Female");
		parent = new Member("ParentName", "ParentLastName", LocalDate.of(1993, 1, 1), "Female");
		FamilyTreeService.addRoot(parent);
		FamilyTreeService.addOrUpdateMember(newMember, parent);
	}

	@Test
	void testToString() {
		assertEquals("MemberName MemberLastName", newMember.toString());
	}

	@Test
	void testGetSuccessors() {
		Member anotherMember = new Member("AnotherName", "AnotherLastName", LocalDate.of(1994, 12, 31), "Male");
		FamilyTreeService.addOrUpdateMember(anotherMember, parent);
		List<Member> kids = parent.getSuccessors();
		assertEquals(newMember, kids.get(0));
		assertEquals(anotherMember, kids.get(1));
	}

	@Test
	void testAddOrUpdateMember() {
		Member anotherMember = new Member("AnotherName", "AnotherLastName", LocalDate.of(1994, 12, 31), "Male");
		FamilyTreeService.addOrUpdateMember(anotherMember, parent);
		assertEquals(parent.getSuccessors().get(1), anotherMember);
	}

	@Test
	void testfindParent() {
		Member found = FamilyTreeService.findParent(newMember);
		assertSame(parent, found);
	}

	@Test
	void testRemoveMember() {
		Member anotherMember = new Member("AnotherName", "AnotherLastName", LocalDate.of(1994, 12, 31), "Male");
		FamilyTreeService.addOrUpdateMember(anotherMember, parent);
		FamilyTreeService.removeMember(newMember);
		List<Member> kids = parent.getSuccessors();
		assertSame(anotherMember, kids.get(0));
	}

}
