package OfekIdo4;
import java.util.ArrayList;
public class Committee<T extends Lecturer> implements Cloneable {
    protected String name;
    protected ArrayList<T> members;    
    protected Lecturer head_of_com;    

    public Committee(String name, Lecturer head_of_com) throws InvalidDegreeException {
        if (head_of_com != null && !(head_of_com instanceof Doctor))
            throw new InvalidDegreeException();
        this.name = name;
        this.head_of_com = head_of_com;
        this.members = new ArrayList<>();
        if (head_of_com != null) head_of_com.addCommittee(this);
    }

    public void add_com_mem(T l) throws AlreadyCommitteeMemberException {
        if (head_of_com != null && head_of_com.equals(l))
            throw new AlreadyCommitteeMemberException();
        if (members.contains(l))
            throw new AlreadyCommitteeMemberException();
        members.add(l);
        l.addCommittee(this);
    }


    // Checks whether a lecturer is a regular member of the committee
    public boolean isMember(String lec_name) {
        for (int i = 0; i < this.members.size(); i++) {
            if (this.members.get(i).getName().equals(lec_name))
                return true;
        }
        return false;
    }

	 // Update the committee head
	public void update_head(Lecturer lec) throws InvalidDegreeException {
	       // Check degree requirement (Must be PhD or Professor)
	    if (!(lec instanceof Doctor)) {
	        throw new InvalidDegreeException();
	    }
	    // If the new head was a regular member, remove them from the members array
	    // (this also unlinks the committee from their personal list)
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
	    lec.addCommittee(this);		// the new head is now linked to this committee (this)
	}
	
	public int sum_of_papers() {
		int sum = 0;
		for(int i = 0; i < this.members.size(); i++) {
			if(this.members.get(i) instanceof Doctor)
				sum += ((Doctor)this.members.get(i)).getNumOfPapers();
		}
		return sum;
	}

        // Remove a lecturer from the committee
	public void remove_lecturer(String lec_name) throws MemberNotFoundException {
	    for (int i = 0; i < this.members.size(); i++) {
	        if (this.members.get(i).getName().equals(lec_name)) {
	            this.members.remove(i);
	            return;
	        }
	    }
	    throw new MemberNotFoundException();
	}

	public Committee<T> clone() throws CloneNotSupportedException {
		return (Committee<T>)super.clone();
	}

	// Builds a brand new committee named <name>-new with the same head and the same members
	
	public Committee<T> duplicate() throws InvalidDegreeException, AlreadyCommitteeMemberException {
		Committee<T> copy = new Committee<T>(this.name + "-new", this.head_of_com);
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
            Committee<T> other = (Committee<T>) o;
            return this.name.equals(other.name);
        }
        
        // toString
        public String toString() {
        	String level;
        	if (this.members.get(0) instanceof Doctor) level = "Doctor";
        	else if (this.members.get(0) instanceof Professor) level = "Professor";
        	else level = "Bachelor and Master";
            String membersStr = "[";
            for (int i = 0; i < this.members.size(); i++) {
                membersStr += this.members.get(i).getName();
                if (i < this.members.size() - 1) {
                    membersStr += ", ";
                }
            }
            membersStr += "]";

            return "Committee --> Name: " + this.name + ", Head: " + (this.head_of_com != null ? this.head_of_com.getName() : "None") + " Members are: " + level + " , Members: " + membersStr;
        }
    }
