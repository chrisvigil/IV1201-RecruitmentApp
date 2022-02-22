package se.kth.iv1201.iv1201recruitmentapp.controller.dto;

import se.kth.iv1201.iv1201recruitmentapp.model.Person;

/**
 * The application search dto object representing
 * the search parameters.
 */
public class ApplicationsRequestDto {
    private String searchType;
    private String searchText;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
