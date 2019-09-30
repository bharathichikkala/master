
package com.mbb.mbbplatform.svcs.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mbb.mbbplatform.common.EnumTypeForErrorCodes;
import com.mbb.mbbplatform.domain.CustomerDetails;
import com.mbb.mbbplatform.domain.DemoProducts;
import com.mbb.mbbplatform.domain.Dispatch;
import com.mbb.mbbplatform.domain.DispatchStatus;
import com.mbb.mbbplatform.domain.Inventory;
import com.mbb.mbbplatform.domain.InventoryItem;
import com.mbb.mbbplatform.domain.PaymentModes;
import com.mbb.mbbplatform.domain.RentalProducts;
import com.mbb.mbbplatform.model.ServiceResponse;
import com.mbb.mbbplatform.model.Utils;
import com.mbb.mbbplatform.repos.CustomerDetailsRepository;
import com.mbb.mbbplatform.repos.DemoProductsRepository;
import com.mbb.mbbplatform.repos.DispatchRepository;
import com.mbb.mbbplatform.repos.DispatchStatusRepository;
import com.mbb.mbbplatform.repos.DispatchTypesRepository;
import com.mbb.mbbplatform.repos.InventoryItemRepository;
import com.mbb.mbbplatform.repos.InventoryRepository;
import com.mbb.mbbplatform.repos.PaymentModeRepository;
import com.mbb.mbbplatform.repos.RentalProductsRepository;
import com.mbb.mbbplatform.svcs.DemoProductsService;

@RestController

public class DemoProductsServiceImpl implements DemoProductsService {
	private static Logger log = LoggerFactory.getLogger(DemoProductsService.class);
	@Autowired
	private DispatchRepository dispatchRepo;
	@Autowired
	private Utils utils;
	@Autowired
	private RentalProductsRepository rentalProductsRepo;
	@Autowired
	private DispatchStatusRepository dispatchStatusRepository;
	@Autowired
	private DispatchTypesRepository dispatchTypesRepository;
	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;
	@Autowired
	private DemoProductsRepository demoProductsRepo;
	@Autowired
	private InventoryItemRepository inventoryItemRepo;
	@Autowired
	private InventoryRepository inventoryRepo;

	@Autowired
	private PaymentModeRepository paymentModeRepository;
	private static final String SUCCESS = "success";
	private static final String SKUCODE = "skuCode";
	private static final String QRCODE = "qrCode";

	private static final String PRODUCTNAME = "productName";

	/**
	 * get All Demo Products service implementation
	 * 
	 * @return ServiceResponse<List<DemoProducts>>
	 */
	@Override
	public ServiceResponse<List<DemoProducts>> getAllDemoProducts() {
		log.info("get All Demo Products");
		ServiceResponse<List<DemoProducts>> response = new ServiceResponse<>();
		try {
			List<DemoProducts> servicingProduct = demoProductsRepo.findAll();
			response.setData(servicingProduct);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3000.name(), EnumTypeForErrorCodes.SCUS3000.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}

		return response;
	}

	/**
	 * Add Demo Product service implementation
	 * 
	 * @RequestBody demoProducts
	 * @return ServiceResponse<DemoProducts>
	 */
	@Override
	public ServiceResponse<DemoProducts> addDemoProduct(DemoProducts demoProducts) {
		log.info("adding product for demo ");
		ServiceResponse<DemoProducts> response = new ServiceResponse<>();
		try {
			CustomerDetails customerDetails = customerDetailsRepository.save(demoProducts.getCustomerDetailsId());
			DemoProducts serialNumberForDemo = demoProductsRepo.findBylastRecord();
			if (serialNumberForDemo == null) {
				String num = "1";
				String demoId = "DM" + StringUtils.leftPad(num, 4, "0");

				demoProducts.setDemoId(demoId);
			} else {

				String str = serialNumberForDemo.getDemoId();
				Long demoId = Long.parseLong(str.substring(2));
				Long newDemoId = demoId + 1;
				String num = newDemoId.toString();
				String newDemoId1 = "DM" + StringUtils.leftPad(num, 4, "0");

				demoProducts.setDemoId(newDemoId1);
			}
			demoProducts.setDispatcstatusId(dispatchStatusRepository.getOne(1l));
			demoProducts.setCustomerDetailsId(customerDetails);
			DemoProducts demoProductsSave = demoProductsRepo.save(demoProducts);
			response.setData(demoProductsSave);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3001.name(), EnumTypeForErrorCodes.SCUS3001.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}
		return response;
	}

