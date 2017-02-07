listByUserId
============
SELECT * FROM
(SELECT * FROM `tb_article_show` as1 WHERE as1.`type` = 4 AND as1.`to_id` IN 
(SELECT ug.`group_id` FROM `tb_user_group` ug WHERE ug.`user_id` = #{userId}) ORDER BY as1.create_time DESC)
AS a1
GROUP BY a1.`to_id` 