package C209_GA;

/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 5 Jun 2022 1:19:36 am
 */

/**
 * @author 21045050
 *
 */
public class Lifetime extends Member implements Citation{

	private String Citation;
		
	public Lifetime(String ID, String name, String category, String citation) {
		super(ID, name, category);
		this.Citation = citation;
		
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
	
	public String displayCitation() {
		// TODO Auto-generated method stub
		return Citation;
	}
	
}
