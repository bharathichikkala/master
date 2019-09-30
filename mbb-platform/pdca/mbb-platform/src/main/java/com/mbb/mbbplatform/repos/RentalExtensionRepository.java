package com.mbb.mbbplatform.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbb.mbbplatform.domain.RentalExtension;
import com.mbb.mbbplatform.domain.RentalProducts;

public interface RentalExtensionRepository extends JpaRepository<RentalExtension, Long>{

	List<RentalExtension> findByRentalProductId(RentalProducts rentalProducts);

}
