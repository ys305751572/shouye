list
===
select * from tb_article_shielding

listByUserId
============
select as1.user_id userId, as1.* from tb_article_shielding as1

findUsers
=========
SELECT
        ui.`user_id` AS id,
        ui.`username` AS `name`,
        IFNULL(ui.`avater`,'') AS avater,
        ui.`province_city` AS province_city ,
        ui.`per` AS `special1`,
        ui.`domain` AS `special2`,
        ui.`key_word` AS `special3`,
        1 AS `type`
        FROM 
        tb_user_info ui
        WHERE 
        ui.user_id IN (#{ids})
        
findGroups
==========
  SELECT 
        g.`id` AS id,
        g.`name` AS `name`,
        IFNULL(g.`avater`,'') AS avater,
        g.`province_city` AS province_city,
        g.`member_count` AS `special1`,
        g.`targat` AS `special2`,
        '' AS `special3`,
        2 AS `type`
FROM
    tb_group g
WHERE
    g.id IN (#{ids})
