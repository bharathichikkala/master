CREATE SCHEMA `pdc_automation` ;

CREATE TABLE `pdc_automation`.`product_information` (
  `orderId` INT NOT NULL,
  `date` DATE NOT NULL,
  `source` VARCHAR(45) NOT NULL,
  `product_name` VARCHAR(45) NOT NULL,
  `product_quantity` INT NOT NULL,
  `product_warranty` TINYINT NOT NULL,
  `msn` VARCHAR(45) NULL,
  `mode_of_payment` VARCHAR(45) NOT NULL,
  `courier_provider_name` VARCHAR(45) NOT NULL,
  `shipment_price` DECIMAL NOT NULL,
  `cod_through` VARCHAR(45) NOT NULL,
  `pinCode` VARCHAR(45) NOT NULL,
  `customer_image` TINYINT NOT NULL,
  `mbb_quality_tag` TINYINT NOT NULL,
  `bottom_instructions` VARCHAR(45) NOT NULL,
  `pack` TINYINT NOT NULL,
  `big_items` TINYINT NULL,
  `forms_names` VARCHAR(45) NULL,
  `dispatched` TINYINT NOT NULL,
  `service_numbers_sent` TINYINT NOT NULL,
  `software_links_sent` TINYINT NOT NULL,
  `invoice_to_accountant` TINYINT NOT NULL,
  PRIMARY KEY (`orderId`));