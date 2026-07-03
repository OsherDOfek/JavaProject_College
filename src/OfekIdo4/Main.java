package OfekIdo4;

import java.util.Scanner;
import java.util.Comparator;
import java.util.InputMismatchException;
public class Main {
	//Ido David 322558339
	//Ofek Osher 212482616
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter college name: ");
		College c1 = new College(s.next());
		int choice = -1;
		while (choice != 0) {
		    System.out.println("Choose an option: \n"
		    	+ "0 ---> exit\n"
		    	+ "1 ---> add lecturer to the college\n"
		   		+ "2 ---> add a new committee\n"
		   		+ "3 ---> add committee member\n"
		   		+ "4 ---> replace head of a committee\n"
		   		+ "5 ---> remove a committee member\n"
	    		+ "6 ---> add department\n"
	    		+ "7 ---> add lecturer to a department\n"
		    	+ "8 ---> show the averege salary of all lecturers\n"
		    	+ "9 ---> show the averege salary of all lecturers in a specific department\n"
		   		+ "10 ---> show lecturers' personal information\n"
		   		+ "11 ---> show all Committees information\n"
		   		+ "12 ---> compare Doctors/Professors by the number of papers the published\n"
		   		+ "13 ---> compare committees\n"
		   		+ "14 ---> duplicate a committee\n"
		   		+ "15 ---> add a paper to a Doctor/Professor");

		    try {
		    	choice = s.nextInt();
		    }
		    catch (InputMismatchException e) {
		    	System.out.println("Invalid input, please enter a number");
		    	s.nextLine();	// consume the bad token so the next read doesn't fail again
		    	continue;
		    }

		    switch (choice) {
		        case 1: {
		            System.out.println("Enter the name of the lecturer:");
		            String lec_name = s.next();
		            try {
		            	c1.checkValidName(lec_name);
		            }
		            catch(InvalidNameException e) {
		            	System.out.println(e);
		            	break;
		            }
		            System.out.println("Enter ID:");
		            String lec_id = s.next();
		            try {
		            	c1.check_lecturer_id(lec_id);
		            }
		            catch(InvalidIdException e){
		                System.out.println(e);
		                break;
		            }
		            System.out.println("Enter degree title\n"
		                    + "1 --> Bachelor\n"
		                    + "2 --> Master\n"
		                    + "3 --> PHD\n"
		                    + "4 --> Professor");
		            int degree_choice = s.nextInt();

		            if (degree_choice < 1 || degree_choice > 4) {
		                System.out.println("Invalid input");
		                break;
		            }

		            System.out.println("Enter degree name:");
		            s.nextLine();
		            String lec_degree_name = s.nextLine();

		            System.out.println("Enter lecturer's salary:");
		            int lec_salary = 0;
		            try {
		            	lec_salary = s.nextInt();
		            }
		            catch(InputMismatchException e) {
		            	throw new InputMismatchException();
		            }


		            System.out.println("Enter lecturer department (press Enter for none):");
		            s.nextLine();
		            String dep_name = s.nextLine();
		            if (!dep_name.equals("") && c1.get_department(dep_name) == null) {
		                System.out.println("Couldn't find the department");
		                break;
		            }

		            String granting_body = "";
		            if (degree_choice == 4) {
		                System.out.println("Enter the body that granted the professorship:");
		                granting_body = s.nextLine();
		            }

		            try {
		            	Lecturer temp_lec;
		            	if(degree_choice <= 2) {
		            		temp_lec = new BachelorOrMaster(lec_name, lec_id,lec_degree_name, lec_salary, dep_name);
		            	}
		            	else if(degree_choice == 3) {
		            		temp_lec = new Doctor(lec_name, lec_id,lec_degree_name, lec_salary, dep_name);
		            	}
		            	else {
		            		temp_lec = new Professor(lec_name, lec_id,lec_degree_name, lec_salary, dep_name, granting_body);
		            	}
		            	c1.add_lecturer(temp_lec);
		            	System.out.println("Lecturer added successfully");
		            }
		            catch(InvalidSalaryException | LecturerAlreadyExistsException e) {
		            	System.out.println(e);
		            }
		            break;
		        }

		        case 2: {
		            System.out.println("Enter committe name:");
		            String com_name = s.next();
		            System.out.println("Enter the degree level of the committee:\n"
		            				 + "1 ---> Bachelor and Master\n"
		            				 + "2 ---> Doctor\n"
		            				 + "3 ---> Professor");
		            int level_choice = s.nextInt();
		            if(level_choice < 1 || level_choice > 3) {
		            	System.out.println("Invalid input");
		            	break;
		            }
		            
		            System.out.println("Enter committe's head name:");
		            String head_name = s.next();
		            Lecturer head = c1.get_lecturer(head_name);
		            if (head == null) {
		                System.out.println("Lecturer wasn't found in college");
		                break;
		            }
		            try {
		            	c1.add_com(com_name, head,level_choice);
		            	System.out.println(com_name + " added successfully!");
		            }
		            catch(CommitteeAlreadyExistsException | InvalidDegreeException e) {
		            	System.out.println(e);
		            }
		            break;
		        }

		        case 3: {
		            if (c1.getNumOfCommittee() == 0) {
		                System.out.println("There are no committees");
		                break;
		            }
		            System.out.println("Choose a committee");
		            printComList(c1);
		            int com_choice = s.nextInt();
		            if (com_choice < 1 || com_choice > c1.getNumOfCommittee()) {
		                System.out.println("Invalid input!");
		                break;
		            }
		            if (c1.getNumOfLecturer() == 0) {
		                System.out.println("There are no lecturers");
		                break;
		            }
		            System.out.println("choose lecturer to add:");
		            printLecList(c1);
		            int lec_choice = s.nextInt();
		            if (lec_choice < 1 || lec_choice > c1.getNumOfLecturer()) {
		                System.out.println("Invalid input!");
		                break;
		            }
		            Committee comm = c1.getCom()[com_choice - 1];
		            Lecturer toAdd = c1.getLec()[lec_choice - 1];
		            try {
		            	comm.add_com_mem(toAdd);
		            	System.out.println("Member added successfully");
		            }
		            catch(AlreadyCommitteeMemberException e) {
		            	System.out.println(e);
		            }
		            break;
		        }

		        case 4: {
		            if (c1.getNumOfCommittee() == 0) {
		                System.out.println("There are no committees");
		                break;
		            }
		            System.out.println("Choose a committee");
		            printComList(c1);
		            int com_num = s.nextInt();
		            if (com_num < 1 || com_num > c1.getNumOfCommittee()) {
		                System.out.println("Invalid input!");
		                break;
		            }
		            if (c1.getNumOfLecturer() == 0) {
		                System.out.println("There are no lecturers");
		                break;
		            }
		            System.out.println("Choose a lecturer to assign as head: ");
		            printLecList(c1);
		            int lec_num = s.nextInt();
		            if (lec_num < 1 || lec_num > c1.getNumOfLecturer()) {
		                System.out.println("Invalid input!");
		                break;
		            }
		            Lecturer newHead = c1.getLec()[lec_num - 1];
		            try {
		            	c1.getCom()[com_num - 1].setHead_of_com(newHead);
		            	System.out.println("head of committee changed successfully");
		            }
		            catch(InvalidDegreeException e) {
		            	System.out.println(e);
		            }
		            break;
		        }

		        case 5: {
		            if (c1.getNumOfCommittee() == 0) {
		                System.out.println("There are no committees");
		                break;
		            }
		            System.out.println("Choose a committee");
		            printComList(c1);
		            int com = s.nextInt();
		            if (com < 1 || com > c1.getNumOfCommittee()) {
		                System.out.println("Invalid input!");
		                break;
		            }
		            Committee comm = c1.getCom()[com - 1];
		            if (comm.getMembers().size() == 0) {
		                System.out.println("This committee has no members to remove");
		                break;
		            }
		            System.out.println("choose lecturer to remove:");
		            printMembers(comm);
		            int lec = s.nextInt();
		            if (lec < 1 || lec > comm.getMembers().size()) {
		                System.out.println("Invalid input!");
		                break;
		            }
		            String lec_to_remove = ((Lecturer)comm.getMembers().get(lec)).getName();
		            try {
		            	comm.remove_lecturer(lec_to_remove);
		            	System.out.println("Member removed successfully");
		            }
		            catch(MemberNotFoundException e) {
		            	System.out.println(e);
		            }
		            break;
		        }

		        case 6: {
		            System.out.println("Enter new department name: ");
		            String depName = s.next();
		            try{
		            	c1.department_exists(depName);
		            }
		            catch(DepartmentAlreadyExistsException e){
		                System.out.println(e);
		                break;
		            }
		            System.out.println("Enter number of student in the department:");
		            int student_num = s.nextInt();
		            try {
		            	c1.add_department(depName, student_num);
		            	System.out.println("Department added successfully");
		            }
		            catch(DepartmentAlreadyExistsException | InvalidStudentNumberException e) {
		            	System.out.println(e);
		            }
		            break;
		        }

		        case 7: {
		            if (c1.getNumOfDepartment() == 0) {
		                System.out.println("There are no departments");
		                break;
		            }
		            System.out.println("Choose department: ");
		            printDepList(c1);
		            int dep_choice = s.nextInt();
		            if (dep_choice < 1 || dep_choice > c1.getNumOfDepartment()) {
		                System.out.println("Invalid input");
		                break;
		            }
		            if (c1.getNumOfLecturer() == 0) {
		                System.out.println("There are no lecturers");
		                break;
		            }
		            System.out.println("Choose a lecturer: ");
		            printLecList(c1);
		            int lec_number = s.nextInt();
		            if (lec_number < 1 || lec_number > c1.getNumOfLecturer()) {
		                System.out.println("Invalid input");
		                break;
		            }
		            Lecturer chosen = c1.getLec()[lec_number - 1];
		            // A lecturer can be assigned to at most one department
		            if (!chosen.getDep_name().equals("")) {
		                System.out.println(chosen.getName() + " is already assigned to department: " + chosen.getDep_name());
		                break;
		            }
		            Department d = c1.getDep()[dep_choice - 1];
		            try {
		            	d.add_lecturer(chosen);
		            	chosen.setDep_name(d.getDep_name());	// keep the single department in sync
		            	System.out.println("Lecturer added successfully");
		            }
		            catch(AlreadyInDepartmentException e) {
		            	System.out.println(e);
		            }
		            break;
		        }

		        case 8: {
		            if (c1.getNumOfLecturer() == 0)
		                System.out.println("There are no lecturers in the college");
		            else
		                System.out.println("The average salary is: " + c1.avg_salary_college());
		            break;
		        }

		        case 9: {
		            System.out.println("Enter department name:");
		            String dep = s.next();
		            Department d = c1.get_department(dep);
		            if (d == null)
		                System.out.println("Department not found");
		            else
		                System.out.println(dep + " average salary is: " + d.avg_salary());
		            break;
		        }

		        case 10: {
		            if (c1.getNumOfLecturer() == 0)
		                System.out.println("There are no lecturers");
		            else
		                for (int i = 0; i < c1.getNumOfLecturer(); i++)
		                    System.out.println(c1.getLec()[i].toString());
		            break;
		        }

		        case 11: {
		            if (c1.getNumOfCommittee() == 0)
		                System.out.println("There are no committees");
		            else
		                for (int i = 0; i < c1.getNumOfCommittee(); i++)
		                    System.out.println(c1.getCom()[i].toString());
		            break;
		        }
		        case 12:{
		        	if(c1.getNumOfLecturer() == 0) {
		        		System.out.println("No lecturers found");
		        		break;
		        	}
		        	System.out.println("Choose first lecturer:");
		        	printLecList(c1);
		        	int cmp_choice = s.nextInt();
		        	if(cmp_choice < 1 || cmp_choice > c1.getNumOfLecturer()) {		// checks if valid
		        		System.out.println("Wrong input!");
		        		break;
		        	}
		        	if(!(c1.getLec()[cmp_choice-1] instanceof Doctor))
		        	{
		        		System.out.println("Cant compare him/her/them");
		        		break;
		        	}
		        	Comparable first_lec = (Doctor)c1.getLec()[cmp_choice-1];		// save the first doctor/professor

		        	System.out.println("Choose second lecturer:");
		        	printLecList(c1);
		        	cmp_choice = s.nextInt();
		        	if(cmp_choice < 1 || cmp_choice > c1.getNumOfLecturer()) {		// checks if valid
		        		System.out.println("Wrong input!");
		        		break;
		        	}
		        	if(!(c1.getLec()[cmp_choice-1] instanceof Doctor))
		        	{
		        		System.out.println("Cant compare him/her/them");
		        		break;
		        	}
		        	Comparable second_lec = (Doctor)c1.getLec()[cmp_choice-1];		// save the second doctor/professor
		        	if(first_lec.compareTo(second_lec) == -1)
		        		System.out.println(((Doctor)second_lec).getName() + " has more papers than " + ((Doctor)first_lec).getName());
		        	else if(first_lec.compareTo(second_lec) == 1)
		        		System.out.println(((Doctor)first_lec).getName() + " has more papers than " + ((Doctor)second_lec).getName());
		        	else
		        		System.out.println("Same number of papers");
		        	break;
		        }

		        case 13:{
		        	if(c1.getNumOfCommittee() == 0) {
		        		System.out.println("No committees found");
		        		break;
		        	}
		        	System.out.println("Choose how to compare:\n1 ---> by number of members\n2 ---> by amount of papers of members");
		        	int cmp_choice = s.nextInt();
		        	if(cmp_choice != 1 && cmp_choice != 2) {
		        		System.out.println("Wrong input");
		        		break;
		        	}
		        	System.out.println("Choose a committee");
		        	printComList(c1);
		        	int com_choice = s.nextInt();
		        	if(com_choice < 1 || com_choice > c1.getNumOfCommittee()) {
		        		System.out.println("Wrong input");
		        		break;
		        	}
		        	Committee toCompare1 = c1.getCom()[com_choice-1];

		        	System.out.println("Choose a committee:");
		        	printComList(c1);
		        	com_choice = s.nextInt();
		        	if(com_choice < 1 || com_choice > c1.getNumOfCommittee()) {
		        		System.out.println("Wrong input");
		        		break;
		        	}
		        	Committee toCompare2 = c1.getCom()[com_choice-1];


		        	if(cmp_choice == 1) {		// compare by number of members
		        		Comparator<Committee> comparator = new CompareCommitteeByMembers();
		        		int sub = comparator.compare(toCompare1, toCompare2);
		        		if(sub > 0)
		        			System.out.println(toCompare1.getName() + " has more members than " + toCompare2.getName());
		        		else if(sub < 0)
		        			System.out.println(toCompare2.getName() + " has more members than " + toCompare1.getName());
		        		else
		        			System.out.println("Same amount of members");
		        	}

		        	else {						// compare by amount of papers
		        		Comparator<Committee> comparator = new CompareCommitteeByPapers();
		        		int sub = comparator.compare(toCompare1, toCompare2);
		        		if(sub > 0)
		        			System.out.println(toCompare1.getName() + " has more papers than " + toCompare2.getName());
		        		else if(sub < 0)
		        			System.out.println(toCompare2.getName() + " has more papers than " + toCompare1.getName());
		        		else
		        			System.out.println("Same amount of papers");
		        	}
		        	break;
		        }

		        case 14:{
		        	if(c1.getNumOfCommittee() == 0) {
		        		System.out.println("No committees found");
		        		break;
		        	}
		        	System.out.println("Choose a committee to clone:");
		        	printComList(c1);
		        	int com_choice = s.nextInt();
		        	if(com_choice < 1 || com_choice > c1.getNumOfCommittee()) {
		        		System.out.println("Wrong input");
		        		break;
		        	}
		        	String com_name = c1.getCom()[com_choice-1].getName();
		        	try {
		        		c1.duplicate_committee(com_name);
		        		System.out.println("Committee duplicated successfully");
		        	}
		        	catch(CommitteeAlreadyExistsException | InvalidDegreeException | AlreadyCommitteeMemberException e) {
		        		System.out.println(e);
		        	}
		        	break;
		        }

		        case 15: {
		        	if (c1.getNumOfLecturer() == 0) {
		        		System.out.println("There are no lecturers");
		        		break;
		        	}
		        	System.out.println("Choose a lecturer:");
		        	printLecList(c1);
		        	int lec_choice = s.nextInt();
		        	if (lec_choice < 1 || lec_choice > c1.getNumOfLecturer()) {
		        		System.out.println("Invalid input!");
		        		break;
		        	}
		        	Lecturer chosen = c1.getLec()[lec_choice - 1];
		        	if (!(chosen instanceof Doctor)) {
		        		System.out.println(chosen.getName() + " isn't a Doctor/Professor, can't add a paper");
		        		break;
		        	}
		        	System.out.println("Enter the paper's title:");
		        	s.nextLine();
		        	String paper = s.nextLine();
		        	((Doctor) chosen).add_paper(paper);
		        	System.out.println("Paper added successfully");
		        	break;
		        }

		        case 0:{
		            System.out.println("Exiting...");
		            break;
				}

		        default:
		            System.out.println("Invalid choice, please try again.");
		            break;

		    }
		}
		s.close();
	}

	private static void printComList(College c) {
		for (int i = 0; i < c.getNumOfCommittee(); i++)
			System.out.println((i + 1) + "---> " + c.getCom()[i].getName());
	}

	private static void printLecList(College c) {
		for (int i = 0; i < c.getNumOfLecturer(); i++)
			System.out.println((i + 1) + "---> " + c.getLec()[i].getName());
	}

	private static void printDepList(College c) {
		for (int i = 0; i < c.getNumOfDepartment(); i++)
			System.out.println((i + 1) + "---> " + c.getDep()[i].getDep_name());
	}

	private static void printMembers(Committee comm) {
		for (int i = 0; i < comm.members.size(); i++)
			System.out.println((i + 1) + "---> " + ((Lecturer)comm.getMembers().get(i)).getName());
	}
}
