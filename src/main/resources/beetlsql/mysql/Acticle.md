list
===
select * from tb_article

listByUserId
============
SELECT 
	as1.`id`,
	as1.`article_id`,
	a.`title`,
	a.`cover`,
	as1.`create_time` as push_time,
	a.create_time,
	as1.`type`,
    d.`NAME` AS typename,
	ui.`username`,
	ui2.`username` sharename
FROM 
	`tb_article_show` as1 
LEFT JOIN 
	tb_article a 
ON 
	as1.`article_id` = a.`id`
LEFT JOIN
	`tfw_dict` d
ON
	a.`article_type` = d.`ID`
LEFT JOIN
	tb_user_info ui 
ON 
	a.`from_id` = ui.`user_id`
LEFT JOIN
    	tb_user_info ui2 
    ON 
    	as1.`from_id` = ui2.`user_id`
WHERE
	as1.`to_id` = #{userId}
	
findById
========
SELECT a.`id`,a.`title`,a.`content`, a.`cover`,d.`NAME` typename, ui.`username` ,a.create_time push_time
FROM `tb_article` a 
LEFT JOIN `tfw_dict` d ON a.`article_type` = d.`id`
LEFT JOIN `tb_user_info` ui ON ui.`user_id` = a.`from_id`
WHERE a.`id` = #{id}

publishListByUserId
===================
SELECT a.id,a.`title`,a.`content`,a.`cover`,a.`forwarding_quantity`,
a.`interest_quantity`,a.`reading_quantity`,a.create_time,ui.`username`,
d.`NAME`,a.
FROM tb_article a 
LEFT JOIN tb_user_info ui 
ON a.`from_id` = ui.`user_id` 
LEFT JOIN tfw_dict d
ON a.`article_type` = d.`ID`
WHERE a.`from_id` = #{userId} AND a.`from_type` = 1

interestListByUserId
====================
SELECT 
	as1.`id`,
	as1.`article_id`,
	a.`title`,
	a.`cover`,
	as1.`create_time` AS push_time,
	as1.is_intereste,
	a.create_time,
	as1.`type`,
    d.`NAME` AS typename,
	ui.`username`
FROM 
	`tb_article_show` as1 
LEFT JOIN 
	tb_article a 
ON 
	as1.`article_id` = a.`id`
LEFT JOIN
	`tfw_dict` d
ON
	a.`article_type` = d.`ID`
LEFT JOIN
	tb_user_info ui 
ON 
	a.`from_id` = ui.`user_id`
WHERE
	as1.`to_id` = #{userId}
AND
	as1.`is_intereste`= #{is_Intereste}