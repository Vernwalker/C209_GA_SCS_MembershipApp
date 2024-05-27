package C209_GA;

import java.util.Date;

/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 5 Jun 2022 1:19:19 am
 */

/**
 * @author 21045050
 *
 */
public class Ordinary extends Member {
	
	private Date MemberUntil;
	
	public Ordinary(String ID, String name, String category, Date memberUntil) {
		super(ID, name, category);
		this.MemberUntil = memberUntil;
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
	
	public Date getMemberUntil() {
		return MemberUntil;
	}
			
}
