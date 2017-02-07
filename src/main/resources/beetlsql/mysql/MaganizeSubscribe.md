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
SELECT mi.id,mi.name FROM tb_magazine_subscribe ms
LEFT JOIN tb_magazine_info mi
ON ms.magazine_id = mi.id 
WHERE ms.user_id =31
UNION 
SELECT mi.id,mi.`name` FROM `tb_magazine_info` mi WHERE mi.`domain_id` IN (
   SELECT ud.`domain_id` FROM `tb_userinfo_domain` ud WHERE ud.`user_id` = #{userId}
)