	/**
	 * get All Demo Products To Be Dispatched service implementation
	 * 
	 * @return ServiceResponse<List<JSONObject>>
	 */
	@Override
	public ServiceResponse<List<String>> getAllProdutcsToBeDispatched() {
		log.info("get All Produtcs To Be Dispatched");
		ServiceResponse<List<String>> response = new ServiceResponse<>();
		List<String> listOfDemoIds = new ArrayList<>();

		try {
			List<DemoProducts> demoProducts = demoProductsRepo.findAll();
			for (DemoProducts demoProduct : demoProducts) {
				if (demoProduct.getDispatcstatusId().getId() == 1) {
					listOfDemoIds.add(demoProduct.getDemoId());
				}
			}
			response.setData(listOfDemoIds);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3000.name(), EnumTypeForErrorCodes.SCUS3000.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}

		return response;
	}

	@Override
	public ServiceResponse<List<String>> getAllDispatchedProducts() {
		log.info("get All  Dispatched products");
		ServiceResponse<List<String>> response = new ServiceResponse<>();
		List<String> listOfDemoIds = new ArrayList<>();
		try {
			List<DemoProducts> demoProducts = demoProductsRepo.findAll();
			for (DemoProducts demoProduct : demoProducts) {
				if (demoProduct.getDispatcstatusId().getId() == 2) {
					listOfDemoIds.add(demoProduct.getDemoId());
				}
			}
			response.setData(listOfDemoIds);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3000.name(), EnumTypeForErrorCodes.SCUS3000.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}

		return response;
	}

