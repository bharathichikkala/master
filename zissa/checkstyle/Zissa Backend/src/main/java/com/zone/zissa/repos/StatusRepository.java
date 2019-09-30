package com.zone.zissa.repos;

import com.zone.zissa.model.Status;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/** The StatusRepository Interface for the Status database table. */
@Transactional
public interface StatusRepository extends JpaRepository<Status, Byte> {

  /**
   * The findBystatusID method.
   *
   * @param statusId the status id
   * @return Status
   */
  Optional<Status> findBystatusID(Byte statusId);

  /**
   * The findAll method.
   *
   * @return Status
   */
  @Cacheable(value = "Status")
  List<Status> findAll();

  /**
   * the save method.
   *
   * @return Status
   */
  @CacheEvict(value = "Status", allEntries = true)
  Status save(Status entity);
}
