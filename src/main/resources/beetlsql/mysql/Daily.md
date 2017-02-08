listByUserId
============
SELECT g.id daily_id,a.`title`,a.`cover`,t1.`audit_time`,g.`name`,t1.status FROM (
SELECT * FROM `tb_daily` d
WHERE d.`group_id` IN (
 SELECT ug.`group_id` FROM `tb_user_group` ug WHERE ug.`user_id` = #{userId}
) ORDER BY d.`audit_time` DESC
) AS t1
LEFT JOIN tb_article a 
ON a.`id` = t1.article_id
LEFT JOIN `tb_group` g
ON t1.group_id = g.`id`
GROUP BY a.`id`

simpleListByUserId
==================
SELECT
    ug.`group_id` as id,
    g.`name`
FROM 
    `tb_user_group` ug 
LEFT JOIN
    `tb_group` g 
ON
    ug.`group_id` = g.`id`
WHERE 
    ug.`user_id` = #{userId}