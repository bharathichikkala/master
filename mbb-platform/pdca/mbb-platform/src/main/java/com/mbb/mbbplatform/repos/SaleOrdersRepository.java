package com.mbb.mbbplatform.repos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mbb.mbbplatform.domain.SaleOrders;

@Repository
public interface SaleOrdersRepository extends JpaRepository<SaleOrders, Long> {

	@Query(value = "SELECT * FROM mbbinventory.saleorders WHERE saleorders.shipping_package_status_code =?1 And saleorders.dispatch_date < ?2 ", nativeQuery = true)
	public List<SaleOrders> findByShippingPackageStatusCodeAndDispatchDate(String shippingPackageStatusCode,
			LocalDateTime dispatchDate);
	
	List<SaleOrders> findByZohocreatedDate(LocalDate zohocreatedDate);


	public List<SaleOrders> findByDisplayOrderCode(String displayOrderCode);
	


	public SaleOrders findBySaleOrderItemCode(String saleOrderItemCode);

	public SaleOrders findBySaleOrderItemCodeAndDisplayOrderCode(String saleOrderItemCode, String invoiceNumber);

	@Query(value = "SELECT * FROM mbbinventory.saleorders where channel_name = 'AMAZON_IN' AND shipping_provider in('ATS',' ') AND sale_order_item_status!='FULFILLABLE'", nativeQuery = true)
	public List<SaleOrders> findAllByChannelName();

	@Query(value=" SELECT * FROM mbbinventory.saleorders WHERE saleorders.channel_name='FLIPKART'AND sale_order_item_status!='FULFILLABLE'  ", nativeQuery = true)
	public List<SaleOrders> findByChannelName();

	public List<SaleOrders> findAllByDisplayOrderCode(String orderNumber);

}
