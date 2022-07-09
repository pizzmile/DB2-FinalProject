# Materialized views

The following materialized views have been designed:
1. `TotalSalesValue_mv`: total value of sales per package with and without the optional products;
2. `PurchasesPerPackage_mv`: number of total purchases per package;
3. `PurchasesPerPackageValidity_mv`: number of total purchases per package and validity period;
4. `BestSellerProducts_mv`: bestseller optional product, i.e. the optional product with the greatest value of sales 
across all the sold service packages.Number of total purchases per package;
5. `AvgProductsSold_mv`: average number of optional products sold together with each service package;
6. `InsolventCustomersReport_mv`: list of insolvent users, suspended orders and alerts;


## Triggers

### Package creation

**CompatibleValidities**

1. Insert new default row (`purchases=0`) in `PurchasesPerPackageValidity_mv`
````
CREATE TRIGGER `TelcoDB`.`CompatibleValidities_AFTER_INSERT`
AFTER INSERT ON `TelcoDB`.`CompatibleValidities`
FOR EACH ROW
BEGIN
    CALL defaultPurchasesPerPackageValidity(NEW.idPackage, NEW.idValidity);
END
````

Test:
1. ✅

**Package**

1. Insert new default (``value=0``) row in TotalSalesValue_mv
2. Insert new default (``purchases=0``) row in PurchasesPerPackage_mv
3. Insert new default (``average=0``) row In AvgProductsSold_mv
````
CREATE TRIGGER `TelcoDB`.`Package_AFTER_INSERT`
AFTER INSERT ON `TelcoDB`.`Package`
FOR EACH ROW
BEGIN
    INSERT INTO TotalSalesValue_mv (idPackage, `name`, completeValue, partialValue) 
    VALUES (NEW.idPackage, NEW.`name`, 0, 0);
    
    INSERT INTO PurchasesPerPackage_mv (idPackage, `name`, purchases) 
    VALUES (NEW.idPackage, NEW.`name`, 0);
    
    INSERT INTO AvgProductsSold_mv (idPackage, `name`, avgNumOfProducts)
    VALUES (NEW.idPackage, NEW.`name`, 0);
END
````

Test:
1. ✅
2. ✅
3. ✅

### Order creation

**Order**

If the new order has been paid then proceed as follows:
1. update the tuple `TotalSalesValue_mv` for the package in order with the new value of sales;
2. update the tuple `PurchasesPerPackage_mv` for the package in order with the new number of purchases;
3. update the tuple `PurchasesPerPackageValidity_mv` for the couple ``<package, validity`` in order with the new number
of purchases;
4. update `BestSellerProduct_mv` by recomputing the new current bestseller product;
5. update the tuple `AvgProductsSold_mv` for the package in order with the new average of products sold with it.

If the new order has not been paid yet then:
6. inset a row into `InsolventCustomersReport_mv` for the current triple `<customer, order, alert>` and update the 
already existing tuples for that customer.
````
CREATE TRIGGER `TelcoDB`.`Order_AFTER_INSERT`
AFTER INSERT ON `TelcoDB`.`Order`
FOR EACH ROW
BEGIN
	IF (NEW.paid = true) THEN
		CALL updateTotalSalesValue(NEW.idPackage);
        CALL updatePurchasesPerPackage(NEW.idPackage);
        CALL updatePurchasesPerPackageValidity(NEW.idPackage, NEW.idValidity);
        CALL updateBestSellerProduct(NEW.idOrder);
        CALL updateAvgProductsSold(NEW.idPackage);
	ELSE
		CALL updateInsolventCustomersReport(NEW.idCustomer);
	END IF;
END
````

Test:
1. ✅
2. ✅
3. ⚠️
4. ⚠️
5. ⚠️
6. ✅

### Order update

**Order**
If the order status has changed from unpaid (`false`) to paid (`true`), then 
1. update the tuple `TotalSalesValue_mv` for the package in order with the new value of sales;
2. update the tuple `PurchasesPerPackage_mv` for the package in order with the new number of purchases;
3. update the tuple `PurchasesPerPackageValidity_mv` for the couple ``<package, validity`` in order with the new number
   of purchases;
4. update `BestSellerProduct_mv` by recomputing the new current bestseller product;
5. update the tuple `AvgProductsSold_mv` for the package in order with the new average of products sold with it;
6. delete the tuple in `InsolventCustomersReport_mv` for the current order.
```
CREATE TRIGGER `TelcoDB`.`Order_AFTER_UPDATE`
AFTER UPDATE ON `TelcoDB`.`Order`
FOR EACH ROW
BEGIN
    IF (OLD.paid = false AND NEW.paid = true) THEN
        CALL updateTotalSalesValue(NEW.idPackage);
        CALL updatePurchasesPerPackage(NEW.idPackage);
        CALL updatePurchasesPerPackageValidity(NEW.idPackage, NEW.idValidity);
        CALL updateBestSellerProduct(NEW.idOrder);
        CALL updateAvgProductsSold(NEW.idPackage);
        
        DELETE FROM InsolventCustomersReport_mv ICR WHERE ICR.idOrder = NEW.idOrder;
    END IF;
END
````