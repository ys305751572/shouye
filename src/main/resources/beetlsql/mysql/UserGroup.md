list
===
SELECT
  tug.id AS id,
  tug.user_id AS userId,
  tug.group_id AS groupId,
  tui.avater AS avater,
  tui.username AS userName,
  tui.gender AS gender,
  tui.age AS age,
  tui.province_city AS provinceCity,
  tui.school AS school,
  tui.career AS career,
  tui.domain AS domain,
  tui.professional AS professional,
  tui.zy AS zy,
  tui.sc AS sc,
  tui.zl AS zl,
  tui.zy2 AS zy2,
  tg.name AS groupName,
  tg.type AS groupType,
  tui.product_type AS productType,
  tui.industry_ranking AS industryRanking,
  tui.qualification AS qualification,
  tui.key_word AS keyWord,
  tui.mobile AS mobile,
  DATE_FORMAT(FROM_UNIXTIME(tug.create_time / 1000),'%Y-%m-%d') AS createTime,
  DATE_FORMAT(FROM_UNIXTIME(tui.vip_end_time / 1000),'%Y-%m-%d') AS vipEndTime,
  tui.vip_type AS vipType,
  tui.join_type AS joinType,
  GROUP_CONCAT(tuc.classification) AS classification,
  GROUP_CONCAT(tut.tag) AS tag
FROM tb_user_group tug
  LEFT JOIN tb_group tg ON tug.group_id = tg.id
  LEFT JOIN tb_user_info tui ON tui.user_id = tug.user_id
  LEFT JOIN (SELECT a.user_id userId, b.classification classification FROM tb_user_classification a LEFT JOIN tb_classification b ON a.classification_id = b.id) tuc ON tug.user_id = tuc.userId
  LEFT JOIN (SELECT a.user_id userId, b.tag tag FROM tb_user_tag a LEFT JOIN tb_tag b ON a.tag_id = b.id) tut ON tug.user_id = tut.userId
GROUP BY tug.id