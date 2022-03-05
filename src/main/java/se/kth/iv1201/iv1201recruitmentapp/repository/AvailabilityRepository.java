package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.kth.iv1201.iv1201recruitmentapp.model.Availability;

import java.util.List;

/**
 * Defines database access for the availabilities.
 */
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {

    /**
     * Gets all the availabilities associated with
     * the specified application id.
     *
     * @param applicationId The application id.
     * @return The availabilities.
     */
    @Query(value = "select av.availability_id, av.person_id, av.from_date, av.to_date from application as app inner join season as s on app.season_id=s.season_id right outer join availability as av on app.person_id=av.person_id where app.application_id=?1 and av.from_date>s.season_start and av.to_date<s.season_end\n",
            nativeQuery = true)
    List<Availability> getAvailabilityByApplicationId(int applicationId);

}
