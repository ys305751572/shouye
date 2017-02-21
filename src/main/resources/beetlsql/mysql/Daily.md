listByUserId
============
SELECT g.id daily_id,a.`title`,a.`cover`,t1.`audit_time`,g.`name`,ge.`daily_status` `status` FROM (
SELECT * FROM `tb_daily` d
WHERE d.`group_id` IN (
 SELECT ug.`group_id` FROM `tb_user_group` ug WHERE ug.`user_id` = #{userId}
) ORDER BY d.`audit_time` DESC
) AS t1
LEFT JOIN tb_article a 
ON a.`id` = t1.article_id
LEFT JOIN `tb_group` g
ON t1.group_id = g.`id`
LEFT JOIN `tb_group_extend` ge
ON g.`id` = ge.`group_id`
GROUP BY g.`id`

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
    
listById
========
SELECT a.id article_id, d.id daily_id,a.`title`,d.`audit_time`,g.`name`,
a.cover
FROM tb_daily d 
LEFT JOIN tb_article a 
ON d.`article_id` = a.`id`
LEFT JOIN tb_group g 
ON d.`group_id` = g.`id`
WHERE d.group_id = #{id}


detail
======
SELECT g.`id` daily_id,g.`name`,ge.`daily_desc`,ge.`daily_cover` FROM tb_group g
LEFT JOIN tb_group_extend ge
ON g.`id` = ge.`group_id`
WHERE g.`id` = #{id} 