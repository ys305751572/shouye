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
mi.domain_pid,mi.domain_id,mi.desc
FROM `tb_magazine_info`  mi
LEFT JOIN tb_group g 
ON mi.`group_id` = g.`id`
LEFT JOIN tb_magazine_subscribe ms
ON mi.`id` = ms.`magazine_id` 
WHERE mi.id = #{id}

listById
========
SELECT t1.id,a.`title`,a.`id` articleId, IFNULL(a.`cover`,'') cover
FROM 
(
SELECT * FROM `tb_article_show` as1 
WHERE as1.`from_id` = #{fromId}
AND as1.`from_type` = 4
AND FROM_UNIXTIME(as1.`create_time`/1000,'%Y%m%d') = #{time}
ORDER BY as1.`create_time` DESC 
) AS t1
LEFT JOIN `tb_article` a 
ON t1.article_id = a.id 
 GROUP BY t1.article_id LIMIT 4

