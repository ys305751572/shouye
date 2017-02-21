list
===
select * from tb_article

listByUserId
============
SELECT 
   t1.id,
   a.`id` articleId,
   t1.`from_id` authorId,
   t1.`from_type` authorType,
   t1.name author,
   a.`title`,
   IFNULL(a.`cover`,'') cover,
   t1.create_time pushTime,
   d.`NAME` typename,
   t1.type,
   ui2.username sharename
FROM 
(
SELECT as1.id,as1.article_id,ui.`username` `name`,as1.`create_time`,as1.type,as1.share_id,as1.from_id,as1.from_type FROM `tb_article_show` as1
LEFT JOIN tb_user_info  ui
ON as1.`from_id` = ui.`user_id`
WHERE as1.`from_type` = 1 AND as1.`to_id` = #{userId} AND as1.`is_intereste` = 1

UNION ALL

SELECT as1.id,as1.article_id,g.name,as1.`create_time`,as1.type,as1.share_id,as1.from_id,as1.from_type FROM tb_article_show as1
LEFT JOIN tb_group g
ON as1.`from_id` = g.id 
WHERE as1.`from_type` = 2 AND as1.type != 8 AND as1.`to_id` = #{userId} AND as1.`is_intereste` = 1

UNION ALL

SELECT as1.id,as1.article_id,g.name,as1.`create_time`,as1.type,as1.share_id,as1.from_id,as1.from_type
FROM tb_article_show as1
LEFT JOIN tb_group g
ON as1.`from_id` = g.id 
LEFT JOIN tb_activity_interest ai
ON ai.article_id = as1.article_id and ai.user_id = #{userId}
WHERE as1.`from_type` = 2 AND as1.type = 8
AND ai.id IS NULL

UNION ALL

SELECT as1.id,as1.article_id,mi.name,as1.`create_time`,as1.type,as1.share_id,as1.from_id,as1.from_type FROM tb_article_show as1
LEFT JOIN tb_magazine_info mi
ON as1.`from_id` = mi.id
WHERE as1.`from_type` = 4 AND (as1.`to_id` = #{userId} OR as1.`from_id` IN (#{maganzineIds}))AND as1.`is_intereste` = 1

UNION ALL

SELECT as1.id,as1.article_id,'官方' AS `name`,as1.`create_time`,as1.type,as1.share_id,as1.from_id,as1.from_type FROM tb_article_show as1
WHERE as1.`from_type` = 3 AND as1.`to_id` = #{userId} AND as1.`is_intereste` = 1
) AS t1
LEFT JOIN `tb_article` a
ON t1.article_id = a.`id`
LEFT JOIN `tfw_dict` d
ON a.`article_type` = d.`ID`
LEFT JOIN `tb_user_info` ui2
ON t1.share_id = ui2.user_id
ORDER BY t1.create_time DESC
	
findById
========
SELECT a.`id`,a.`title`,a.`content`, a.`cover`,d.`NAME` typename,
a.`from_id`,a.`from_type`,a.create_time push_time
FROM `tb_article` a
LEFT JOIN `tfw_dict` d ON a.`article_type` = d.`id`
WHERE a.`id` = #{id}

publishListByUserId
===================
SELECT a.id,a.`title`,a.`content`,a.`cover`,a.`forwarding_quantity`,
a.`interest_quantity`,a.`reading_quantity`,a.create_time,ui.`username`,
d.`NAME` typename
FROM tb_article a 
LEFT JOIN tb_user_info ui 
ON a.`from_id` = ui.`user_id` 
LEFT JOIN tfw_dict d
ON a.`article_type` = d.`ID`
WHERE a.`from_id` = #{userId} AND a.`from_type` = 1
ORDER BY a.create_time DESC

interestListByUserId
====================
SELECT 
	as1.`id`,
	as1.`article_id` articleId,
	a.`title`,
	a.`cover`,
	as1.`create_time` AS pushTime,
	as1.is_intereste,
	a.create_time createTime,
	as1.`type`,
    d.`NAME` AS typename,
	ui.`username`,
	as1.`from_id` authorId,
	as1.`from_type` authorType
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
	
interestUserListByArticleId
===========================
SELECT
    aa.`article_id` articleId,
    ui.user_id AS userId,
    ui.username,
    ui.avater,
    ui.province_id province,
    ui.city_id city,
    ui.school,
    ui.province_city provinceCity,
    ui.domain,
    ui.key_word keyWord,
    ui.organization,
    ui.professional,
    ui.per,
    ui.career,
    ui.desc,
    ua.status,
    ua.from_user_id,
    ua.to_user_id,
    ui.org_is_open
FROM `tb_article_show` aa
LEFT JOIN  tb_user_info ui
ON aa.to_id = ui.`user_id` AND aa.is_intereste = 2 
LEFT JOIN tb_user_approval ua ON ((ui.user_id = ua.from_user_id OR ui.user_id = ua.to_user_id)
AND (ua.from_user_id = #{userId} OR ua.to_user_id = #{userId}))
GROUP BY userId