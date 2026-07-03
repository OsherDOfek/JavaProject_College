package OfekIdo4;
public class Committee implements Cloneable{
	protected String name ;
	protected Lecturer[] lec_arr;
	protected int numOfLecturer; // amount of lecturers in the committee
	protected Lecturer head_of_com ;
	protected int degree_level;

	// Committee constructor (head must be at least a Doctor)
	public Committee(String name,Lecturer head_of_com,int degree_level) throws InvalidDegreeException {
		if (head_of_com != null && !(head_of_com instanceof Doctor)) {
			throw new InvalidDegreeException();
		}
		this.name = name;
		this.degree_level = degree_level;
		this.head_of_com = head_of_com;
		this.lec_arr = new Lecturer[2]; //start size
		this.numOfLecturer = 0; //no members at first (head doesn't count as member)
		if (this.head_of_com != null)
			this.head_of_com.addCommittee(this);	// the head is linked to this committee (this)
	}

	 // Add a new lecturer to the committee
    public void add_com_mem(Lecturer l) throws AlreadyCommitteeMemberException {
        // Check if the lecturer is already the committee head
        if (this.head_of_com != null && this.head_of_com.getName().equals(l.getName())) {
            throw new AlreadyCommitteeMemberException();	// already the head
        }

        // Check if the lecturer is already a regular member
        if (isMember(l.getName())) {
            throw new AlreadyCommitteeMemberException();	// already a member
        }
        // check if the lec array needs to be expend
        if(this.lec_arr.length == this.numOfLecturer)
			expend_lec_arr();

        this.lec_arr[this.numOfLecturer] = l;
        this.numOfLecturer++;
        l.addCommittee(this);	// link the lecturer to this committee (this)
    }

    // Checks whether a lecturer is a regular member of the committee
    public boolean isMember(String lec_name) {
        for (int i = 0; i < this.numOfLecturer; i++) {
            if (this.lec_arr[i].getName().equals(lec_name))
                return true;
        }
        return false;
    }

    public void expend_lec_arr() {
        Lecturer[] temp = new Lecturer[this.lec_arr.length*2];
        for(int i = 0; i < this.numOfLecturer; i++)
        	temp[i] = this.lec_arr[i];
        this.lec_arr = temp;
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
		for(int i = 0; i < this.numOfLecturer; i++) {
			if(this.getLec_arr()[i] instanceof Doctor)
				sum += ((Doctor)this.getLec_arr()[i]).getNumOfPapers();
		}
		return sum;
	}

        // Remove a lecturer from the committee
	public void remove_lecturer(String lec_name) throws MemberNotFoundException {
	    for (int i = 0; i < this.numOfLecturer; i++) {
	        if (this.lec_arr[i].getName().equals(lec_name)) {
	            Lecturer removed = this.lec_arr[i];
	            for (int j = i; j < this.numOfLecturer - 1; j++) {
	                this.lec_arr[j] = this.lec_arr[j + 1];
	            }
	            this.lec_arr[this.numOfLecturer - 1] = null;
	            this.numOfLecturer--;
	            removed.removeCommittee(this.name);
	            return;
	        }
	    }
	    throw new MemberNotFoundException();
	}

	public Committee clone() throws CloneNotSupportedException {
		return (Committee)super.clone();
	}

	// Builds a brand new committee named <name>-new with the same head and the same members
	// (unlike clone(), this does not share the internal lec_arr with the original)
	public Committee duplicate() throws InvalidDegreeException, AlreadyCommitteeMemberException {
		Committee copy = new Committee(this.name + "-new", this.head_of_com,this.degree_level);
		for (int i = 0; i < this.numOfLecturer; i++) {
			copy.add_com_mem(this.lec_arr[i]);
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

        public Lecturer[] getLec_arr() {
            return lec_arr;
        }

        public int getNumOfLecturer() {
			return numOfLecturer;
		}

		public void setLec_arr(Lecturer[] lec_arr) {
			this.lec_arr = lec_arr;
		}

		public void setNumOfLecturer(int numOfLecturer) {
			this.numOfLecturer = numOfLecturer;
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
        
        public int getDegree_level() {
			return degree_level;
		}

		@Override
        public boolean equals(Object o) {
            if (this == o) 
            	return true;
            if (o == null || getClass() != o.getClass())
            	return false;
            Committee other = (Committee) o;
            return this.name.equals(other.name);
        }
        
        // toString
        public String toString() {
        	String level = "Bachelor and Master";
        	if(this.degree_level == 2)
        		level = "Doctor";
        	else
        		level = "Professor";
            String membersStr = "[";
            for (int i = 0; i < numOfLecturer; i++) {
                membersStr += lec_arr[i].getName();
                if (i < numOfLecturer - 1) {
                    membersStr += ", ";
                }
            }
            membersStr += "]";

            return "Committee --> Name: " + this.name + ", Head: " + (this.head_of_com != null ? this.head_of_com.getName() : "None") + " Members are: " + level + " , Members: " + membersStr;
        }
    }
