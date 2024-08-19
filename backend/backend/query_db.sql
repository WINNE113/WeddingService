
-- Find service is exit in wishlist
select w.wishlist_name as wishListName, wi.wishlist_item_id as wishListItem, wi.service_id as serviceId, w.user_id as userId from wishlists as w
inner join wishlist_items as wi
on w.wishlist_id = wi.wishlist_id
where w.wishlist_name = 'service' and w.user_id = '08ce75edefd446159f4f590cb78977ca' and wi.service_id = 1;


Select s.id, s.title, s.address, s.created_date as createdDate, s.price, s.image, wi.wishlist_item_id as wishListItemId
from services as s
inner join wishlist_items as wi on s.id = wi.service_id
inner join wishlists as w on w.wishlist_id = wi.wishlist_id
where w.user_id = '08ce75edefd446159f4f590cb78977ca'  and w.wishlist_name LIKE 'service'
group by s.id, s.title, s.address, s.created_date, s.price, s.image, wi.wishlist_item_id 
order by wi.wishlist_item_id desc;

  
  
-- Detail service
	Select s.id, li.image_url_list as imagesURL, sa.name as nameAlb from services as s
                    inner join service_albums as sa on s.id = sa.service_id
                    inner join service_album_entity_image_url_list as li on sa.id = li.service_album_entity_id
                    where s.id= 1 ;
  
ALTER TABLE `wedding_db`.`services`
    CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
    CHANGE COLUMN `title` `title` VARCHAR(255) NOT NULL AFTER `id`,
    CHANGE COLUMN `address` `address` VARCHAR(255) NULL DEFAULT NULL AFTER `title`,
    CHANGE COLUMN `image` `image` VARCHAR(255) NULL DEFAULT NULL AFTER `address`,
    CHANGE COLUMN `price` `price` DECIMAL(38,2) NULL DEFAULT NULL AFTER `image`,
    CHANGE COLUMN `information` `information` TEXT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NULL DEFAULT NULL AFTER `price`,
    CHANGE COLUMN `supplier_id` `supplier_id` BIGINT NULL DEFAULT NULL AFTER `information`,
    CHANGE COLUMN `service_type_id` `service_type_id` BIGINT NULL DEFAULT NULL AFTER `supplier_id`;


-- Query select all supplier (conditional)
Select * from supplier;
UPDATE `wedding_db`.`supplier` SET `is_deleted` = 'false' WHERE (`id` = '1');

-- Get services by supplier
select * from services as s 
inner join supplier as sup on s.supplier_id = sup.id
inner join users as u on u.id = sup.user_id;

-- Get service type by supplier

SELECT 
    st.id as serviceTypeId, 
    st.name AS serviceTypeName,
    COUNT(*) AS serviceCount
FROM supplier s JOIN services sv ON s.id = sv.supplier_id
JOIN service_types st ON sv.service_type_id = st.id
where s.id = 1
GROUP BY st.id, st.name
ORDER BY st.id, st.name;

-- Get service by service type 

SELECT 
    s.id, s.title, s.address, s.created_date as createdDate, s.image
FROM services s
JOIN supplier sup ON s.supplier_id = sup.id
JOIN service_types st ON s.service_type_id = st.id
WHERE sup.id = 1 AND s.service_type_id = 1 AND s.status = "APPROVED" AND s.is_deleted = false;


SELECT 
    s.id, s.title, s.address, s.created_date as createdDate, s.image
FROM services s inner join supplier sup on s.supplier_id = sup.id
where s.service_type_id = 1 and sup.id = 1;

Select s.id, s.title, s.image, s.address, s.is_deleted, s.status, s.service_type_id, s.created_date, sup.name, sup.id from services s inner join supplier as sup on s.supplier_id = sup.id where s.is_deleted = false AND s.supplier_id = 1 AND service_type_id = 1 group by s.id, s.title, s.image, s.address, s.is_deleted, s.status, s.service_type_id, s.created_date, sup.name, sup.id order by s.created_date DESC LIMIT 10 OFFSET 0;



-- Get total payment package service by month

SELECT Month(t.purchase_date) as month, SUM(sp.price) as tolalPrice
FROM transaction as t inner join service_package as sp
where t.package_id = sp.id
GROUP BY MONTH(t.purchase_date)
Order by month(t.purchase_date);


Select month(pt.tran_date) as month, sum(p.paid_amount) as totalPrice
            From payment as p inner join payment_transaction as pt
            where p.id = pt.payment_id and pt.tran_status = 0
            Group by Month(pt.tran_date)
            Order by month(pt.tran_date);
            
Select b.id, b.name as nameCustomer, b.email, b.created_date as createdDate, b.phone_number as phoneNumber, b.note, b.service_id as serviceId, s.title as titleService from bookings as b
join services as s on b.service_id = s.id
join supplier as sup on s.supplier_id = sup.id
where sup.id = 2;


WITH RankedServices AS (
 SELECT 
  s.id,
s.title, 
s.image, 
    s.address,
  s.created_date as  createdDate, 
  t.purchase_date as purchaseDate, sup.id as supplierId,
ROW_NUMBER() OVER (PARTITION BY s.supplier_id ORDER BY s.created_date DESC) AS rn
                FROM services AS s
               INNER JOIN supplier AS sup ON s.supplier_id = sup.id
                    INNER JOIN transaction AS t ON t.supplier_id = sup.id
                     WHERE t.package_id = 1
                        AND s.status = 'APPROVED'
                       AND s.is_deleted = FALSE
                    )
                    SELECT *
                    FROM RankedServices
                    WHERE rn <= 5
                    ORDER BY  supplierId,  purchaseDate DESC

