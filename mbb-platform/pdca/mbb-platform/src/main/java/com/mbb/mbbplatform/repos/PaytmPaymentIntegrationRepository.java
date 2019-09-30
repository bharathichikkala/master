package com.mbb.mbbplatform.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbb.mbbplatform.domain.CustomerDetails;
import com.mbb.mbbplatform.domain.PaytmPaymentIntegration;

public interface PaytmPaymentIntegrationRepository extends JpaRepository<PaytmPaymentIntegration, Long>{

	PaytmPaymentIntegration findByCustomerDetailsId(CustomerDetails customerDetailsId);

}
