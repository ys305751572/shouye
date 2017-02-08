list
====
SELECT mi.`id`,mi.`name`,mi.`cover`,mi.`status`,g.`name` groupname,ms.`magazine_id`,
mi.domain_pid,mi.domain_id
FROM `tb_magazine_info`  mi
LEFT JOIN tb_group g 
ON mi.`group_id` = g.`id`
LEFT JOIN tb_magazine_subscribe ms
ON mi.`id` = ms.`magazine_id` AND ms.`user_id` = #{userId}