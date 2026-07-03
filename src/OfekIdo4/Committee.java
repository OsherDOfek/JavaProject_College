package OfekIdo4;
import java.io.Serializable;
import java.util.ArrayList;

// Generic committee (part 1): T is bounded to Lecturer.
// The committee's degree level is decided once, at creation time, and every
// member (except the head) must match it exactly.
public class Committee<T extends Lecturer> implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	public static final int LEVEL_REGULAR = 1;	// Bachelor or Master
	public static final int LEVEL_DOCTOR = 2;
	public static final int LEVEL_PROFESSOR = 3;

	protected String name;
	protected ArrayList<T> members;		// part 2: ArrayList instead of array + counter
	protected Lecturer head_of_com;		// the head is exempt from the homogeneity rule
	protected final int required_level;	// decided at creation time, never changes

	// Committee constructor (head must be at least a Doctor, level must be 1-3)
	public Committee(String name, Lecturer head_of_com, int required_level) throws InvalidDegreeException {
		if (required_level < LEVEL_REGULAR || required_level > LEVEL_PROFESSOR) {
			throw new InvalidDegreeException();
		}
		if (head_of_com != null && !(head_of_com instanceof Doctor)) {
			throw new InvalidDegreeException();
		}
		this.name = name;
		this.required_level = required_level;
		this.head_of_com = head_of_com;
		this.members = new ArrayList<>();
		if (this.head_of_com != null) {
			this.head_of_com.addCommittee(this);	// link the head to this committee
		}
	}

	// Add a new lecturer to the committee.
	// Homogeneity check (part 1): the lecturer's level must match the committee's level.
	public void add_com_mem(T l) throws AlreadyCommitteeMemberException, IncompatibleMemberDegreeException {
		if (this.head_of_com != null && this.head_of_com.equals(l)) {
			throw new AlreadyCommitteeMemberException();	// already the head
		}
		if (this.members.contains(l)) {
			throw new AlreadyCommitteeMemberException();	// already a member
		}
		if (l.getLevel() != this.required_level) {
			throw new IncompatibleMemberDegreeException();	// wrong degree for this committee
		}
		this.members.add(l);
		l.addCommittee(this);	// link the lecturer to this committee
	}

	// Checks whether a lecturer is a regular member of the committee
	public boolean isMember(String lec_name) {
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i).getName().equals(lec_name))
				return true;
		}
		return false;
	}

	// Update the committee head (must be PhD or Professor)
	public void update_head(Lecturer lec) throws InvalidDegreeException {
		if (!(lec instanceof Doctor)) {
			throw new InvalidDegreeException();
		}
		// If the new head was a regular member, remove them from the members list
		if (isMember(lec.getName())) {
			try {
				remove_lecturer(lec.getName());
			} catch (MemberNotFoundException e) {
				// unreachable: isMember() just confirmed lec is a member
			}
		}
		// unlink the committee from the previous head
		if (this.head_of_com != null)
			this.head_of_com.removeCommittee(this.name);
		this.head_of_com = lec;
		lec.addCommittee(this);	// the new head is now linked to this committee
	}

	public int sum_of_papers() {
		int sum = 0;
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i) instanceof Doctor)
				sum += ((Doctor) this.members.get(i)).getNumOfPapers();
		}
		return sum;
	}

	// Remove a lecturer from the committee (and unlink it from the lecturer's list)
	public void remove_lecturer(String lec_name) throws MemberNotFoundException {
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i).getName().equals(lec_name)) {
				T removed = this.members.remove(i);
				removed.removeCommittee(this.name);
				return;
			}
		}
		throw new MemberNotFoundException();
	}

	@SuppressWarnings("unchecked")
	public Committee<T> clone() throws CloneNotSupportedException {
		return (Committee<T>) super.clone();
	}

	// Builds a brand new committee named <name>-new with the same head, level and members
	public Committee<T> duplicate() throws InvalidDegreeException, AlreadyCommitteeMemberException, IncompatibleMemberDegreeException {
		Committee<T> copy = new Committee<>(this.name + "-new", this.head_of_com, this.required_level);
		for (int i = 0; i < this.members.size(); i++) {
			copy.add_com_mem(this.members.get(i));
		}
		return copy;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Lecturer getHead_of_com() {
		return head_of_com;
	}

	public void setHead_of_com(Lecturer head_of_com) throws InvalidDegreeException {
		if (head_of_com == null) {
			return;
		}
		update_head(head_of_com);
	}

	public int getRequired_level() {
		return required_level;
	}

	public ArrayList<T> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<T> members) {
		this.members = members;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Committee<?> other = (Committee<?>) o;
		return this.name.equals(other.name);
	}

	// the level label comes from required_level, so it works even for an empty committee
	private String levelName() {
		if (this.required_level == LEVEL_DOCTOR) return "Doctor";
		if (this.required_level == LEVEL_PROFESSOR) return "Professor";
		return "Bachelor and Master";
	}

	// toString
	public String toString() {
		String membersStr = "[";
		for (int i = 0; i < this.members.size(); i++) {
			membersStr += this.members.get(i).getName();
			if (i < this.members.size() - 1) {
				membersStr += ", ";
			}
		}
		membersStr += "]";
		return "Committee --> Name: " + this.name + ", Head: "
				+ (this.head_of_com != null ? this.head_of_com.getName() : "None")
				+ ", Members are: " + levelName() + ", Members: " + membersStr;
	}
}
