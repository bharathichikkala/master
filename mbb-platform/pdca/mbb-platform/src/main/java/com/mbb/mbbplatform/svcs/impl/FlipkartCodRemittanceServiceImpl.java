package com.mbb.mbbplatform.svcs.impl;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.mbb.mbbplatform.common.EnumTypeForErrorCodes;
import com.mbb.mbbplatform.domain.FlipkartCodDump;
import com.mbb.mbbplatform.domain.FlipkartCodRemittance;
import com.mbb.mbbplatform.domain.FlipkartTaxDetails;
import com.mbb.mbbplatform.model.ServiceResponse;
import com.mbb.mbbplatform.model.Utils;
import com.mbb.mbbplatform.repos.FlipkartCodDumpRepository;
import com.mbb.mbbplatform.repos.FlipkartCodRemittanceRepository;
import com.mbb.mbbplatform.repos.FlipkartTaxDetailsRepository;
import com.mbb.mbbplatform.svcs.FlipkartCodRemittanceService;

@Service
public class FlipkartCodRemittanceServiceImpl implements FlipkartCodRemittanceService {
	private static Logger log = LoggerFactory.getLogger(FlipkartCodRemittanceServiceImpl.class);

	@Autowired
	private FlipkartTaxDetailsRepository flipkartTaxDetailsRepository;
	@Autowired
	private FlipkartCodRemittanceRepository flipkartCodRemittanceRepo;
	@Autowired
	private FlipkartCodDumpRepository flipkartCodDumpRemittanceRepo;
	private static final String CGSTAMOUNT = "cgstAmount";

	private static final String FEENAME = "feeName";

	private static final String IGSTAMOUNT = "igstAmount";

	private static final String SGSTAMOUNT = "sgstAmount";

	private static final String FEEAMOUNT = "feeAmount";

	private static final String TOTALTAXES = "totalTaxes";
	@Autowired
	private Utils utils;

