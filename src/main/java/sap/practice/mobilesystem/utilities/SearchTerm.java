package sap.practice.mobilesystem.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTerm {
	private String searchCategory;
	private String searchText;

	public SearchTerm() {
		super();
		this.searchCategory = "";
		this.searchText = "";
	}

	public SearchTerm(String searchCategory, String searchText) {
		super();
		this.searchCategory = searchCategory;
		this.searchText = searchText;
	}

}
