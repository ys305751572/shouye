list
====
SELECT a.id articleId,a.`title`,a.`cover`,a.`create_time`,g.`id` groupId,g.`name` groupname 
FROM `tb_article_show` as1 
LEFT JOIN tb_article a
ON as1.`article_id` = a.`id`
LEFT JOIN tb_group g
ON as1.`from_id` = g.`id`
WHERE as1.`to_id` = #{userId} AND a.`article_type` = #{articleType}