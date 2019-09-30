package com.mbb.mbbplatform.svcs.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.mbb.mbbplatform.common.EnumTypeForErrorCodes;
import com.mbb.mbbplatform.domain.CustomerDetails;
import com.mbb.mbbplatform.domain.DemoProducts;
import com.mbb.mbbplatform.domain.Dispatch;
import com.mbb.mbbplatform.domain.DispatchStatus;
import com.mbb.mbbplatform.domain.Facility;
import com.mbb.mbbplatform.domain.History;
import com.mbb.mbbplatform.domain.Inventory;
import com.mbb.mbbplatform.domain.InventoryItem;
import com.mbb.mbbplatform.domain.PaytmPaymentIntegration;
import com.mbb.mbbplatform.domain.RentalExtension;
import com.mbb.mbbplatform.domain.RentalProducts;
import com.mbb.mbbplatform.domain.RentalServiceTypes;
import com.mbb.mbbplatform.domain.User;
import com.mbb.mbbplatform.model.ServiceResponse;
import com.mbb.mbbplatform.model.Utils;
import com.mbb.mbbplatform.repos.CustomerDetailsRepository;
import com.mbb.mbbplatform.repos.DemoProductsRepository;
import com.mbb.mbbplatform.repos.DispatchRepository;
import com.mbb.mbbplatform.repos.DispatchStatusRepository;
import com.mbb.mbbplatform.repos.DispatchTypesRepository;
import com.mbb.mbbplatform.repos.FacilityRepository;
import com.mbb.mbbplatform.repos.HistoryRepository;
import com.mbb.mbbplatform.repos.InventoryItemRepository;
import com.mbb.mbbplatform.repos.InventoryRepository;
import com.mbb.mbbplatform.repos.PaytmPaymentIntegrationRepository;
import com.mbb.mbbplatform.repos.RentalExtensionRepository;
import com.mbb.mbbplatform.repos.RentalProductsRepository;
import com.mbb.mbbplatform.repos.RentalServiceTypesRepository;
import com.mbb.mbbplatform.svcs.EmailService;
import com.mbb.mbbplatform.svcs.RentalProductsService;
import com.mbb.mbbplatform.svcs.UserService;

@RestController
public class RentalProductsServiceImpl implements RentalProductsService {
	private static Logger log = LoggerFactory.getLogger(RentalProductsServiceImpl.class);
	@Autowired
	private Utils utils;
	@Value("${rentals.beforedays}")
	public Long beforeDays;

	@Value("${email}")
	private String replyTo;
	@Autowired
	private DispatchRepository dispatchRepo;
	@Autowired
	private InventoryRepository inventoryRepo;
	@Autowired
	private InventoryItemRepository inventoryItemRepo;
	@Autowired
	private DemoProductsRepository demoProductsRepo;
	@Autowired
	private RentalExtensionRepository rentalExtensionRepo;
	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private CustomerDetailsRepository customerDetailsRepo;

	@Autowired
	private RentalProductsRepository rentalProductsRepo;

	@Autowired
	private DemoProductsRepository demoProductsRepository;

	@Autowired
	private DispatchStatusRepository dispatchStatusRepo;

	@Autowired
	private RentalServiceTypesRepository rentalServiceTypesRepo;

	@Autowired
	private PaytmPaymentIntegrationRepository paytmPaymentIntegrationRepo;

	@Autowired
	private InventoryServiceImpl inventoryServiceImpl;

	@Autowired
	private EmailService emailsvc;

	@Autowired
	private DispatchTypesRepository dispatchTypesRepo;

	@Autowired
	private UserService userSvc;

	@Autowired
	private FacilityRepository facilityRepository;

	
	private static final String INVENTORY = "inventory";
	private static final String SKUCODE = "skuCode";
	private static final String FACILITY = "facility";
	private static final String PRODUCTNAME = "productName";
	private static final String QRCODE = "qrcode";
	private static final String PONUMBER = "poNumber";
	private static final String CREATEDUSER = "createdUser";

	private static final String CREATEDDATE = "createdDate";

	private static final String UPDATEDDATE = "updateDate";

	private static final String SERIALNUMBER = "serialNumber";

	private static final String UPDATEDUSER = "updatedUser";

	private static final String SKUIMAGE = "SKUImage";

	private static final String PRODUCTIMAGE = "productImage";


	static final String ACCESSORYSTATUS = "accessoryStatus";

	private static final String BADINVENTORY = "badInventory";

	private static final String BARCODEID = "barcodeId";
	
	private static final String PENDINGQCASSEMENT = "pendingQcAccessment";
	private static final String RENTAL = "rental";
	private static final String EXTENSION = "extension";


	@Value("${paytmIntegration.user}")
	private String userName;

	@Value("${paytmIntegration.password}")
	private String passWord;
	@Value("${razorgateway.api}")
	private String razorApi;
	@Value("${paymentlink.expirydays}")
	private Long expiryDays;

