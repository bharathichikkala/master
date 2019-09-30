package com.mbb.mbbplatform.repos;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbb.mbbplatform.domain.Dispatch;
import com.mbb.mbbplatform.domain.RentalProducts;

public interface RentalProductsRepository extends JpaRepository<RentalProducts, Long>{
	@Query(value = "SELECT * FROM mbbinventory.rentalproducts ORDER BY ID DESC limit 1 ", nativeQuery = true)
	RentalProducts findBylastRecord();

	List<RentalProducts> findByInvoiceNumber(String invoiceId);
	
	@Query(value = "SELECT * FROM mbbinventory.rentalproducts WHERE created_time >= ?1 AND created_time <= ?2 And dispatch_status_id=?3", nativeQuery = true)
	List<RentalProducts> findByDatesAndStatus(@NotNull String startDate, @NotNull String endDate,@NotNull Long statusId);
	
	@Query(value = "SELECT * FROM mbbinventory.rentalproducts WHERE created_time >= ?1 AND created_time <= ?2 ", nativeQuery = true)

	List<RentalProducts> findByDates(@NotNull String startDate, @NotNull String endDate);
	
	@Query(value = "SELECT * FROM mbbinventory.rentalproducts WHERE created_time >= ?1 AND created_time <= ?2 And rental_service_type=?3", nativeQuery = true)
	List<RentalProducts> findByDatesAndService(String startDate, String endDate1, Long serviceType);
	
	@Query(value = "SELECT * FROM mbbinventory.rentalproducts WHERE created_time >= ?1 AND created_time <= ?2 And dispatch_status_id=?3 AND rental_service_type=?4", nativeQuery = true)
	List<RentalProducts> findByDatesAndStatusAndService(String startDate, String endDate1, Long statusId,
			Long serviceType);
	@Query(value = "SELECT * FROM mbbinventory.rentalproducts WHERE invoice_number = ?1 AND  bar_code=?2 And dispatch_status_id=2  order by id desc LIMIT 1", nativeQuery = true)

	RentalProducts findByInvoiceNumberAndStatus(String invoiceId,String barcode);

	RentalProducts findByDispatchId(Dispatch dispatch);
}