	@Override
	public ServiceResponse<String> getProductReturnDropdown(String invoiceNumber) {
		log.info("get All  Dispatched products");

		List<String> list = getAllDispatchedProducts().getData();
		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			if (list.contains(invoiceNumber)) {
				response.setData(SUCCESS);
			} else {
				response.setError(EnumTypeForErrorCodes.SCUS619.name(), EnumTypeForErrorCodes.SCUS619.errorMsg());

			}
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS3000.name(), EnumTypeForErrorCodes.SCUS3000.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	/**
	 * Update Demo Product service implementation
	 * 
	 * @param id
	 * @RequestBody demoProducts
	 * @return ServiceResponse<DemoProducts>
	 */

	@Override
	public ServiceResponse<DemoProducts> updateDemoProduct(@Valid DemoProducts demoProducts, @Valid Long id) {
		log.info("updating  demo product");
		ServiceResponse<DemoProducts> response = new ServiceResponse<>();
		try {
			Optional<DemoProducts> demoProductExist = demoProductsRepo.findById(id);
			if (demoProductExist.isPresent()) {
				demoProductExist.get().setComments(demoProducts.getComments());
				demoProductExist.get().setDemoPrice(demoProducts.getDemoPrice());
				demoProductExist.get().setOrderDate(demoProducts.getOrderDate());
				demoProductExist.get().setProductName(demoProducts.getProductName());
				demoProductExist.get().setSkuCode(demoProducts.getSkuCode());

				CustomerDetails customerDetailsupdated = demoProducts.getCustomerDetailsId();
				CustomerDetails customerDetails = customerDetailsRepository
						.findById(demoProducts.getCustomerDetailsId().getId()).get();
				if (customerDetails != null) {
					customerDetailsupdated.setId(customerDetails.getId());
					customerDetailsRepository.save(customerDetailsupdated);
				}
				demoProductExist.get().setCustomerDetailsId(customerDetailsupdated);
				DemoProducts saveDemoProducts = demoProductsRepo.save(demoProductExist.get());
				response.setData(saveDemoProducts);

			} else {
				response.setError(EnumTypeForErrorCodes.SCUS3003.name(), EnumTypeForErrorCodes.SCUS3003.errorMsg());

			}
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS3002.name(), EnumTypeForErrorCodes.SCUS3002.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;

	}

	/**
	 * addReturnDemoProduct service implementation
	 * 
	 * @param demoId
	 * @RequestBody demoProducts
	 * @return ServiceResponse<String>
	 */

	@Override
	public ServiceResponse<String> addReturnDemoProduct(String demoId) {
		log.info("returned demo products");
		ServiceResponse<DemoProducts> response = new ServiceResponse<>();
		try {
			DemoProducts demoReturns = demoProductsRepo.findByDemoId(demoId);

			if (demoReturns != null) {
				demoReturns.setDispatcstatusId(dispatchStatusRepository.getOne(4l));
				demoProductsRepo.save(demoReturns);
				Inventory inv = null;
				InventoryItem inventoryItem = inventoryItemRepo.findByBarcode(demoReturns.getQrCode());
				if (inventoryItem != null) {
					inv = inventoryItem.getInventoryId();
					if (inventoryItem.getItemStatusId().getId() == 7) {
						Long invCount = inv.getInventory();
						inv.setInventory(invCount + 1);
						inventoryRepo.save(inv);

					}
				}

			} else {
				response.setError(EnumTypeForErrorCodes.SCUS3003.name(), EnumTypeForErrorCodes.SCUS3003.errorMsg());
			}
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS3004.name(), EnumTypeForErrorCodes.SCUS3004.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return null;
	}

	@Override
	public ServiceResponse<DemoProducts> convertDemoProductToOrder(String invoice, String demoId) {
		log.info("convert demo product to order");
		ServiceResponse<DemoProducts> response = new ServiceResponse<>();
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(invoice);
			DemoProducts demoReturns = demoProductsRepo.findByDemoId(demoId);
			DemoProducts demoReturnsSaved = null;
			if (demoReturns != null) {
				demoReturns.setDispatcstatusId(dispatchStatusRepository.getOne(3l));
				demoReturns.setUnicommerceReferenceNumber(jsonObject.getString("unicommerceReferenceNumber"));
				demoReturnsSaved = demoProductsRepo.save(demoReturns);
				Dispatch dispatch = demoReturns.getDispatchId();
				if (dispatch != null) {
					List<Dispatch> dispatchList = dispatchRepo.findByInvoiceId(demoId);

					for (Dispatch dispatchedDemoProduct : dispatchList) {
						InventoryItem inventoryItem = inventoryItemRepo.findByBarcode(dispatchedDemoProduct.getBarcode());
						inventoryItem.setDemostatus(false);
						inventoryItem.getInventoryId().setDemo(inventoryItem.getInventoryId().getDemo()-1);
						inventoryItemRepo.save(inventoryItem);
						dispatchedDemoProduct.setDispatchType(dispatchTypesRepository.getOne(1l));
						dispatchedDemoProduct.setInvoiceId(jsonObject.getString("unicommerceReferenceNumber"));
						dispatchRepo.save(dispatchedDemoProduct);
					}
				} else {
					response.setError(EnumTypeForErrorCodes.SCUS711.name(), EnumTypeForErrorCodes.SCUS711.errorMsg());
				}

			} else {
				response.setError(EnumTypeForErrorCodes.SCUS3003.name(), EnumTypeForErrorCodes.SCUS3003.errorMsg());
			}
			response.setData(demoReturnsSaved);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS3004.name(), EnumTypeForErrorCodes.SCUS3004.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

	/**
	 * get By Id service implementation
	 * 
	 * @return ServiceResponse<DemoProducts>
	 */
	@Override
	public ServiceResponse<DemoProducts> getById(Long id) {
		log.info("get demo product by id");
		ServiceResponse<DemoProducts> response = new ServiceResponse<>();
		try {
			DemoProducts demoProduct = demoProductsRepo.findById(id).get();
			response.setData(demoProduct);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3005.name(), EnumTypeForErrorCodes.SCUS3005.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}
		return response;
	}

	/**
	 * get By Demo Id service implementation
	 * 
	 * @return ServiceResponse<DemoProducts>
	 */
	@Override
	public ServiceResponse<JSONObject> getByDemoId(String demoId) {
		log.info("get demo product by demo id");
		ServiceResponse<JSONObject> response = new ServiceResponse<>();
		try {
			DemoProducts demoProduct = demoProductsRepo.findByDemoId(demoId);
			JSONObject object = new JSONObject();

			if (demoProduct != null) {
				if (demoProduct.getDemoPrice() == null || demoProduct.getDemoPrice() == 0) {
					object.put("freeDemo", true);

				} else {
					object.put("freeDemo", false);

				}
				response.setData(object);
			} else {
				response.setError(EnumTypeForErrorCodes.SCUS3003.name(), EnumTypeForErrorCodes.SCUS3003.errorMsg());

			}
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3006.name(), EnumTypeForErrorCodes.SCUS3006.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}
		return response;
	}

	@Override
	public ServiceResponse<String> getByUnicommerceReferenceNumber(String unicommerceReferenceNumber) {
		log.info("Get by unicommerce reference number");
		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			List<RentalProducts> rental = rentalProductsRepo.findByInvoiceNumber(unicommerceReferenceNumber);
			DemoProducts demoProduct = demoProductsRepo.findByUnicommerceReferenceNumber(unicommerceReferenceNumber);
			List<Dispatch> dispatch = dispatchRepo.findByInvoiceId(unicommerceReferenceNumber);
			response.setData(null);
			if (demoProduct == null && dispatch.isEmpty() && rental.isEmpty()) {
				response.setData(SUCCESS);

			} else {

				response.setError(EnumTypeForErrorCodes.SCUS3010.name(), EnumTypeForErrorCodes.SCUS3010.errorMsg());

			}
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3006.name(), EnumTypeForErrorCodes.SCUS3006.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}
		return response;
	}

	/**
	 * get All Demo Products Details service implementation
	 * 
	 * @return ServiceResponse<List<DemoProducts>>
	 */
	@Override
	public ServiceResponse<List<JSONObject>> getAllDemoProductsDetails(@NotNull String startDate, String endDate,
			Long dispatcStatusId) {
		log.info("get All Demo Products details");
		ServiceResponse<List<JSONObject>> response = new ServiceResponse<>();
		List<JSONObject> demoList = new ArrayList<>();

		List<DemoProducts> demoProductList = new ArrayList<>();

		try {
			String endDate1 = LocalDate.parse(endDate).plusDays(1).toString();
			if (dispatcStatusId == 0) {
				demoProductList = demoProductsRepo.findDemoProductsByStartDateAndEndDate(startDate, endDate1);

			} else {
				demoProductList = demoProductsRepo.findDemoProductsByStartDateAndEndDateAndStatus(startDate, endDate1,
						dispatcStatusId);

			}
			for (DemoProducts demoProduct : demoProductList) {
				JSONObject obj = new JSONObject();

				obj.put("demo", demoProduct);
				if (demoProduct.getDispatcstatusId().getId() == 2 || demoProduct.getDispatcstatusId().getId() == 4
						|| demoProduct.getDispatcstatusId().getId() == 3) {
					String sku = "";
					String productName = "";
					String qrCode = "";
					List<Dispatch> dispatchlist = null;
					if (demoProduct.getDispatcstatusId().getId() == 3
							&& demoProduct.getUnicommerceReferenceNumber() != null) {
						dispatchlist = dispatchRepo.findByInvoiceId(demoProduct.getUnicommerceReferenceNumber());

					} else if (demoProduct.getDispatcstatusId().getId() == 3) {

						dispatchlist = dispatchRepo.findByInvoiceId(demoProduct.getDemoId());

					} else {

						dispatchlist = dispatchRepo.findByInvoiceId(demoProduct.getDemoId());

					}
					for (Dispatch dispatch : dispatchlist) {

						InventoryItem inventoryItem = inventoryItemRepo.findByBarcode(dispatch.getBarcode());

						sku = sku + inventoryItem.getInventoryId().getSkuCode() + ",";
						productName = productName + inventoryItem.getInventoryId().getProductName() + ",";
						qrCode = qrCode + dispatch.getBarcode() + ",";

					}

					if (sku.length() != 0) {
						sku = sku.substring(0, sku.length() - 1);
					} else {
						sku = demoProduct.getSkuCode();

					}
					if (productName.length() != 0) {

						productName = productName.substring(0, productName.length() - 1);
					} else {
						productName = demoProduct.getProductName();
					}
					if (qrCode.length() != 0) {

						qrCode = qrCode.substring(0, qrCode.length() - 1);
					} else {
						qrCode = demoProduct.getQrCode();
					}
					obj.put(SKUCODE, sku);
					obj.put(PRODUCTNAME, productName);
					obj.put(QRCODE, qrCode);

				} else {
					obj.put(SKUCODE, demoProduct.getSkuCode());
					obj.put(PRODUCTNAME, demoProduct.getProductName());
					obj.put(QRCODE, demoProduct.getQrCode());

				}
				demoList.add(obj);

			}

			response.setData(demoList);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3000.name(), EnumTypeForErrorCodes.SCUS3000.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}

		return response;
	}

	@Override
	public ServiceResponse<List<JSONObject>> getAllDemoProductsDetailss(@NotNull String startDate, String endDate,
			Long dispatcStatusId, Long paymentMode) {
		log.info("get All Demo Products details");
		ServiceResponse<List<JSONObject>> response = new ServiceResponse<>();
		List<JSONObject> demoList = new ArrayList<>();

		List<DemoProducts> demoProductList = new ArrayList<>();

		try {
			String endDate1 = LocalDate.parse(endDate).plusDays(1).toString();
			if (dispatcStatusId == 0) {
				demoProductList = demoProductsRepo.findDemoProductsByStartDateAndEndDate(startDate, endDate1);

			} else {
				demoProductList = demoProductsRepo.findDemoProductsByStartDateAndEndDateAndStatus(startDate, endDate1,
						dispatcStatusId);

			}
			for (DemoProducts demoProduct : demoProductList) {
				JSONObject obj = new JSONObject();

				if (demoProduct.getDispatcstatusId().getId() == 2 || demoProduct.getDispatcstatusId().getId() == 4
						|| demoProduct.getDispatcstatusId().getId() == 3) {
					String sku = "";
					String productName = "";
					String qrCode = "";
					List<Dispatch> dispatchlist = null;
					if (demoProduct.getDispatcstatusId().getId() == 3) {
						dispatchlist = dispatchRepo.findByInvoiceId(demoProduct.getUnicommerceReferenceNumber());

					} else {
						dispatchlist = dispatchRepo.findByInvoiceId(demoProduct.getDemoId());

					}
					for (Dispatch dispatch : dispatchlist) {

						InventoryItem inventoryItem = inventoryItemRepo.findByBarcode(dispatch.getBarcode());
						sku = sku + inventoryItem.getInventoryId().getSkuCode() + ",";
						productName = productName + inventoryItem.getInventoryId().getProductName() + ",";
						qrCode = qrCode + dispatch.getBarcode() + ",";

					}

					sku = sku.substring(0, sku.length() - 1);
					productName = productName.substring(0, productName.length() - 1);
					qrCode = qrCode.substring(0, qrCode.length() - 1);
					int i = 0;
					if (demoProduct.getDispatchId() != null
							&& demoProduct.getDispatchId().getPaymentModes().getId().equals(paymentMode)) {

						i = 1;
					}

					switch (i) {
					case 1:
						obj = getJsonObject(demoProduct, sku, productName, qrCode);
						demoList.add(obj);
						break;
					default:
						obj = getJsonObject(demoProduct, sku, productName, qrCode);
						demoList.add(obj);
						break;

					}
				} else {
					int i = 0;

					if (demoProduct.getDispatchId() != null
							&& demoProduct.getDispatchId().getPaymentModes().getId().equals(paymentMode)) {

						i = 1;
					}
					switch (i) {
					case 1:
						obj.put("demo", demoProduct);
						obj.put(SKUCODE, demoProduct.getSkuCode());
						obj.put(PRODUCTNAME, demoProduct.getProductName());
						obj.put(QRCODE, demoProduct.getQrCode());
						demoList.add(obj);
						break;
					default:
						obj.put("demo", demoProduct);
						obj.put(SKUCODE, demoProduct.getSkuCode());
						obj.put(PRODUCTNAME, demoProduct.getProductName());
						obj.put(QRCODE, demoProduct.getQrCode());
						demoList.add(obj);
						break;

					}
				}

			}

			response.setData(demoList);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3000.name(), EnumTypeForErrorCodes.SCUS3000.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}

		return response;
	}

	public JSONObject getJsonObject(DemoProducts demoProduct, String skuCode, String productName, String qrCode) {
		log.info("getJsonObject ");
		JSONObject obj = new JSONObject();
		ServiceResponse<JSONObject> response = new ServiceResponse<>();
		try {

			obj.put("demo", demoProduct);
			obj.put(SKUCODE, demoProduct.getSkuCode());
			obj.put(PRODUCTNAME, demoProduct.getProductName());
			obj.put(QRCODE, demoProduct.getQrCode());
			response.setData(obj);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1152.name(), EnumTypeForErrorCodes.SCUS1152.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return obj;
	}

	/**
	 * get All DispatchStatus service implementation
	 * 
	 * @return ServiceResponse<List<JSONObject>>
	 */
	@Override
	public ServiceResponse<List<JSONObject>> getAllDispatchStatus() {
		log.info("get all status ");

		ServiceResponse<List<JSONObject>> response = new ServiceResponse<>();
		try {
			List<JSONObject> obj = new ArrayList<>();
			List<DispatchStatus> allstatus = dispatchStatusRepository.findAll();
			for (DispatchStatus dispatchStatus : allstatus) {
				JSONObject object = new JSONObject();

				if (dispatchStatus.getId() <= 4) {
					object.put("status", dispatchStatus);
					obj.add(object);

				}
			}
			response.setData(obj);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1152.name(), EnumTypeForErrorCodes.SCUS1152.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<String> barcodesCheckForRentals(@NotNull String demoId, List<String> barcodes) {

		log.info("barcodes check for demo");

		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			DemoProducts demoProduct = demoProductsRepo.findByDemoId(demoId);
			if (demoProduct != null) {
				LOOP: for (String barcode : barcodes) {
					InventoryItem invItem = inventoryItemRepo.findByBarcode(barcode);
					if (demoProduct.getSkuCode().equals(invItem.getInventoryId().getSkuCode())) {

						response.setData("SUCCESS");
						break LOOP;
					} else {
						response.setError(EnumTypeForErrorCodes.SCUS3009.name(),
								EnumTypeForErrorCodes.SCUS3009.errorMsg());

					}

				}
			} else {

				response.setError(EnumTypeForErrorCodes.SCUS3003.name(), EnumTypeForErrorCodes.SCUS3003.errorMsg());

			}

		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3008.name(), EnumTypeForErrorCodes.SCUS3008.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}

		return response;
	}

	@Override
	public ServiceResponse<List<PaymentModes>> getAllPaymentModes() {
		log.info("get all payment modes");

		ServiceResponse<List<PaymentModes>> response = new ServiceResponse<>();
		try {
			List<PaymentModes> listPaymentModes = paymentModeRepository.findAll();
			response.setData(listPaymentModes);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS727.name(), EnumTypeForErrorCodes.SCUS727.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}
		return response;
	}

	@Override
	public ServiceResponse<List<String>> getAllProductNames() {
		log.info("get All Product Names");
		List<String> listOfProductNames = new ArrayList<>();

		ServiceResponse<List<String>> response = new ServiceResponse<>();
		try {

			List<Inventory> inventoryList = inventoryRepo.findAll();
			for (Inventory inventory : inventoryList) {
				listOfProductNames.add(inventory.getProductName());
			}
			response.setData(listOfProductNames);
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3012.name(), EnumTypeForErrorCodes.SCUS3012.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}
		return response;
	}

	@Override
	public ServiceResponse<JSONObject> getSkuCodeByProductName(JSONObject getSkuCodeByProductName) {
		log.info("get SkuCode By Product Name");
		ServiceResponse<JSONObject> response = new ServiceResponse<>();
		try {
			org.json.JSONObject obj = new org.json.JSONObject(getSkuCodeByProductName);
			JSONObject object = new JSONObject();

			Inventory inventory = inventoryRepo.findByProductName(obj.getString("name"));

			if (inventory != null) {
				object.put(SKUCODE, inventory.getSkuCode());
				response.setData(object);
			} else {
				response.setError(EnumTypeForErrorCodes.SCUS2202.name(), EnumTypeForErrorCodes.SCUS2202.errorMsg());

			}
		} catch (Exception exception) {
			response.setError(EnumTypeForErrorCodes.SCUS3011.name(), EnumTypeForErrorCodes.SCUS3011.errorMsg());
			log.error(utils.toJson(response.getError()), exception);
		}
		return response;

	}

	@Override
	public ServiceResponse<List<DispatchStatus>> getStatusForDemoReturns() {
		log.info("get all status for demo returns");

		ServiceResponse<List<DispatchStatus>> response = new ServiceResponse<>();
		List<DispatchStatus> list = new ArrayList<>();
		try {
			List<DispatchStatus> allstatus = dispatchStatusRepository.findAllDemoReturnStatus();

			for (DispatchStatus dispatchStatus : allstatus) {
				list.add(dispatchStatus);
			}
			response.setData(list);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1152.name(), EnumTypeForErrorCodes.SCUS1152.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<String> getCommentsForDemoRejection(String demoId, String rejectionComments) {
		log.info("get comments for demo rejection");
		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(rejectionComments);
			DemoProducts demoReturns = demoProductsRepo.findByDemoId(demoId);

			if (demoReturns != null) {
				demoReturns.setRejectionComments(jsonObject.getString("comments"));
				demoProductsRepo.save(demoReturns);
			}
			response.setData(SUCCESS);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS3004.name(), EnumTypeForErrorCodes.SCUS3004.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

}