	@Override
	public ServiceResponse<RentalProducts> addRentals(@Valid RentalProducts rentalProducts) {
		log.info("adding rental product");

		ServiceResponse<RentalProducts> response = new ServiceResponse<>();
		try {

			CustomerDetails customerDetails = customerDetailsRepo.save(rentalProducts.getCustomerDetailsId());
			RentalProducts serialNumber = rentalProductsRepo.findBylastRecord();
			if (serialNumber == null) {
				String num = "1";
				String serviceId = "R" + StringUtils.leftPad(num, 4, "0");

				rentalProducts.setRentalId(serviceId);
			} else {

				String str = serialNumber.getRentalId();
				Long serviceId = Long.parseLong(str.substring(1));
				Long newserviceId = serviceId + 1;
				String num = newserviceId.toString();
				String newserviceId1 = "R" + StringUtils.leftPad(num, 4, "0");

				rentalProducts.setRentalId(newserviceId1);
			}
			rentalProducts.setCustomerDetailsId(customerDetails);
			rentalProducts.setExtension(0l);
			RentalProducts savedRentalProducts = rentalProductsRepo.save(rentalProducts);
			response.setData(savedRentalProducts);

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1150.name(), EnumTypeForErrorCodes.SCUS1150.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

	@Override
	public ServiceResponse<RentalProducts> getById(Long id) {

		log.info("get by id");

		ServiceResponse<RentalProducts> response = new ServiceResponse<>();
		try {

			RentalProducts rental = rentalProductsRepo.findById(id).get();
			if (rental != null) {

				response.setData(rental);
			} else {
				response.setError(EnumTypeForErrorCodes.SCUS1158.name(), EnumTypeForErrorCodes.SCUS1158.errorMsg());

			}
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1154.name(), EnumTypeForErrorCodes.SCUS1154.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<List<JSONObject>> getAllRentalProducts(String startDate, String endDate, Long statusId,
			Long facilityId, Long serviceType) {
		log.info("get all rental products");

		ServiceResponse<List<JSONObject>> response = new ServiceResponse<>();
		List<JSONObject> objList = new ArrayList<>();
		try {
			String endDate1 = LocalDate.parse(endDate).plusDays(1).toString();
			List<RentalProducts> allRentals;
			if (statusId == 0 && serviceType == 0) {
				allRentals = rentalProductsRepo.findByDates(startDate, endDate1);
			} else if (statusId == 0) {
				allRentals = rentalProductsRepo.findByDatesAndService(startDate, endDate1, serviceType);

			} else if (serviceType == 0) {
				allRentals = rentalProductsRepo.findByDatesAndStatus(startDate, endDate1, statusId);

			}

			else {

				allRentals = rentalProductsRepo.findByDatesAndStatusAndService(startDate, endDate1, statusId,
						serviceType);

			}
			for (RentalProducts rentalProducts : allRentals) {
				JSONObject obj = new JSONObject();
				obj.put(RENTAL, rentalProducts);
				List<RentalExtension> extensionList = rentalExtensionRepo.findByRentalProductId(rentalProducts);
				LocalDate rentalEndDate = rentalProducts.getRentalEndDate();
				LocalDate todayDate = LocalDate.now();
				LocalDate finalDate;
				if (rentalProducts.getDispatchStatusId().getId() == 2
						|| rentalProducts.getDispatchStatusId().getId() == 7) {
					if (extensionList.isEmpty()) {
						finalDate = rentalEndDate.minusDays(beforeDays);
					} else {
						int last = extensionList.size() - 1;
						RentalExtension extension = extensionList.get(last);
						rentalEndDate = extension.getExtensionEndDate();
						finalDate = extension.getExtensionEndDate().minusDays(beforeDays);

					}
					if ((finalDate.isEqual(todayDate) || finalDate.isBefore(todayDate))
							&& (todayDate.isEqual(rentalEndDate) || todayDate.isBefore(rentalEndDate))) {

						obj.put(EXTENSION, true);

					} else {
						obj.put(EXTENSION, false);

					}

				} else {
					obj.put(EXTENSION, false);

				}

				if (rentalProducts.getDispatchStatusId().getId() == 2
						|| rentalProducts.getDispatchStatusId().getId() == 3
						|| rentalProducts.getDispatchStatusId().getId() == 7
						|| rentalProducts.getDispatchStatusId().getId() == 11
						|| rentalProducts.getDispatchStatusId().getId() == 12) {
					List<Dispatch> listDispatches;

					listDispatches = dispatchRepo.findByInvoiceId(rentalProducts.getInvoiceNumber());

					if (facilityId == 0 || listDispatches.get(0).getFacilityId().getId().equals(facilityId)) {

						if (!listDispatches.isEmpty()) {

							StringBuilder skuCode = new StringBuilder();
							StringBuilder productName = new StringBuilder();
							StringBuilder qrCode = new StringBuilder();
							for (Dispatch dispatch : listDispatches) {
								InventoryItem invItem = inventoryItemRepo.findByBarcode(dispatch.getBarcode());

								if ((rentalProducts.getDispatchStatusId().getId() == 12
										|| rentalProducts.getDispatchStatusId().getId() == 3
										|| rentalProducts.getDispatchStatusId().getId() == 11)) {
									if (dispatch.getProductReturn()) {
										Dispatch dis = dispatchRepo.findById(rentalProducts.getDispatchId().getId())
												.get();
										if (dis.getCreatedTime().isAfter(dispatch.getCreatedTime().minusSeconds(10))
												&& dis.getCreatedTime()
														.isBefore(dispatch.getCreatedTime().plusSeconds(10))) {
											skuCode = skuCode.append(invItem.getInventoryId().getSkuCode() + ",");
											productName = productName
													.append(invItem.getInventoryId().getProductName() + ",");
											qrCode = qrCode.append(invItem.getBarcode() + ",");
										}
									}
								} else if (!dispatch.getProductReturn()) {
									skuCode = skuCode.append(invItem.getInventoryId().getSkuCode() + ",");
									productName = productName.append(invItem.getInventoryId().getProductName() + ",");
									qrCode = qrCode.append(invItem.getBarcode() + ",");

								}

							}
							String skuCode1 = skuCode.substring(0, skuCode.length() - 1);
							String productName1 = productName.substring(0, productName.length() - 1);
							String qrCode1 = qrCode.substring(0, qrCode.length() - 1);
							obj.put("skuCode", skuCode1);
							obj.put("productName", productName1);
							obj.put("qrCode", qrCode1);
							if (listDispatches.get(0).getDispatchPaymentDocuments() != null) {
								obj.put("paymentImage", true);
								obj.put("dispatchId", listDispatches.get(0).getId());
								obj.put("facility", listDispatches.get(0).getFacilityId().getFacility());

							} else {
								obj.put("paymentImage", false);
								obj.put("dispatchId", null);
								obj.put("facility", null);
							}
							objList.add(obj);
						}
					}

				} else {
					obj.put("skuCode", rentalProducts.getSkucode());
					obj.put("productName", rentalProducts.getProductName());
					obj.put("qrCode", rentalProducts.getBarCode());
					obj.put("paymentImage", false);
					obj.put("dispatchId", null);
					obj.put("facility", null);
					objList.add(obj);
				}

			}

			response.setData(objList);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1154.name(), EnumTypeForErrorCodes.SCUS1154.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<List<RentalServiceTypes>> getAllRentalServiceTypes() {
		log.info("get all rental service types");

		ServiceResponse<List<RentalServiceTypes>> response = new ServiceResponse<>();
		try {
			List<RentalServiceTypes> allRentalServiceTypes = rentalServiceTypesRepo.findAll();
			response.setData(allRentalServiceTypes);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1151.name(), EnumTypeForErrorCodes.SCUS1151.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<List<DispatchStatus>> getAllDispatchStatus() {
		log.info("get all status ");

		ServiceResponse<List<DispatchStatus>> response = new ServiceResponse<>();
		try {
			List<DispatchStatus> allstatus = dispatchStatusRepo.findAll();
			response.setData(allstatus);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1152.name(), EnumTypeForErrorCodes.SCUS1152.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<RentalProducts> updateRentals(@Valid RentalProducts rentalProducts, Long rentalId) {
		log.info("update rental product ");

		ServiceResponse<RentalProducts> response = new ServiceResponse<>();
		try {

			RentalProducts existingRentalProduct = rentalProductsRepo.findById(rentalId).get();
			if (existingRentalProduct != null) {

				if (existingRentalProduct.getId().equals(rentalProducts.getId())) {

					if (existingRentalProduct.getCustomerDetailsId().getId()
							.equals(rentalProducts.getCustomerDetailsId().getId())) {

						CustomerDetails existingCustomerDetails = customerDetailsRepo
								.findById(existingRentalProduct.getCustomerDetailsId().getId()).get();
						existingCustomerDetails.setAddress(rentalProducts.getCustomerDetailsId().getAddress());
						existingCustomerDetails
								.setAlternatePhoneNo(rentalProducts.getCustomerDetailsId().getAlternatePhoneNo());

						existingCustomerDetails.setCity(rentalProducts.getCustomerDetailsId().getCity());
						existingCustomerDetails.setEmailId(rentalProducts.getCustomerDetailsId().getEmailId());
						existingCustomerDetails.setName(rentalProducts.getCustomerDetailsId().getName());
						existingCustomerDetails.setPhone(rentalProducts.getCustomerDetailsId().getPhone());
						existingCustomerDetails.setPincode(rentalProducts.getCustomerDetailsId().getPincode());
						existingCustomerDetails.setState(rentalProducts.getCustomerDetailsId().getState());
						existingCustomerDetails.setLandmark(rentalProducts.getCustomerDetailsId().getLandmark());
						CustomerDetails savedCustomerDetails = customerDetailsRepo.save(existingCustomerDetails);

						existingRentalProduct.setCustomerDetailsId(savedCustomerDetails);
						existingRentalProduct.setBarCode(rentalProducts.getBarCode());
						existingRentalProduct.setComments(rentalProducts.getComments());
						existingRentalProduct.setInvoiceNumber(rentalProducts.getInvoiceNumber());
						existingRentalProduct.setOrderDate(rentalProducts.getOrderDate());
						existingRentalProduct.setProductName(rentalProducts.getProductName());
						existingRentalProduct.setSkucode(rentalProducts.getSkucode());
						existingRentalProduct.setRentalAmount(rentalProducts.getRentalAmount());
						existingRentalProduct.setRentalDays(rentalProducts.getRentalDays());
						existingRentalProduct.setRentalServiceType(rentalProducts.getRentalServiceType());
						existingRentalProduct.setRequestedDate(rentalProducts.getRequestedDate());
						existingRentalProduct.setDeliveredBy(rentalProducts.getDeliveredBy());
						existingRentalProduct.setRentalStartDate(rentalProducts.getRentalStartDate());
						existingRentalProduct.setDepositAmount(rentalProducts.getDepositAmount());
						existingRentalProduct.setRentalEndDate(rentalProducts.getRentalEndDate());
						existingRentalProduct.setDoctorDetails(rentalProducts.getDoctorDetails());
						existingRentalProduct.setConvertedComments(rentalProducts.getConvertedComments());
						existingRentalProduct.setDispatchStatusId(rentalProducts.getDispatchStatusId());

						RentalProducts savedRental = rentalProductsRepo.save(existingRentalProduct);
						response.setData(savedRental);
					} else {

						response.setError(EnumTypeForErrorCodes.SCUS1157.name(),
								EnumTypeForErrorCodes.SCUS1157.errorMsg());

					}

				} else {

					response.setError(EnumTypeForErrorCodes.SCUS1156.name(), EnumTypeForErrorCodes.SCUS1156.errorMsg());

				}
			} else {

				response.setError(EnumTypeForErrorCodes.SCUS1158.name(), EnumTypeForErrorCodes.SCUS1158.errorMsg());

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1155.name(), EnumTypeForErrorCodes.SCUS1155.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;

	}

	@Override
	public ServiceResponse<List<DispatchStatus>> getStatusForRentals() {
		log.info("get all status for rentals");

		ServiceResponse<List<DispatchStatus>> response = new ServiceResponse<>();
		List<DispatchStatus> list = new ArrayList<>();
		try {
			List<DispatchStatus> allstatus = dispatchStatusRepo.findAll();
			for (DispatchStatus dispatchStatus : allstatus) {
				if (dispatchStatus.getId() == 1 || dispatchStatus.getId() == 6 || dispatchStatus.getId() == 8
						|| dispatchStatus.getId() == 5) {

					list.add(dispatchStatus);
				}
			}

			response.setData(list);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1152.name(), EnumTypeForErrorCodes.SCUS1152.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<RentalExtension> addRentalExtension(@Valid RentalExtension rentalExtension, Long rentalId) {
		log.info("get all status for rentals");

		ServiceResponse<RentalExtension> response = new ServiceResponse<>();
		try {
			RentalProducts rentalProduct = rentalProductsRepo.findById(rentalId).get();
			if (rentalProduct != null) {
				Long extension = rentalProduct.getExtension() + 1;
				rentalProduct.setExtension(extension);
				if (rentalId.equals(rentalExtension.getRentalProductId().getId())) {
					rentalProduct.setDispatchStatusId(dispatchStatusRepo.getOne(7l));
					rentalProductsRepo.save(rentalProduct);

					RentalExtension savedRentalExtension = rentalExtensionRepo.save(rentalExtension);
					response.setData(savedRentalExtension);
				} else {
					response.setError(EnumTypeForErrorCodes.SCUS1156.name(), EnumTypeForErrorCodes.SCUS1156.errorMsg());

				}

			} else {
				response.setError(EnumTypeForErrorCodes.SCUS1158.name(), EnumTypeForErrorCodes.SCUS1158.errorMsg());

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1159.name(), EnumTypeForErrorCodes.SCUS1159.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;
	}

	@Override
	public ServiceResponse<List<JSONObject>> viewExtesnions(Long rentalId) {
		log.info("view rental extensions");

		ServiceResponse<List<JSONObject>> response = new ServiceResponse<>();
		List<JSONObject> list = new ArrayList<>();
		try {
			RentalProducts rentalProduct = rentalProductsRepo.findById(rentalId).get();

			List<RentalExtension> extensionList = rentalExtensionRepo.findByRentalProductId(rentalProduct);
			if (!extensionList.isEmpty()) {
				int i = 0;
				for (RentalExtension rentalExtension : extensionList) {
					i++;
					JSONObject obj1 = new JSONObject();
					obj1.put("invoiceNumber", rentalExtension.getInvoiceNumber());
					obj1.put("rentalDays", rentalExtension.getDays());
					obj1.put("rentalAmount", rentalExtension.getPrice());
					obj1.put("extendedDate", rentalExtension.getExtendedDate());
					obj1.put("comments", rentalExtension.getComments());

					obj1.put(EXTENSION, i);
					list.add(obj1);
				}

			}
			response.setData(list);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1160.name(), EnumTypeForErrorCodes.SCUS1160.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Scheduled(cron = "${rentals.sendemails}")
	@Override
	public ServiceResponse<String> sendMailAlertsForRentals() {

		log.info("send mail alerts for rentals");

		ServiceResponse<String> response = new ServiceResponse<>();
		try {

			List<RentalProducts> allRentals = rentalProductsRepo.findAll();
			for (RentalProducts rentalProducts : allRentals) {

				List<RentalExtension> extensionList = rentalExtensionRepo.findByRentalProductId(rentalProducts);
				LocalDate rentalEndDate;
				LocalDate todayDate = LocalDate.now();
				LocalDate finalDate;

				if (rentalProducts.getDispatchStatusId().getId() == 2) {
					if (extensionList.isEmpty()) {
						rentalEndDate = rentalProducts.getRentalEndDate();
						finalDate = rentalEndDate.minusDays(beforeDays);
					} else {
						int last = extensionList.size() - 1;
						RentalExtension extension = extensionList.get(last);
						finalDate = extension.getExtensionEndDate().minusDays(beforeDays);
						rentalEndDate = extension.getExtendedDate();

					}
					if ((finalDate.isEqual(todayDate) || finalDate.isBefore(todayDate))
							&& (todayDate.isEqual(rentalEndDate) || todayDate.isBefore(rentalEndDate))) {
						DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						String formattedDate = rentalEndDate.format(dateFormatter);

						String body1 = "Greetings,<br><br>"
								+ "We would like to thank you for being connected with medicalbulkbuy.com <br>"
								+ "The way you paid your rental charges in time without asking is marvelous.<br>"
								+ "You are receiving this email as a reminder to you about the rental period <br>"
								+ "getting expired on <strong>" + formattedDate + "</strong>.<br><br>";
						String body2 = "If you want to extend it further,Please make the payment likewise <br>"
								+ "before you did it and drop us the payment proofs at below mentioned email <br>"
								+ "info@medicalbulkbuy.com<br><br>" +

								"If you have any concerns in this, feel free to contact us via <strong>Phone call (1800-313-2353) or Email at  info@medicalbulkbuy.com. <br><br><br>Thanks, <br>"
								+ "GVS-Enterprises pvt ltd. </strong>";
						String subject = "GVS-Enterprises pvt ltd-Rental Expiry Reminder";
						emailsvc.notifyUserByEmail(rentalProducts.getCustomerDetailsId().getEmailId(), body1 + body2,
								subject);

						ServiceResponse<Collection<User>> users = userSvc.getUsersByRole("ADMIN");
						Collection<User> usersData = users.getData();

						for (User user : usersData) {

							if (user.isNotificationStatus()) {
								String email = user.getEmail();

								MimeMessage mail = mailSender.createMimeMessage();

								MimeMessageHelper helper = new MimeMessageHelper(mail, true);
								helper.setSubject(subject);
								helper.setTo(email);
								helper.setReplyTo(replyTo);

								helper.setText(body1 + body2, true);

								mailSender.send(mail);

								response.setData("Success");

							}

						}

					}

				}

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1161.name(), EnumTypeForErrorCodes.SCUS1161.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<List<String>> getRentalsForDispatch() {
		log.info("get Rentals For dispatch");

		ServiceResponse<List<String>> response = new ServiceResponse<>();
		List<String> invoices = new ArrayList<>();
		try {
			List<RentalProducts> rentalList = rentalProductsRepo.findAll();
			for (RentalProducts rentalProducts : rentalList) {
				if (rentalProducts.getDispatchStatusId().getId() == 9
						|| rentalProducts.getDispatchStatusId().getId() == 12) {

					invoices.add(rentalProducts.getInvoiceNumber());
				}
			}
			response.setData(invoices);

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1162.name(), EnumTypeForErrorCodes.SCUS1162.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<String> invoiceCheck(String invoiceNumber) {

		log.info("invoice check for add rentals");

		ServiceResponse<String> response = new ServiceResponse<>();
		try {

			List<RentalProducts> rental = rentalProductsRepo.findByInvoiceNumber(invoiceNumber);
			List<Dispatch> dispatch = dispatchRepo.findByInvoiceId(invoiceNumber);
			DemoProducts demo = demoProductsRepository.findByDemoId(invoiceNumber);
			response.setData("");
			DemoProducts demoProduct = demoProductsRepo.findByUnicommerceReferenceNumber(invoiceNumber);

			if (rental.isEmpty() && dispatch.isEmpty() && demoProduct == null && demo == null) {
				response.setData("success");

			} else {

				response.setError(EnumTypeForErrorCodes.SCUS709.name(), EnumTypeForErrorCodes.SCUS709.errorMsg());

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1162.name(), EnumTypeForErrorCodes.SCUS1162.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<RentalProducts> convertToOrder(String invoiceNumber, Long rentalId) {

		log.info("invoice check for add rentals");

		ServiceResponse<RentalProducts> response = new ServiceResponse<>();
		try {
			RentalProducts rental = rentalProductsRepo.findById(rentalId).get();
			if (rental != null) {

				rental.setDispatchInvoiceId(invoiceNumber);
				rental.setDispatchStatusId(dispatchStatusRepo.getOne(3l));
				RentalProducts savedRental = rentalProductsRepo.save(rental);

				List<Dispatch> listDispatch = dispatchRepo.findByInvoiceId(rental.getInvoiceNumber());
				for (Dispatch dispatch : listDispatch) {

					dispatch.setInvoiceId(invoiceNumber);
					dispatch.setDispatchType(dispatchTypesRepo.getOne(1l));
					dispatchRepo.save(dispatch);

				}
				response.setData(savedRental);

			} else {
				response.setError(EnumTypeForErrorCodes.SCUS1158.name(), EnumTypeForErrorCodes.SCUS1158.errorMsg());

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1162.name(), EnumTypeForErrorCodes.SCUS1162.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<String> invoiceCheckForDropDown(String invoiceNumber) {
		log.info("invoice check for add rentals drop down");

		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			List<String> rental = getRentalsForDispatch().getData();

			if (rental.contains(invoiceNumber)) {

				response.setData("success");
			} else {
				response.setError(EnumTypeForErrorCodes.SCUS1165.name(), EnumTypeForErrorCodes.SCUS1165.errorMsg());

			}

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1162.name(), EnumTypeForErrorCodes.SCUS1162.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<List<JSONObject>> getRentalInventory(Long facilityId, Boolean status) {
		log.info("get Product Count Based On Facility");
		ServiceResponse<List<JSONObject>> response = new ServiceResponse<>();

		try {

			List<Inventory> listOfInventorys = new ArrayList<>();
			List<InventoryItem> listInvItem ;

			List<JSONObject> objList = new ArrayList<>();

			if (facilityId == 0) {
				listInvItem = inventoryItemRepo.findByFacilityIdRental();
				for (InventoryItem inventoryItem : listInvItem) {
					if (!listOfInventorys.contains(inventoryItem.getInventoryId())) {
						listOfInventorys.add(inventoryItem.getInventoryId());
					}
				}
				for (Inventory inventory : listOfInventorys) {
					Long demo = 0l;
					Long rental = 0l;
					JSONObject object = new JSONObject();

					List<InventoryItem> avaliableInventoryItemList = new ArrayList<>();
					List<InventoryItem> badInvInventoryItemList = new ArrayList<>();
					List<InventoryItem> pendingQcInventoryItemList = new ArrayList<>();

					Collection<InventoryItem> gettingInventoryItemsList = inventoryItemRepo
							.findByInventoryId(inventory);
					Long avaliable = (long) 0;
					Long badInv = (long) 0;
					Long pendingQc = (long) 0;

					for (InventoryItem inventoryItem : gettingInventoryItemsList) {
						if (inventoryItem.getFacilityId().getId() == 4 || inventoryItem.getFacilityId().getId() == 5
								|| inventoryItem.getFacilityId().getId() == 7) {
							if (inventoryItem.getApprovalstatus()) {
								if (!inventoryItem.getScanned()) {
									if (inventoryItem.getItemStatusId().getId() == 1) {
										avaliableInventoryItemList.add(inventoryItem);
										avaliable = (long) avaliableInventoryItemList.size();

									} else if (inventoryItem.getItemStatusId().getId() == 6) {
										badInvInventoryItemList.add(inventoryItem);
										badInv = (long) badInvInventoryItemList.size();

									} else if (inventoryItem.getItemStatusId().getId() == 5) {
										pendingQcInventoryItemList.add(inventoryItem);
										pendingQc = (long) pendingQcInventoryItemList.size();

									} else if (inventoryItem.getItemStatusId().getId() == 7) {

										if (inventoryItem.getDemostatus()) {
											demo = demo + 1;

										} else if (inventoryItem.getRentalstatus()) {
											rental = rental + 1;
										}

									}
								}
							}

						}

					}

					object.put(SKUCODE, inventory.getSkuCode());
					object.put(PRODUCTNAME, inventory.getProductName());
					object.put(INVENTORY, avaliable);
					object.put(BADINVENTORY, badInv);
					object.put(PENDINGQCASSEMENT, pendingQc);
					object.put("id", inventory.getId());

					object.put(BARCODEID, inventory.getBarcodeId());
					object.put(RENTAL, rental);
					object.put("demo", demo);
					object.put(ACCESSORYSTATUS, inventory.getAccessoriesStatus());

					if (inventory.getProductImage() != null) {

						object.put(PRODUCTIMAGE, true);
						byte[] inventoryImage = inventory.getProductImage();
						inventoryServiceImpl.imageToThumbnail(inventoryImage).getData();

						object.put(SKUIMAGE, inventoryServiceImpl.imageToThumbnail(inventoryImage).getData());

					} else {
						object.put(PRODUCTIMAGE, false);
						object.put(SKUIMAGE, false);
					}

					objList.add(object);

				}

			} else {

				String skuCode = null;
				String productName = null;
				Optional<Facility> facility = facilityRepository.findById(facilityId);

				listInvItem = (List<InventoryItem>) inventoryItemRepo.findByFacilityId(facility.get());

				for (InventoryItem inventoryItem : listInvItem) {
					if (!listOfInventorys.contains(inventoryItem.getInventoryId())) {
						listOfInventorys.add(inventoryItem.getInventoryId());
					}
				}
				for (Inventory inventory : listOfInventorys) {
					Long demo = 0l;
					Long rental = 0l;
					JSONObject object = new JSONObject();
					skuCode = inventory.getSkuCode();
					productName = inventory.getProductName();

					if (facility.isPresent()) {
						List<InventoryItem> avaliableInventoryItemList = new ArrayList<>();
						List<InventoryItem> badInvInventoryItemList = new ArrayList<>();
						List<InventoryItem> pendingQcInventoryItemList = new ArrayList<>();

						Collection<InventoryItem> gettingInventoryItemsList = inventoryItemRepo
								.findByInventoryId(inventory);
						Long avaliable = (long) 0;
						Long badInv = (long) 0;
						Long pendingQc = (long) 0;

						for (InventoryItem inventoryItem : gettingInventoryItemsList) {
							if (inventoryItem.getFacilityId().getId().equals(facilityId)) {
								if (inventoryItem.getApprovalstatus()) {
									if (!inventoryItem.getScanned()) {
										if (inventoryItem.getItemStatusId().getId() == 1) {
											avaliableInventoryItemList.add(inventoryItem);
											avaliable = (long) avaliableInventoryItemList.size();

										} else if (inventoryItem.getItemStatusId().getId() == 6) {
											badInvInventoryItemList.add(inventoryItem);
											badInv = (long) badInvInventoryItemList.size();

										} else if (inventoryItem.getItemStatusId().getId() == 5) {
											pendingQcInventoryItemList.add(inventoryItem);
											pendingQc = (long) pendingQcInventoryItemList.size();

										} else if (inventoryItem.getItemStatusId().getId() == 7) {

											if (inventoryItem.getDemostatus()) {
												demo = demo + 1;

											} else if (inventoryItem.getRentalstatus()) {
												rental = rental + 1;
											}

										}
									}
								}

							}

						}

						object.put(SKUCODE, skuCode);
						object.put(PRODUCTNAME, productName);
						object.put(INVENTORY, avaliable);
						object.put(BADINVENTORY, badInv);
						object.put(PENDINGQCASSEMENT, pendingQc);
						object.put("id", inventory.getId());

						object.put(BARCODEID, inventory.getBarcodeId());
						object.put(RENTAL, rental);
						object.put("demo", demo);
						object.put(ACCESSORYSTATUS, inventory.getAccessoriesStatus());

						if (inventory.getProductImage() != null) {

							object.put(PRODUCTIMAGE, true);
							byte[] inventoryImage = inventory.getProductImage();
							inventoryServiceImpl.imageToThumbnail(inventoryImage).getData();

							object.put(SKUIMAGE, inventoryServiceImpl.imageToThumbnail(inventoryImage).getData());

						} else {
							object.put(PRODUCTIMAGE, false);
							object.put(SKUIMAGE, false);
						}

						objList.add(object);

					}
				}
			}

			response.setData(objList);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS051.name(), EnumTypeForErrorCodes.SCUS051.errorMsg());
			log.error(utils.toJson(response.getError()), e);

		}
		return response;
	}

	@Override
	public ServiceResponse<Collection<JSONObject>> getRentalInventoryItemsBasedOnSkuCodeAndDates(
			@PathVariable String startDate, @PathVariable String endDate, @PathVariable String skuCode,
			@PathVariable Long statusId, @PathVariable Long facilityId) {
		log.info("get inventory item based on sku and dates");
		ServiceResponse<Collection<JSONObject>> response = new ServiceResponse<>();
		List<JSONObject> objList = new ArrayList<>();
		try {
			String endDate1 = LocalDate.parse(endDate).plusDays(1).toString();

			Inventory inventory = inventoryRepo.findBySkuCode(skuCode);

			Collection<InventoryItem> inventoryItemList = inventoryItemRepo
					.findByInventoryIdAndDatesAndRentalFacility(startDate, endDate1, inventory);

			for (InventoryItem inventoryItem : inventoryItemList) {
				if (inventoryItem.getApprovalstatus() && !inventoryItem.getScanned()) {

					if (statusId == 8) {
						if ((inventoryItem.getItemStatusId().getId() == 7 && facilityId == 0)
								|| (inventoryItem.getItemStatusId().getId() == 7
										&& inventoryItem.getFacilityId().getId().equals(facilityId))) {

							if (inventoryItem.getDemostatus()) {

								JSONObject obj = new JSONObject();
								String qrcode = inventoryItem.getBarcode();
								String createdUser = inventoryItem.getCreatedUser();

								obj.put(QRCODE, qrcode);
								obj.put(PRODUCTNAME, inventory.getProductName());

								ZonedDateTime create = inventoryItem.getCreatedTime();
								ZonedDateTime update = inventoryItem.getUpdatedTime();

								String createDate = create.toString();
								String updateDate = update.toString();

								obj.put(CREATEDDATE, createDate.substring(0, 10));
								obj.put(UPDATEDDATE, updateDate.substring(0, 10));
								obj.put(SERIALNUMBER, inventoryItem.getSerialNumber());

								obj.put(CREATEDUSER, createdUser);
								obj.put(UPDATEDUSER, inventoryItem.getUpdatedUser());
								obj.put(PONUMBER, inventoryItem.getPoVendorId().getPurchaseOrderNumber());

								obj.put(FACILITY, inventoryItem.getFacilityId());

								List<History> listInv = historyRepository.findByBarcode(inventoryItem.getBarcode());
								if (listInv.isEmpty()) {
									obj.put("history", null);

								} else {

									obj.put("history", listInv.get(listInv.size() - 1).getHistoryDetails());
								}
								objList.add(obj);

							}
						}

					} else if (statusId == 9) {

						if ((inventoryItem.getItemStatusId().getId() == 7 && facilityId == 0)
								|| (inventoryItem.getItemStatusId().getId() == 7
										&& inventoryItem.getFacilityId().getId().equals(facilityId))) {
							if (inventoryItem.getRentalstatus()) {

								JSONObject obj = new JSONObject();
								String qrcode = inventoryItem.getBarcode();
								String createdUser = inventoryItem.getCreatedUser();

								obj.put(QRCODE, qrcode);
								obj.put(PRODUCTNAME, inventory.getProductName());

								ZonedDateTime create = inventoryItem.getCreatedTime();
								ZonedDateTime update = inventoryItem.getUpdatedTime();

								String createDate = create.toString();
								String updateDate = update.toString();

								obj.put(CREATEDDATE, createDate.substring(0, 10));
								obj.put(UPDATEDDATE, updateDate.substring(0, 10));
								obj.put(SERIALNUMBER, inventoryItem.getSerialNumber());

								obj.put(CREATEDUSER, createdUser);
								obj.put(UPDATEDUSER, inventoryItem.getUpdatedUser());
								obj.put(PONUMBER, inventoryItem.getPoVendorId().getPurchaseOrderNumber());

								obj.put(FACILITY, inventoryItem.getFacilityId());

								List<History> listInv = historyRepository.findByBarcode(inventoryItem.getBarcode());
								if (listInv.isEmpty()) {
									obj.put("history", null);

								} else {

									obj.put("history", listInv.get(listInv.size() - 1).getHistoryDetails());
								}
								objList.add(obj);
							}
						}
					} else if ((inventoryItem.getItemStatusId().getId().equals(statusId) && facilityId == 0)
							|| (inventoryItem.getItemStatusId().getId().equals(statusId)
									&& inventoryItem.getFacilityId().getId().equals(facilityId))) {
						JSONObject obj = new JSONObject();
						String qrcode = inventoryItem.getBarcode();
						String createdUser = inventoryItem.getCreatedUser();

						obj.put(QRCODE, qrcode);
						obj.put(PRODUCTNAME, inventory.getProductName());

						ZonedDateTime create = inventoryItem.getCreatedTime();
						ZonedDateTime update = inventoryItem.getUpdatedTime();

						String createDate = create.toString();
						String updateDate = update.toString();

						obj.put(CREATEDDATE, createDate.substring(0, 10));
						obj.put(UPDATEDDATE, updateDate.substring(0, 10));
						obj.put(SERIALNUMBER, inventoryItem.getSerialNumber());

						obj.put(CREATEDUSER, createdUser);
						obj.put(UPDATEDUSER, inventoryItem.getUpdatedUser());
						obj.put(PONUMBER, inventoryItem.getPoVendorId().getPurchaseOrderNumber());

						obj.put(FACILITY, inventoryItem.getFacilityId());

						List<History> listInv = historyRepository.findByBarcode(inventoryItem.getBarcode());
						if (listInv.isEmpty()) {
							obj.put("history", null);

						} else {

							obj.put("history", listInv.get(listInv.size() - 1).getHistoryDetails());
						}
						objList.add(obj);

					}

				}
			}

			response.setData(objList);

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS052.name(), EnumTypeForErrorCodes.SCUS052.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}

		return response;

	}

	@Override
	public ServiceResponse<RentalProducts> addRentalsFromWoocomerce(@Valid String rental) {
		log.info("adding rental product from woocomerce");

		ServiceResponse<RentalProducts> response = new ServiceResponse<>();
		try {
			CustomerDetails customerDetailsNew = new CustomerDetails();
			RentalProducts rentalProductsNew = new RentalProducts();
			org.json.JSONObject rentalProducts = new org.json.JSONObject(rental);
			customerDetailsNew.setName(rentalProducts.getString("name"));
			customerDetailsNew.setPhone(rentalProducts.getLong("phoneNo"));
			customerDetailsNew.setCity(rentalProducts.getString("city"));

			CustomerDetails customerDetails = customerDetailsRepo.save(customerDetailsNew);
			RentalProducts serialNumber = rentalProductsRepo.findBylastRecord();
			if (serialNumber == null) {
				String num = "1";
				String serviceId = "R" + StringUtils.leftPad(num, 4, "0");

				rentalProductsNew.setRentalId(serviceId);
			} else {

				String str = serialNumber.getRentalId();
				Long serviceId = Long.parseLong(str.substring(1));
				Long newserviceId = serviceId + 1;
				String num = newserviceId.toString();
				String newserviceId1 = "R" + StringUtils.leftPad(num, 4, "0");

				rentalProductsNew.setRentalId(newserviceId1);
			}
			rentalProductsNew.setCustomerDetailsId(customerDetails);
			rentalProductsNew.setExtension(0l);
			rentalProductsNew.setRequestedDate(LocalDate.now());
			rentalProductsNew.setDispatchStatusId(dispatchStatusRepo.getOne(5l));
			rentalProductsNew
					.setRentalServiceType(rentalServiceTypesRepo.findById(rentalProducts.getLong("service")).get());
			RentalProducts savedRentalProducts = rentalProductsRepo.save(rentalProductsNew);
			response.setData(savedRentalProducts);

		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1150.name(), EnumTypeForErrorCodes.SCUS1150.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

	@Override
	public ServiceResponse<History> addHistory(@Valid String historyDetails) {
		log.info("adding history");

		ServiceResponse<History> response = new ServiceResponse<>();
		try {

			History history = new History();
			org.json.JSONObject object = new org.json.JSONObject(historyDetails);
			history.setHistoryDetails(object.getString("history"));
			history.setBarcode(inventoryItemRepo.findByBarcode(object.getString("barcode")).getBarcode());
			History savedHistory = historyRepository.save(history);
			response.setData(savedHistory);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1150.name(), EnumTypeForErrorCodes.SCUS1150.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

	@Override
	public ServiceResponse<List<DispatchStatus>> getStatusForRentalReturns() {
		log.info("get all status for rental returns");

		ServiceResponse<List<DispatchStatus>> response = new ServiceResponse<>();
		List<DispatchStatus> list = new ArrayList<>();
		try {

			List<DispatchStatus> allstatus = dispatchStatusRepo.findAllRentalReturnStatus();

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
	public ServiceResponse<List<History>> getAllHistory(String barcode) {
		log.info("get all status for rental returns");

		ServiceResponse<List<History>> response = new ServiceResponse<>();
		try {
			List<History> listInv = historyRepository.findByBarcode(barcode);

			response.setData(listInv);
		} catch (Exception e) {
			response.setError(EnumTypeForErrorCodes.SCUS1152.name(), EnumTypeForErrorCodes.SCUS1152.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

	@Override
	public ServiceResponse<String> generatingPaymentLink(@Valid String paytmPaymentIntegration,
			CustomerDetails customerDetailsId) {
		log.info("generating Link");

		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			RestTemplate rt = new RestTemplate();
			rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			rt.getMessageConverters().add(new StringHttpMessageConverter());
			PaytmPaymentIntegration addPaytmPaymentIntegration = new PaytmPaymentIntegration();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(userName, passWord);
			HttpEntity<String> entity = new HttpEntity<>(paytmPaymentIntegration, headers);

			ResponseEntity<String> res = rt.exchange(razorApi, HttpMethod.POST, entity, String.class);
			String result = res.getBody();
			org.json.JSONObject jsonObject = new org.json.JSONObject(result);
			if (res.getStatusCode().value() != 200) {
				log.error(jsonObject.getJSONObject("error").getString("description"));
				response.setData(jsonObject.getString(jsonObject.getJSONObject("error").getString("description")));

			} else {
				org.json.JSONObject requestJsonObject = new org.json.JSONObject(paytmPaymentIntegration);

				org.json.JSONObject customerDetails = jsonObject.getJSONObject("customer_details");
				addPaytmPaymentIntegration.setCustomerId(customerDetails.getString("id"));
				addPaytmPaymentIntegration.setName(customerDetails.getString("name"));
				addPaytmPaymentIntegration.setEmail(customerDetails.getString("email"));
				addPaytmPaymentIntegration.setPhone(customerDetails.getLong("contact"));
				addPaytmPaymentIntegration.setOrderId(jsonObject.getString("order_id"));
				addPaytmPaymentIntegration.setShortUrl(jsonObject.getString("short_url"));
				addPaytmPaymentIntegration.setAmount(jsonObject.getLong("amount"));
				addPaytmPaymentIntegration.setCurrency(jsonObject.getString("currency"));
				addPaytmPaymentIntegration.setDescription(jsonObject.getString("description"));
				addPaytmPaymentIntegration.setExpireBy(jsonObject.getLong("expire_by"));
				addPaytmPaymentIntegration.setEmailNotify(requestJsonObject.getBoolean("email_notify"));
				addPaytmPaymentIntegration.setSmsNotify(requestJsonObject.getBoolean("sms_notify"));
				addPaytmPaymentIntegration.setReceipt(jsonObject.getString("receipt"));
				addPaytmPaymentIntegration.setStatus(jsonObject.getString("status"));
				addPaytmPaymentIntegration.setCustomerDetailsId(customerDetailsId);
				addPaytmPaymentIntegration.setType(jsonObject.getString("type"));
				paytmPaymentIntegrationRepo.save(addPaytmPaymentIntegration);

				response.setData(jsonObject.getString("short_url"));
			}
		} catch (HttpStatusCodeException e) {
			log.error(ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString()).getBody());
		} catch (Exception e) {

			response.setError(EnumTypeForErrorCodes.SCUS1152.name(), EnumTypeForErrorCodes.SCUS1152.errorMsg());
			log.error(utils.toJson(response.getError()), e);
		}
		return response;
	}

}