	@Scheduled(cron = "${flipkartcodremittance.getflipkartcod}")
	@Override
	public ServiceResponse<Collection<FlipkartCodRemittance>> getFlipkartCodRemittance() {
		log.info("get Flipkart Cod Remittance");

		ServiceResponse<Collection<FlipkartCodRemittance>> response = new ServiceResponse<>();

		Collection<FlipkartCodRemittance> flipkartCodRemittanceList = new ArrayList<>();
		try {
			LocalDateTime dateTime = LocalDateTime.now();
			Collection<FlipkartCodDump> flipkartPostpaidCodRemittanceList = flipkartCodDumpRemittanceRepo
					.findByNeftType();
			for (FlipkartCodDump postpaidOrdersList : flipkartPostpaidCodRemittanceList) {
				FlipkartCodRemittance flipkartAmazonCodRemittance = new FlipkartCodRemittance();

				FlipkartCodRemittance order = flipkartCodRemittanceRepo
						.findByOrderItemId(postpaidOrdersList.getOrderItemId());

				if (order == null) {
					flipkartAmazonCodRemittance.setNeftId(postpaidOrdersList.getNeftId());
					flipkartAmazonCodRemittance.setSettlementDate(postpaidOrdersList.getSettlementDate());
					flipkartAmazonCodRemittance.setOrderDate(postpaidOrdersList.getOrderDate());
					flipkartAmazonCodRemittance.setTier(postpaidOrdersList.getTier());
					flipkartAmazonCodRemittance.setAdditionalInformation(postpaidOrdersList.getAdditionalInformation());
					flipkartAmazonCodRemittance.setBuyerInvoiceAmount(postpaidOrdersList.getBuyerInvoiceAmount());
					flipkartAmazonCodRemittance.setBuyerInvoiceId(postpaidOrdersList.getBuyerInvoiceId());
					flipkartAmazonCodRemittance.setBuyerInvoiceDate(postpaidOrdersList.getBuyerInvoiceDate());
					flipkartAmazonCodRemittance.setChargableWeightSlab(postpaidOrdersList.getChargableWeightSlab());
					flipkartAmazonCodRemittance.setChargableWeightType(postpaidOrdersList.getChargableWeightType());
					flipkartAmazonCodRemittance
							.setCustomerShippingAmount(postpaidOrdersList.getCustomerShippingAmount());
					flipkartAmazonCodRemittance.setCustomerShippingFee(postpaidOrdersList.getCustomerShippingFee());
					flipkartAmazonCodRemittance.setFeeDiscount(postpaidOrdersList.getFeeDiscount());
					flipkartAmazonCodRemittance.setFixedFee(postpaidOrdersList.getFixedFee());
					flipkartAmazonCodRemittance.setFixedFeeWaiver(postpaidOrdersList.getCommissionFeeWaiver());
					flipkartAmazonCodRemittance.setFranciseFee(postpaidOrdersList.getFranciseFee());
					flipkartAmazonCodRemittance
							.setInsatllationAndPackagingFee(postpaidOrdersList.getInsatllationAndPackagingFee());
					flipkartAmazonCodRemittance.setInstallationFee(postpaidOrdersList.getInstallationFee());
					flipkartAmazonCodRemittance.setLengthBreadthHeight(postpaidOrdersList.getLengthBreadthHeight());
					flipkartAmazonCodRemittance.setMarketPlaceFee(postpaidOrdersList.getMarketPlaceFee());
					flipkartAmazonCodRemittance.setMultipartShipment(postpaidOrdersList.getMultipartShipment());
					flipkartAmazonCodRemittance.setMyShare(postpaidOrdersList.getMyShare());
					flipkartAmazonCodRemittance.setNeftType(postpaidOrdersList.getNeftType());
					flipkartAmazonCodRemittance
							.setProductCancalletionFee(postpaidOrdersList.getProductCancalletionFee());
					flipkartAmazonCodRemittance
							.setNoCostFeeReiumbersment(postpaidOrdersList.getNoCostFeeReiumbersment());
					flipkartAmazonCodRemittance.setOrderItemSaleAmount(postpaidOrdersList.getOrderItemSaleAmount());
					flipkartAmazonCodRemittance.setOrderItemMyShare(postpaidOrdersList.getOrderItemMyShare());
					flipkartAmazonCodRemittance.setOrderItemTotalOffer(postpaidOrdersList.getOrderItemTotalOffer());
					flipkartAmazonCodRemittance.setPickAndPackFee(postpaidOrdersList.getPickAndPackFee());
					flipkartAmazonCodRemittance.setPickAndPackFeeWaiver(postpaidOrdersList.getPickAndPackFeeWaiver());
					flipkartAmazonCodRemittance.setProfilerDeadWeight(postpaidOrdersList.getProfilerDeadWeight());
					flipkartAmazonCodRemittance.setProtectionFund(postpaidOrdersList.getProtectionFund());
					flipkartAmazonCodRemittance.setReverseShippingFee(postpaidOrdersList.getReverseShippingFee());
					flipkartAmazonCodRemittance.setSaleAmount(postpaidOrdersList.getSaleAmount());
					flipkartAmazonCodRemittance.setSellerDeadWeight(postpaidOrdersList.getSellerDeadWeight());
					flipkartAmazonCodRemittance
							.setServiceCancalletionFee(postpaidOrdersList.getServiceCancalletionFee());
					flipkartAmazonCodRemittance.setShippingFeeWaiver(postpaidOrdersList.getShippingFeeWaiver());
					flipkartAmazonCodRemittance.setShippingZone(postpaidOrdersList.getShippingZone());
					flipkartAmazonCodRemittance.setTaxCollectedAtSource(postpaidOrdersList.getTaxCollectedAtSource());
					flipkartAmazonCodRemittance.setTaxes(postpaidOrdersList.getTaxes());

					Double taxes = postpaidOrdersList.getTaxes();
					Double taxCollectedAtSource = postpaidOrdersList.getTaxCollectedAtSource();
					Double totalTaxes = taxes + taxCollectedAtSource;
					DecimalFormat f = new DecimalFormat("##.00");
					flipkartAmazonCodRemittance.setTotalTaxes(Double.parseDouble(f.format(totalTaxes)));

					Double marketPlaceFee = postpaidOrdersList.getMarketPlaceFee();
					DecimalFormat decimal = new DecimalFormat("##.00");
					Double marketPlaceFeeAndTaxes = taxes + marketPlaceFee;
					flipkartAmazonCodRemittance
							.setMarketPlaceFeeAndTaxes(Double.parseDouble(decimal.format(marketPlaceFeeAndTaxes)));

					flipkartAmazonCodRemittance.setTechVisitFee(postpaidOrdersList.getTechVisitFee());
					flipkartAmazonCodRemittance.setTotalOfferAmount(postpaidOrdersList.getTotalOfferAmount());
					flipkartAmazonCodRemittance.setUnInstallationFee(postpaidOrdersList.getUnInstallationFee());
					flipkartAmazonCodRemittance.setVolumetricWeight(postpaidOrdersList.getVolumetricWeight());
					flipkartAmazonCodRemittance.setCollectionFee(postpaidOrdersList.getCollectionFee());
					flipkartAmazonCodRemittance.setCollectionFeeWaiver(postpaidOrdersList.getCollectionFeeWaiver());
					flipkartAmazonCodRemittance.setCommission(postpaidOrdersList.getCommission());
					flipkartAmazonCodRemittance.setCommissionFeeWaiver(postpaidOrdersList.getCommissionFeeWaiver());
					flipkartAmazonCodRemittance.setCommissionRate(postpaidOrdersList.getCommissionRate());
					flipkartAmazonCodRemittance.setShippingCharges(postpaidOrdersList.getShippingFee());
					flipkartAmazonCodRemittance.setFulfillmentType(postpaidOrdersList.getFulfillmentType());
					flipkartAmazonCodRemittance.setSellerSku(postpaidOrdersList.getSellerSku());
					flipkartAmazonCodRemittance.setQuantity(postpaidOrdersList.getQuantity());
					flipkartAmazonCodRemittance.setProductName(postpaidOrdersList.getProductName());
					flipkartAmazonCodRemittance.setOrderId(postpaidOrdersList.getOrderId());
					flipkartAmazonCodRemittance.setSettledAmount(postpaidOrdersList.getSum());
					flipkartAmazonCodRemittance.setOrderItemId(postpaidOrdersList.getOrderItemId());
					flipkartAmazonCodRemittance.setChannel("FLIPKART");
					if (postpaidOrdersList.getDispatchDate().equals("")) {
						flipkartAmazonCodRemittance.setDispatchDate("NA");
					} else {
						flipkartAmazonCodRemittance.setDispatchDate(postpaidOrdersList.getDispatchDate());
					}
					flipkartAmazonCodRemittance.setQuantity(postpaidOrdersList.getQuantity());
					if (postpaidOrdersList.getReturnType().equals("NA")) {
						flipkartAmazonCodRemittance.setReturnType("ORDER");
						flipkartAmazonCodRemittance.setType("NA");
					} else if (postpaidOrdersList.getReturnType().equals("Customer Return")) {
						flipkartAmazonCodRemittance.setReturnType("CUSTOMER RETURN");
						flipkartAmazonCodRemittance.setType("Refund");
					} else if (postpaidOrdersList.getReturnType().equals("Courier Return")) {
						flipkartAmazonCodRemittance.setReturnType("COURIER RETURN");
						flipkartAmazonCodRemittance.setType("Refund");
					} else {
						flipkartAmazonCodRemittance.setReturnType(postpaidOrdersList.getReturnType());
					}
					if (postpaidOrdersList.getItemReturnStatus().equals("")) {
						flipkartAmazonCodRemittance.setItemReturnStatus("NA");

					} else {
						flipkartAmazonCodRemittance.setItemReturnStatus(postpaidOrdersList.getItemReturnStatus());
					}
					flipkartAmazonCodRemittance.setRefund(postpaidOrdersList.getRefund());
					flipkartAmazonCodRemittance.setCreatedDate(dateTime);
					flipkartAmazonCodRemittance.setUpdatedDate(dateTime);
					flipkartCodRemittanceList.add(flipkartAmazonCodRemittance);

				} else {

					flipkartCodRemittanceList.add(order);
					flipkartCodRemittanceRepo.saveAll(flipkartCodRemittanceList);
					response.setData(flipkartCodRemittanceList);

				}

				flipkartCodRemittanceRepo.saveAll(flipkartCodRemittanceList);
				response.setData(flipkartCodRemittanceList);

			}
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS240.name(), EnumTypeForErrorCodes.SCUS240.errorMsg());

			log.error(utils.toJson(response.getError()), e);

		}
		return response;

	}

	@Override
	public ServiceResponse<Collection<FlipkartCodRemittance>> getAllFlipkartCodRemittance() {
		log.info("get All Amazon Flipkart Cod Remittance");

		ServiceResponse<Collection<FlipkartCodRemittance>> response = new ServiceResponse<>();

		try {
			Collection<FlipkartCodRemittance> flipkartCodRemittance = flipkartCodRemittanceRepo.findAll();
			response.setData(flipkartCodRemittance);

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS245.name(), EnumTypeForErrorCodes.SCUS245.errorMsg());

			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<Collection<FlipkartCodRemittance>> findFlipkartReportInBetweenDates(
			@NotNull @RequestParam String startDate, @NotNull @RequestParam String endDate,
			@NotNull @RequestParam String returnType) {
		log.info("find cod reports in between the dates");
		ServiceResponse<Collection<FlipkartCodRemittance>> response = new ServiceResponse<>();
		try {
			if (returnType.equals("all")) {
				Collection<FlipkartCodRemittance> betweenDates = flipkartCodRemittanceRepo.getBetweenDates(startDate,
						endDate);

				response.setData(betweenDates);
			} else {
				Collection<FlipkartCodRemittance> list = flipkartCodRemittanceRepo
						.getBetweenDatesAndReturnType(startDate, endDate, returnType);
				response.setData(list);

			}
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS247.name(), EnumTypeForErrorCodes.SCUS247.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}
	
	@Override
	public ServiceResponse<JSONObject> getFlipkartCsv(@NotNull @RequestParam String startDate, @NotNull @RequestParam String endDate,
			@NotNull @RequestParam String returnType) {
		log.info("getting flipkart csv");
		ServiceResponse<JSONObject> response = new ServiceResponse<>();

		try {
			List<JSONObject> array2 = new ArrayList<>();
			JSONObject array = new JSONObject();
			List<FlipkartCodRemittance> flipkartCodRemittances =null;
			if (returnType.equals("all")) {
				flipkartCodRemittances= flipkartCodRemittanceRepo.findBetweenEndDateAndStartDate(startDate,
					endDate);
			}else {
				flipkartCodRemittances=flipkartCodRemittanceRepo
				.findBetweenEndDateAndStartDateAndReturnType(startDate, endDate, returnType);
			}
			for (FlipkartCodRemittance flipkartCodRemittance : flipkartCodRemittances) {
				if (flipkartCodRemittance != null) {

					String feeName = "Fixed Fee,Commission,Shipping Fee,Collection Fee,Reverse Shipping Fee";
					String[] feeNameList = feeName.split(",", 5);

					for (String feeType : feeNameList) {
						JSONObject obj2 = new JSONObject();
						obj2.put("neftId", flipkartCodRemittance.getNeftId());
						obj2.put("settlementDate", flipkartCodRemittance.getSettlementDate());
						obj2.put("settledAmount", flipkartCodRemittance.getSettledAmount());
						obj2.put("orderItemId", flipkartCodRemittance.getOrderItemId());
						obj2.put("saleAmount", flipkartCodRemittance.getSaleAmount());
						obj2.put("returnType", flipkartCodRemittance.getReturnType());
						obj2.put("taxCollectedAtSource", flipkartCodRemittance.getTaxCollectedAtSource());
						obj2.put("MarketPlaceFee", flipkartCodRemittance.getMarketPlaceFee());
						obj2.put("shippingCharges", flipkartCodRemittance.getShippingCharges());
						obj2.put("MarketPlaceFeeAndTaxes", flipkartCodRemittance.getMarketPlaceFeeAndTaxes());

						List<FlipkartTaxDetails> flipkartTax = flipkartTaxDetailsRepository
								.findByOrderItemIdAndFeeName(flipkartCodRemittance.getOrderItemId(), feeType);
						if (flipkartCodRemittance.getReturnType().equals("COURIER RETURN")) {

							if (!flipkartTax.isEmpty()) {

								FlipkartTaxDetails taxdetails = flipkartTax.get(0);
								obj2.put(CGSTAMOUNT, taxdetails.getCgstAmount());
								obj2.put(FEENAME, feeType);
								obj2.put(IGSTAMOUNT, taxdetails.getIgstAmount());
								obj2.put(SGSTAMOUNT, taxdetails.getSgstAmount());
								obj2.put(FEEAMOUNT, taxdetails.getFeeAmount());
								obj2.put(TOTALTAXES, taxdetails.getTotalTaxes());
								array2.add(obj2);

							}else {
								if(feeType.equals("Fixed Fee"))
								{
								obj2.put(CGSTAMOUNT, 0);
								obj2.put(FEENAME, 0);
								obj2.put(IGSTAMOUNT, 0);
								obj2.put(SGSTAMOUNT,0);
								obj2.put(FEEAMOUNT, 0);
								obj2.put(TOTALTAXES, 0);
								array2.add(obj2);
								}

							}
						} else {
							if (!flipkartTax.isEmpty()) {

							FlipkartTaxDetails taxdetails = flipkartTax.get(0);
							obj2.put(CGSTAMOUNT, taxdetails.getCgstAmount());
							obj2.put(FEENAME, feeType);
							obj2.put(IGSTAMOUNT, taxdetails.getIgstAmount());
							obj2.put(SGSTAMOUNT, taxdetails.getSgstAmount());
							obj2.put(FEEAMOUNT, taxdetails.getFeeAmount());
							obj2.put(TOTALTAXES, taxdetails.getTotalTaxes());
							array2.add(obj2);

						}}

					}

				}
				array.put("flip", array2);

			}

			response.setData(array);

		} catch (Exception e) {
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

}
