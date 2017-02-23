list
====
SELECT 
 a.`id`,
 a.`title`,
 a.`cover`,
 di.`NAME` typename,
 a.`reading_quantity`,
 a.`interest_quantity`,
 a.`forwarding_quantity`,
 a.`create_time`,
 d.`audit_time`,
 d.`status`,
 a.`from_type`,
 a.reason,
 a.`article_type`,
 CASE
 WHEN  a.from_type = 1 THEN
 (SELECT ui.username `name` FROM tb_user_info ui WHERE ui.user_id = a.from_id)
 WHEN a.from_type = 2 THEN
 (SELECT g.name FROM tb_group g WHERE g.id = from_id)
 END AS `author`
FROM tb_daily d 
LEFT JOIN tb_article a
ON d.`article_id` = a.`id`
LEFT JOIN `tfw_dict` di
ON a.`article_type` = di.`ID`
