package C209_GA;
/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 5 Jun 2022 1:19:28 am
 */

/**
 * @author 21045050
 *
 */


public class Student extends Member {
	
	private String School;

	public Student(String ID, String name, String category, String school) {
		super(ID, name, category);
		this.School = school;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getName() {
		return Name;
	}
	
	public String getCategory() {
		return Category;
	}

	public String getSchool() {
		return School;
	}
		
}
