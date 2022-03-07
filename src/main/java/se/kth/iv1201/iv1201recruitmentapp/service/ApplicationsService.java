package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.exception.ApplicationsNameSearchFormatException;
import se.kth.iv1201.iv1201recruitmentapp.exception.ApplicationsTimeSearchFormatException;
import se.kth.iv1201.iv1201recruitmentapp.model.Application;
import se.kth.iv1201.iv1201recruitmentapp.model.Competence;
import se.kth.iv1201.iv1201recruitmentapp.model.CompetenceLocalization;
import se.kth.iv1201.iv1201recruitmentapp.repository.ApplicationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceLocalizationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Service for retrieving applications.
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class ApplicationsService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CompetenceLocalizationRepository competenceLocalizationRepository;

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private MessageSource messageSource;

    /**
     * Employs the corresponding database actions
     * to get all competences depending on the
     * currently selected locale, if the locale
     * is not found, a default set of competences
     * will be returned.
     *
     * @return The competences.
     */
    public List<Competence> getCompetences() {
        List<Competence> results = new LinkedList<Competence>();

        String lang = LocaleContextHolder.getLocale().getLanguage();
        List<CompetenceLocalization> competenceLocalizations = competenceLocalizationRepository.findAllByLocale(lang);

        if (competenceLocalizations.size() > 0) {
            for (CompetenceLocalization cl : competenceLocalizations) {
                Competence c = new Competence();
                c.setId(cl.getCompetence().getId());
                c.setName(cl.getCompetenceName());
                results.add(c);
            }
        }
        else {
            results = competenceRepository.findAll();
        }

        return results;
    }

    /**
     * Parses the applications request dto and employs
     * the corresponding database accesses to create and
     * return an applications response dto.
     *
     * @param applicationsRequestDto The applications request dto.
     * @return The applications response dto.
     */
    public ApplicationsResponseDto getApplicationsSearchResults(ApplicationsRequestDto applicationsRequestDto) {
        ApplicationsResponseDto response = new ApplicationsResponseDto();

        String searchType = applicationsRequestDto.getSearchType();
        String searchName = applicationsRequestDto.getSearchName();
        String searchCompetence = applicationsRequestDto.getSearchCompetence();
        String searchTime = applicationsRequestDto.getSearchTime();

        List<Application> results;

        // TODO in message.properties?
        Locale def = new Locale("en");

        if (searchType.equals(messageSource.getMessage("recruiter.applications.name", null, def))) {
            String[] names = searchName.split(" ");

            try {
                results = applicationRepository.findAllByName(names[0], names[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ApplicationsNameSearchFormatException("Invalid name format");
            }
        }
        else if (searchType.equals(messageSource.getMessage("recruiter.applications.competence", null, def))) {
            results = applicationRepository.findAllByCompetence(Integer.parseInt(searchCompetence));
        }
        else if (searchType.equals(messageSource.getMessage("recruiter.applications.time", null, def))) {
            String[] dates = searchTime.split(" ");

            try {
                LocalDate from_date = LocalDate.parse(dates[0]);
                LocalDate to_date = LocalDate.parse(dates[1]);

                results = applicationRepository.findAllByTime(from_date, to_date);
            } catch (DateTimeParseException e) {
                throw new ApplicationsTimeSearchFormatException("Invalid time format");
            }
        }
        else {
            results = null;
        }

        response.setApplications(results);

        return response;
    }

}
