list
===
select * from tb_magazine_subscribe

listByUserId
============
SELECT * from tb_magazine_subscribe ms
LEFT JOIN tb_magazine_info mi
ON ms.magazine_id = mi.id 
WHERE mi.user_id = #{userId}

SimpleListByUserId
==================
SELECT mi.id,mi.name from tb_magazine_subscribe ms
LEFT JOIN tb_magazine_info mi
ON ms.magazine_id = mi.id 
WHERE mi.user_id = #{userId}