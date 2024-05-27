
/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 5 Jun 2022 12:52:12 am
 */

package C209_GA;


public abstract class Member {
	
	public String ID;
	public String Name;
	public String Category;
		
	public Member(String iD, String name, String category) {
		this.ID = iD;
		this.Name = name;
		this.Category = category;
	}
	
	public abstract String getID();
	
	public abstract String getName();
	
	public abstract String getCategory();
	
}
