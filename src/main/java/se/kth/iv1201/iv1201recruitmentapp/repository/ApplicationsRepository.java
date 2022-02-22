package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.kth.iv1201.iv1201recruitmentapp.model.Application;

import java.time.LocalDate;
import java.util.List;

/**
 * Defines database access for the applications.
 */
public interface ApplicationsRepository extends JpaRepository<Application, Integer> {

    /**
     * Retrieves list of application objects by
     * availability from date and to date.
     *
     * @param fromDate The from date.
     * @param toDate The to date.
     * @return The list of application objects
     */
    @Query(value = "SELECT app.application_id, app.person_id, app.season_id, app.status FROM application AS app INNER JOIN availability AS avl ON app.person_id = avl.person_id WHERE avl.from_date <= ?1 AND avl.to_date >= ?2",
            nativeQuery = true)
    List<Application> findAllByTime(LocalDate fromDate, LocalDate toDate);

    /**
     * TODO search by competence.name ok?
     * Retrieves list of application objects by
     * competence name.
     *
     * @param competence The competence name.
     * @return The list of application objects.
     */
    @Query(value = "SELECT a.application_id, a.person_id, a.season_id, a.status FROM application AS a INNER JOIN competence_profile AS cp ON a.person_id = cp.person_id INNER JOIN competence as co ON cp.competence_id = co.competence_id WHERE co.name = ?1",
            nativeQuery = true)
    List<Application> findAllByCompetence(String competence);

    /**
     * TODO search by person.name and person.surname ok?
     * Retrieves list of application objects by
     * person first name and surname combined as name.
     *
     * @param name The first name and surname.
     * @return The list of application objects.
     */
    @Query(value = "SELECT a.application_id, a.person_id, a.season_id, a.status FROM application AS a INNER JOIN person AS p ON a.person_id = p.person_id WHERE p.name = ?1 AND p.surname = ?2",
            nativeQuery = true)
    List<Application> findAllByName(String name);

}
