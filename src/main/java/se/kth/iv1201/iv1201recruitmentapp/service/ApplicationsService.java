package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.model.Application;
import se.kth.iv1201.iv1201recruitmentapp.repository.ApplicationsRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for retrieving applications.
 */
@Service
public class ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    /**
     * TODO error handling?
     *
     * @param applicationsRequestDto
     * @return
     */
    public ApplicationsResponseDto getApplicationsSearchResults(ApplicationsRequestDto applicationsRequestDto) {
        String searchType = applicationsRequestDto.getSearchType();
        String searchText = applicationsRequestDto.getSearchText();

        List<Application> results;

        // TODO make sure this works with a multiple choice dropdown
        switch (searchType) {
            case "time" -> {
                // TODO fix after changing view
                String[] dates = searchText.split(" ");
                LocalDate from_date = LocalDate.parse(dates[0]);
                LocalDate to_date = LocalDate.parse(dates[1]);
                results = applicationsRepository.findAllByTime(from_date, to_date);
            }
            case "competence" -> {
                results = applicationsRepository.findAllByCompetence(searchText);
            }
            case "name" -> {
                String[] names = searchText.split(" ");
                results = applicationsRepository.findAllByName(names[0], names[1]);
            }
            default -> {
                results = null;
            }
        }

        ApplicationsResponseDto response = new ApplicationsResponseDto();
        response.setApplications(results);

        return response;
    }

}
