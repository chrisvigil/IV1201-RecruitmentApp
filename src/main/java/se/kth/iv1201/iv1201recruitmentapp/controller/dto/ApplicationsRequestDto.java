package se.kth.iv1201.iv1201recruitmentapp.controller.dto;

import se.kth.iv1201.iv1201recruitmentapp.model.Person;

/**
 * The application search dto object representing
 * the search parameters.
 */
public class ApplicationsRequestDto {
    private String searchType;
    private String searchName;
    private String searchCompetence;
    private String searchTime;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchCompetence() {
        return searchCompetence;
    }

    public void setSearchCompetence(String searchCompetence) {
        this.searchCompetence = searchCompetence;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

}
