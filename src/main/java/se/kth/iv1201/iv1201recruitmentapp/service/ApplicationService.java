package se.kth.iv1201.iv1201recruitmentapp.service;

import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.model.*;
import se.kth.iv1201.iv1201recruitmentapp.repository.ApplicationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.AvailabilityRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceLocalizationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceProfileRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Service for retrieving application information.
 */
@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepository;

    @Autowired
    private CompetenceLocalizationRepository competenceLocalizationRepository;

    /**
     * Employs the corresponding database accesses to
     * create and return an application response dto
     * according to the given application id.
     *
     * @param applicationId The application id.
     * @return The application response dto.
     */
    public ApplicationResponseDto getApplicationData(int applicationId, Locale locale) {
        ApplicationResponseDto response = new ApplicationResponseDto();

        Application application = applicationRepository.getById(applicationId);
        List<Availability> availabilities = availabilityRepository.getAvailabilityByApplicationId(applicationId);
        List<CompetenceProfile> competenceProfiles = competenceProfileRepository.getCompetenceProfileById(applicationId);

        List<CompetenceProfileWrapper> competenceProfileWrappers = new LinkedList<CompetenceProfileWrapper>();

        for (CompetenceProfile competenceProfile : competenceProfiles) {
            CompetenceProfileWrapper competenceProfileWrapper = new CompetenceProfileWrapper();

            int id = competenceProfile.getCompetence().getId();
            String loc = locale.getLanguage();

            // TODO no isPresent for optional, shouldn't be a problem since we know there will be an id and a localization?
            competenceProfileWrapper.setCompetenceName(competenceLocalizationRepository.findByCompetenceIdAndLocale(id, loc).get().getCompetenceName());
            competenceProfileWrapper.setCompetenceProfile(competenceProfile);

            competenceProfileWrappers.add(competenceProfileWrapper);

            /*
            //System.out.println(applicationId + " >>>" + c.getId() + ", " + c.getPerson().getId() + ", " + c.getCompetence() + ", " + c.getYearsOfExperience() + "\nc: " + competenceLocalizationRepository.findByCompetenceIdAndLocale(c.getId(), locale.getLanguage()));
            int id = c.getCompetence().getId();
            String l = locale.getLanguage();
            System.out.println(">>>" + id + ", " + l);
            Optional<CompetenceLocalization> cl = competenceLocalizationRepository.findByCompetenceIdAndLocale(id, l);
            System.out.println("|||" + cl.get().getCompetenceName())*/
        }

        response.setApplication(application);
        response.setAvailabilities(availabilities);
        response.setCompetenceProfileWrappers(competenceProfileWrappers);

        return response;
    }

}
