package com.mbb.mbbplatform.repos;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbb.mbbplatform.domain.DemoProducts;

public interface DemoProductsRepository extends JpaRepository<DemoProducts,Long>{
	@Query(value = "SELECT * FROM mbbinventory.demoproducts ORDER BY ID DESC limit 1 ", nativeQuery = true)
	DemoProducts findBylastRecord();
	
	DemoProducts findByDemoId(String invoiceId);

	DemoProducts findByQrCode(String barcode);
	@Query(value = "SELECT * FROM mbbinventory.demoproducts WHERE created_time >= ?1 AND created_time <= ?2  ", nativeQuery = true)

	List<DemoProducts> findDemoProductsByStartDateAndEndDate(@NotNull String startDate, String endDate1);
	@Query(value = "SELECT * FROM mbbinventory.demoproducts WHERE created_time >= ?1 AND created_time <= ?2 And dispatcstatus_id=?3 ", nativeQuery = true)

	List<DemoProducts> findDemoProductsByStartDateAndEndDateAndStatus(@NotNull String startDate, String endDate1,
			Long status);

	DemoProducts findByUnicommerceReferenceNumber(String unicommerceReferenceNumber);
	@Query(value = "SELECT * FROM mbbinventory.demoproducts WHERE demo_id = ?1 AND  qr_code=?2 And dispatcstatus_id=2  order by id desc LIMIT 1", nativeQuery = true)

	DemoProducts findByDemoIdAndStatus(String invoiceId, String barcode);

}
