package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

abstract public class Publication implements Serializable {
//	public enum PUBTYPE {
//		BOOK,
//		PERIODICAL;
//	};

	private static final long serialVersionUID = 2010893663327964921L;
	private LocalDate dateDue;
	private String title;
	private List<LendableCopy> Copys = new ArrayList<>();
	int maxCheckoutLength;
	
	protected void setDateDue(LocalDate d) {
		dateDue = d;
	}
	public Publication(String title, int dateDue) {
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
	}
	public LocalDate getDateDue() {
		return dateDue;
	}
	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}
	public String getTitle() {
		return title;
	}
	public boolean addCopy() {
		LendableCopy copy = new LendableCopy(this);
		Copys.add(copy);
		return true;
	}
	public String toString() {
		return 	"MaxCheckoutLength : " + maxCheckoutLength + 
				", Title: " + title + 
				", DateDue: " + getDateDue();
	}
}
