
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
order by wi.wishlist_item_id desc