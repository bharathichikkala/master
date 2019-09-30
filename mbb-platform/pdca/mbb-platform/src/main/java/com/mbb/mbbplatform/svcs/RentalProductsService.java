package com.mbb.mbbplatform.svcs;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mbb.mbbplatform.common.RestApiUrlConstants;
import com.mbb.mbbplatform.domain.CustomerDetails;
import com.mbb.mbbplatform.domain.DispatchStatus;
import com.mbb.mbbplatform.domain.History;
import com.mbb.mbbplatform.domain.RentalExtension;
import com.mbb.mbbplatform.domain.RentalProducts;
import com.mbb.mbbplatform.domain.RentalServiceTypes;
import com.mbb.mbbplatform.model.ServiceResponse;

@RequestMapping("mbb/rentals")
public interface RentalProductsService {
	@PostMapping(RestApiUrlConstants.ADD_RENTALS)
	@ResponseBody
	public ServiceResponse<RentalProducts> addRentals(@Valid @RequestBody RentalProducts rentalProducts);

	@GetMapping(RestApiUrlConstants.GET_RENTALS_BY_ID)
	@ResponseBody
	public ServiceResponse<RentalProducts> getById(@PathVariable Long id);

	@GetMapping(RestApiUrlConstants.GET_ALL_RENTAL_PRODUCTS)
	@ResponseBody
	public ServiceResponse<List<JSONObject>> getAllRentalProducts(@NotNull @PathVariable String startDate,
			@NotNull @PathVariable String endDate, @PathVariable Long statusId,@PathVariable Long facilityId,@PathVariable Long serviceType);

	@GetMapping(RestApiUrlConstants.GET_ALL_RENTAL_SERVICE_TYPES)
	@ResponseBody
	public ServiceResponse<List<RentalServiceTypes>> getAllRentalServiceTypes();

	@GetMapping(RestApiUrlConstants.GET_ALL_DISPATCH_STATUS)
	@ResponseBody
	public ServiceResponse<List<DispatchStatus>> getAllDispatchStatus();

	@PutMapping(RestApiUrlConstants.UPDATE_RENTALS)
	@ResponseBody
	public ServiceResponse<RentalProducts> updateRentals(@Valid @RequestBody RentalProducts rentalProducts,
			@PathVariable Long rentalId);

	@GetMapping(RestApiUrlConstants.GET_ALL_DISPATCH_STATUS_DROPDOWN)
	@ResponseBody
	public ServiceResponse<List<DispatchStatus>> getStatusForRentals();

	@PostMapping(RestApiUrlConstants.ADD_RENTAL_EXTENSION)
	@ResponseBody
	public ServiceResponse<RentalExtension> addRentalExtension(@Valid @RequestBody RentalExtension rentalExtension,
			@PathVariable Long rentalId);

	@GetMapping(RestApiUrlConstants.VIEW_EXTENSIONS)
	@ResponseBody
	public ServiceResponse<List<JSONObject>> viewExtesnions(@PathVariable Long rentalId);

	@GetMapping(RestApiUrlConstants.SEND_MAIL_ALERTS_FOR_RENTALS)
	@ResponseBody
	public ServiceResponse<String> sendMailAlertsForRentals();

	@GetMapping(RestApiUrlConstants.GET_RENTALS_FOR_DISPATCH)
	@ResponseBody
	public ServiceResponse<List<String>> getRentalsForDispatch();

	@GetMapping(RestApiUrlConstants.INVOICE_CHECK_FOR_ADD_RENTALS)
	@ResponseBody
	public ServiceResponse<String> invoiceCheck(@PathVariable String invoiceNumber);
	
	@GetMapping(RestApiUrlConstants.CONVERT_TO_ORDER)
	@ResponseBody
	public ServiceResponse<RentalProducts> convertToOrder(@PathVariable String invoiceNumber,@PathVariable Long rentalId);
	
	@GetMapping(RestApiUrlConstants.INVOICE_CHECK_FOR_DROP_DOWN)
	@ResponseBody
	public ServiceResponse<String> invoiceCheckForDropDown(@PathVariable String invoiceNumber);
	
	@GetMapping(RestApiUrlConstants.GET_RENTAL_INVENTORY)
	@ResponseBody
	ServiceResponse<List<JSONObject>> getRentalInventory(@PathVariable Long facilityId,
			@PathVariable Boolean status);
	@GetMapping(RestApiUrlConstants.GET_RENTAL_ITEMS_BASED_ON_SKUCODE_AND_DATES)
	@ResponseBody
	ServiceResponse<Collection<JSONObject>> getRentalInventoryItemsBasedOnSkuCodeAndDates(@PathVariable String startDate,
			@PathVariable String endDate, @PathVariable String skuCode, @PathVariable Long statusId,
			@PathVariable Long facilityId);
	@PostMapping(RestApiUrlConstants.ADD_RENTALS_WOOCOMERCE)
	@ResponseBody
	public ServiceResponse<RentalProducts> addRentalsFromWoocomerce(@Valid @RequestBody String rentalProducts);
	@PostMapping(RestApiUrlConstants.ADD_HISTORY)
	@ResponseBody
	public ServiceResponse<History> addHistory(@Valid @RequestBody String historyDetails);
	
	 @GetMapping("/getStatusForRentalReturns")
	 @ResponseBody
	 ServiceResponse<List<DispatchStatus>> getStatusForRentalReturns();

	 @GetMapping(RestApiUrlConstants.GET_ALL_HISTORY)
		@ResponseBody
		public ServiceResponse<List<History>> getAllHistory(@PathVariable String barcode);
	 
	 @PostMapping("/generatingPaymentLink/{customerDetailsId}")
		@ResponseBody
		public ServiceResponse<String> generatingPaymentLink(@Valid @RequestBody String paytmPaymentIntegration,@PathVariable CustomerDetails customerDetailsId);
}
