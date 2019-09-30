package com.mbb.mbbplatform.svcs;

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
import com.mbb.mbbplatform.domain.DemoProducts;
import com.mbb.mbbplatform.domain.DispatchStatus;
import com.mbb.mbbplatform.domain.PaymentModes;
import com.mbb.mbbplatform.model.ServiceResponse;

@RequestMapping("/mbb/demo")

public interface DemoProductsService {


	@GetMapping(RestApiUrlConstants.GET_ALL_DEMO_PRODUCTS)
	@ResponseBody
	ServiceResponse<List<DemoProducts>> getAllDemoProducts();
	
	@PostMapping(RestApiUrlConstants.ADD_DEMO_PRODUCT)
	@ResponseBody
	ServiceResponse<DemoProducts> addDemoProduct(@Valid @RequestBody DemoProducts demoProducts);
	
	@GetMapping(RestApiUrlConstants.GET_ALL_PRODUCTS_TO_BE_DISPATCHED)
	@ResponseBody
	ServiceResponse<List<String>> getAllProdutcsToBeDispatched();
	@PutMapping(RestApiUrlConstants.UPDATE_DEMO_PRODUCT)
	@ResponseBody
	ServiceResponse<DemoProducts> updateDemoProduct(@Valid @RequestBody DemoProducts demoProducts, @Valid @PathVariable Long id);
	
	@GetMapping(RestApiUrlConstants.ADD_RETURNED_DEMO_PRODUCT)
	@ResponseBody
	ServiceResponse<String> addReturnDemoProduct(@PathVariable String demoId);
	
	@PostMapping(RestApiUrlConstants.CONVERT_DEMO_PRODUCT_TO_ORDER)
	@ResponseBody
	ServiceResponse<DemoProducts> convertDemoProductToOrder(@RequestBody String invoice, @PathVariable String demoId);
	@GetMapping(RestApiUrlConstants.GET_DEMO_PRODUCTS_BY_ID)
	@ResponseBody
	ServiceResponse<DemoProducts> getById(@PathVariable Long id);
	@GetMapping(RestApiUrlConstants.GET_DEMO_PRODUCTS_BY_DEMOID)
	@ResponseBody
	ServiceResponse<JSONObject> getByDemoId(@PathVariable String demoId);
	@GetMapping(RestApiUrlConstants.GET_ALL_DEMO_PRODUCTS_DETAILS)
	@ResponseBody
	ServiceResponse<List<JSONObject>> getAllDemoProductsDetails(@NotNull @PathVariable String startDate,@PathVariable String endDate,
			@PathVariable Long dispatcStatusId);
	
	@GetMapping(RestApiUrlConstants.GET_ALL_DISPATCH_STATUS_FOR_DEMO)
	@ResponseBody
	ServiceResponse<List<JSONObject>> getAllDispatchStatus();
	
	@GetMapping(RestApiUrlConstants.GET_ALL_DISPATCHED_PRODUCTS)
	@ResponseBody
	ServiceResponse<List<String>> getAllDispatchedProducts();
	@PostMapping(RestApiUrlConstants.BARCODES_FOR_DEMO)
	@ResponseBody
	ServiceResponse<String> barcodesCheckForRentals(@NotNull @PathVariable String demoId,@RequestBody List<String> barcodes);
	@GetMapping(RestApiUrlConstants.GET_ALL_PAYMENT_MODES)
	@ResponseBody
	ServiceResponse<List<PaymentModes>> getAllPaymentModes();
	
	@GetMapping(RestApiUrlConstants.GET_BY_UNICOMMERCE_REFERENCE_NUMBER)
	@ResponseBody
	ServiceResponse<String> getByUnicommerceReferenceNumber(@PathVariable String unicommerceReferenceNumber);
	@GetMapping("/getAllDemoProducts/{startDate}/{endDate}/{dispatcStatusId}/{paymentMode}")
	@ResponseBody
	ServiceResponse<List<JSONObject>> getAllDemoProductsDetailss(@NotNull @PathVariable String startDate, @PathVariable String endDate,
			@PathVariable Long dispatcStatusId,@PathVariable  Long paymentMode);
	
	@GetMapping(RestApiUrlConstants.GET_ALL_PRODUCT_NAMES)
	@ResponseBody
	ServiceResponse<List<String>> getAllProductNames();
	
	@PostMapping(RestApiUrlConstants.GET_SKUCODE_BY_PRODUCTNAME)
	@ResponseBody
	ServiceResponse<JSONObject> getSkuCodeByProductName(@RequestBody JSONObject getSkuCodeByProductName);
	
	@GetMapping(RestApiUrlConstants.GET_ALL_DEMO_RETURN_TYPES)
	@ResponseBody
	ServiceResponse<List<DispatchStatus>> getStatusForDemoReturns();
	
	@PostMapping(RestApiUrlConstants.ADD_COMMENTS_FOR_DEMO_REJECTION)
	@ResponseBody
	ServiceResponse<String> getCommentsForDemoRejection(@PathVariable String demoId, @RequestBody String rejectionComments);
	@GetMapping(RestApiUrlConstants.GET_ALL_DEMO_RETURN_DROP_DOWN)
	@ResponseBody
	ServiceResponse<String> getProductReturnDropdown(@PathVariable String invoiceNumber);
}
