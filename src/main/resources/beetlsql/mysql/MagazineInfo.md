list
====
SELECT mi.`id`,mi.`name`,mi.`cover`,mi.`status`,g.`name` groupname,ms.`magazine_id`,
mi.domain_pid,mi.domain_id
FROM `tb_magazine_info`  mi
LEFT JOIN tb_group g 
ON mi.`group_id` = g.`id`
LEFT JOIN tb_magazine_subscribe ms
ON mi.`id` = ms.`magazine_id` AND ms.`user_id` = #{userId}

detail
======
SELECT mi.`id`,mi.`name`,mi.`cover`,mi.`status`,g.`name` groupname,ms.`magazine_id`,
mi.domain_pid,mi.domain_id
FROM `tb_magazine_info`  mi
LEFT JOIN tb_group g 
ON mi.`group_id` = g.`id`
LEFT JOIN tb_magazine_subscribe ms
ON mi.`id` = ms.`magazine_id` AND mi.id = #{id}

listById
========
SELECT 
  a.id,a.`title`,a.`cover`
FROM `tb_magazine` m 
LEFT JOIN `tb_article` a
ON m.`article_id` = a.`id`
WHERE m.`magazine_id` = #{id}
AND FROM_UNIXTIME(m.`audit_time`/1000,'%Y%m%d')  = #{time}
ORDER BY m.`audit_time` 
DESC LIMIT 4