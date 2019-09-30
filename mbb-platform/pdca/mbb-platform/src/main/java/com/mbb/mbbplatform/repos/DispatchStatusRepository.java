package com.mbb.mbbplatform.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbb.mbbplatform.domain.DispatchStatus;

public interface DispatchStatusRepository extends JpaRepository<DispatchStatus, Long> {
	@Query(value = "SELECT * FROM mbbinventory.dispatcstatus WHERE  status in('ORDERED','REJECTED') ", nativeQuery = true)

	List<DispatchStatus> findAllDemoReturnStatus();
	@Query(value = "SELECT * FROM mbbinventory.dispatcstatus WHERE  status in('RENTAL EXPIRED','ORDERED','REPLACEMENT') ", nativeQuery = true)

	List<DispatchStatus> findAllRentalReturnStatus();

}
