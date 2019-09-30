package com.mbb.mbbplatform.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mbb.mbbplatform.domain.PaymentModes;

public interface PaymentModeRepository extends JpaRepository<PaymentModes, Long>{

@Query(value = "SELECT * FROM mbbinventory.paymentmodes WHERE types not in('COD','ONLINE','FREE_DEMO') ", nativeQuery = true)
List<PaymentModes> findAllPaymentModes();
@Query(value = "SELECT * FROM mbbinventory.paymentmodes WHERE types!='FREE_DEMO' ", nativeQuery = true)

List<PaymentModes> findAllByPaymentModes();
}
