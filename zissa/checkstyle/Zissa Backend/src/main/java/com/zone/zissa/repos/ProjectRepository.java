package com.zone.zissa.repos;

import com.zone.zissa.model.Project;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/** The ProjectRepository Interface for the Project database table. */
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Integer> {

  /**
   * The findByProjectName method.
   *
   * @param projectName the project name
   * @return Project
   */
  Optional<Project> findByProjectName(String projectName);
}
