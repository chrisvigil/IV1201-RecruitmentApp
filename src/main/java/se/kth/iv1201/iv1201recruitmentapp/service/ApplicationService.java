package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.model.Application;
import se.kth.iv1201.iv1201recruitmentapp.model.Availability;
import se.kth.iv1201.iv1201recruitmentapp.repository.ApplicationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.AvailabilityRepository;

import java.util.List;

/**
 * Service for retrieving application information.
 */
@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    /**
     * Employs the corresponding database accesses to
     * create and return an application response dto
     * according to the given application id.
     *
     * @param applicationId The application id.
     * @return The application response dto.
     */
    public ApplicationResponseDto getApplicationData(int applicationId) {
        ApplicationResponseDto response = new ApplicationResponseDto();

        Application application = applicationRepository.getById(applicationId);
        List<Availability> availabilities = availabilityRepository.getAvailabilityByApplicationId(applicationId);

        response.setApplication(application);
        response.setAvailabilities(availabilities);

        return response;
    }

}
