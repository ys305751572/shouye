listByUserId
============
SELECT a.`id`,a.`title`,a.`cover`,mi.`name`,mi.`id` maganize_id,m.`audit_time`,mi.`status`
FROM `tb_magazine_info` mi 
LEFT JOIN `tb_magazine` m 
ON mi.id = m.`magazine_id`
LEFT JOIN tb_article a
ON a.`id` = m.`article_id`
 
WHERE mi.`id` IN (
SELECT * FROM 
(SELECT ms.`magazine_id` id FROM `tb_magazine_subscribe` ms WHERE ms.`user_id` = #{userId}
UNION ALL
SELECT mi.id FROM `tb_userinfo_domain` ud LEFT JOIN 
`tb_magazine_info` mi ON ud.domain_id = mi.domain_id WHERE ud.user_id = #{userId} AND mi.id IS NOT NULL)  AS t1 GROUP BY t1.id
)  AND (mi.status = 2 or mi.status = 4)


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