package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.iv1201.iv1201recruitmentapp.model.Competence;

/**
 * Defines database access for competences.
 */
public interface CompetenceRepository extends JpaRepository<Competence, Integer> {}
