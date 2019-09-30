package com.mbb.mbbplatform.svcs.impl;

import java.util.Collection;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mbb.mbbplatform.common.EnumTypeForErrorCodes;
import com.mbb.mbbplatform.domain.Dispatch;
import com.mbb.mbbplatform.domain.RefundDetails;
import com.mbb.mbbplatform.domain.ReturnDetails;
import com.mbb.mbbplatform.domain.User;
import com.mbb.mbbplatform.model.ServiceResponse;
import com.mbb.mbbplatform.model.Utils;
import com.mbb.mbbplatform.repos.DispatchRepository;
import com.mbb.mbbplatform.repos.RefundDetailsRepository;
import com.mbb.mbbplatform.repos.ReturnDetailsRepository;
import com.mbb.mbbplatform.svcs.EmailService;
import com.mbb.mbbplatform.svcs.RefundDetailsService;
import com.mbb.mbbplatform.svcs.UserService;

@RestController
public class RefundDetailsServiceImpl implements RefundDetailsService {

	private static Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
	@Autowired
	private Utils utils;

	@Autowired
	private EmailService emailsvc;

	@Autowired
	private UserService userSvc;

	@Autowired
	private DispatchRepository dispatchRepo;

	@Autowired
	private RefundDetailsRepository refundDetailsRepo;

	@Autowired
	private ReturnDetailsRepository returnDetailsRepo;

	@Override
	public ServiceResponse<RefundDetails> addRefundDetails(@Valid RefundDetails refundDetails) {
		log.info("adding return details");

		ServiceResponse<RefundDetails> response = new ServiceResponse<>();
		RefundDetails savedRefundDetails = null;
		try {
			RefundDetails existingRefundDetails = refundDetailsRepo.findByDispatchId(refundDetails.getDispatchId());
			ReturnDetails existingReturnDetails = returnDetailsRepo.findByDispatchId(refundDetails.getDispatchId());
			if (existingReturnDetails == null || !existingReturnDetails.getReturnStatus()) {
				response.setError(EnumTypeForErrorCodes.SCUS618.name(), EnumTypeForErrorCodes.SCUS618.errorMsg());

			} else {

				if (existingRefundDetails == null) {

					savedRefundDetails = refundDetailsRepo.save(refundDetails);

				} else {

					response.setError(EnumTypeForErrorCodes.SCUS610.name(), EnumTypeForErrorCodes.SCUS610.errorMsg());

				}
			}
			response.setData(savedRefundDetails);

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS611.name(), EnumTypeForErrorCodes.SCUS611.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

	@Override
	public ServiceResponse<RefundDetails> getByDispatchId(Long id) {
		log.info("get return details by dispatchId");
		ServiceResponse<RefundDetails> response = new ServiceResponse<>();
		try {
			Dispatch dispatch = dispatchRepo.findById(id).get();
			RefundDetails refundDetails = refundDetailsRepo.findByDispatchId(dispatch);
			if (refundDetails != null) {
				response.setData(refundDetails);

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS613.name(), EnumTypeForErrorCodes.SCUS613.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<RefundDetails> updateRefundDetails(@Valid RefundDetails refundDetails, Long dispatchId) {
		log.info("update refund details");
		ServiceResponse<RefundDetails> response = new ServiceResponse<>();
		try {
			Dispatch dispatch = dispatchRepo.findById(dispatchId).get();
			RefundDetails existingRefundDetails = refundDetailsRepo.findByDispatchId(dispatch);
			existingRefundDetails.setBankName(refundDetails.getBankName());
			existingRefundDetails.setComments(refundDetails.getComments());
			existingRefundDetails.setCourierCharges(refundDetails.getCourierCharges());
			existingRefundDetails.setDeductionAmount(refundDetails.getDeductionAmount());
			existingRefundDetails.setOthers(refundDetails.getOthers());
			existingRefundDetails.setPaymentGatewayCharges(refundDetails.getPaymentGatewayCharges());
			existingRefundDetails.setRefundedAmount(refundDetails.getRefundedAmount());
			existingRefundDetails.setRefundedDate(refundDetails.getRefundedDate());
			existingRefundDetails.setTransactionNumber(refundDetails.getTransactionNumber());
			existingRefundDetails.setRefundStatus(refundDetails.getRefundStatus());
			RefundDetails savedRecord = refundDetailsRepo.save(existingRefundDetails);
			response.setData(savedRecord);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS621.name(), EnumTypeForErrorCodes.SCUS621.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<String> sendRefundAmountMailForAccountant(Double amount, Long dispatchId) {

		log.info("sending Refund Amount Mail For Accountant");

		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			Dispatch dispatch = dispatchRepo.findById(dispatchId).get();
			RefundDetails refund = new RefundDetails();
			refund.setRefundedAmount(amount);
			refund.setDispatchId(dispatch);
			ServiceResponse<RefundDetails> result = addRefundDetails(refund);

			String body1 = "Hi Accounts Team,<br><br> Please proceed for the refund process of the below order<br><Strong>Order ID : " 
					+ dispatch.getInvoiceId() + "<br> Refund Amount : "+amount+"</strong>";
			String subject = "Refund Amount Mail Alert";

			ServiceResponse<Collection<User>> users = userSvc.getUsersByRole("ACCOUNTANT");
			Collection<User> usersData = users.getData();
			for (User user : usersData) {

				if (user.isNotificationStatus()) {
					String email = user.getEmail();

					
					if (result.getError() == null) {
						emailsvc.notifyUserByEmail(email, body1, subject);
						response.setData("mail sent successfully");
					} else {
						response.setError(result.getError());
					}
				}

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS620.name(), EnumTypeForErrorCodes.SCUS620.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

}
