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
  tui.province_id AS provinceCode,
  tui.city_id AS cityCode,
  tui.province_city AS provinceCity,
  tui.school AS school,
  GROUP_CONCAT(DISTINCT(tucr.name)) AS career,
  GROUP_CONCAT(DISTINCT(tud.name)) AS domain,
  GROUP_CONCAT(DISTINCT(tup.`pro_name`)) AS professional,
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
  DATE_FORMAT(FROM_UNIXTIME(tug.vip_end_time / 1000),'%Y-%m-%d') AS vipEndTime,
  tug.vip_type AS vipType,
  tug.join_type AS joinType,
  GROUP_CONCAT(DISTINCT(tuc.classification)) AS classification,
  GROUP_CONCAT(DISTINCT(tuc.classificationId)) AS classificationId,
  GROUP_CONCAT(DISTINCT(tut.tag)) AS tag,
  GROUP_CONCAT(DISTINCT(tut.tagId)) AS tagId,
  GROUP_CONCAT(DISTINCT(tud.domain_id)) AS domainid,
  GROUP_CONCAT(DISTINCT(tud.p_id)) AS domainPid,
  GROUP_CONCAT(DISTINCT(tup.pro_id)) AS proid,
  GROUP_CONCAT(DISTINCT(tup.p_id)) AS proPid,
  GROUP_CONCAT(DISTINCT(tucr.carerrId)) AS carerrId
FROM tb_user_group tug
  LEFT JOIN tb_group tg ON tug.group_id = tg.id
  LEFT JOIN tb_user_info tui ON tui.user_id = tug.user_id
  LEFT JOIN (SELECT a.classification_id AS classificationId,a.user_id userId, b.classification classification ,b.group_id groupId FROM tb_user_classification a LEFT JOIN tb_classification b ON a.classification_id = b.id) tuc ON tug.user_id = tuc.userId AND tug.group_id = tuc.groupId
  LEFT JOIN (SELECT a.tag_id AS tagId,a.user_id userId, b.tag tag, b.group_id groupId FROM tb_user_tag a LEFT JOIN tb_tag b ON a.tag_id = b.id) tut ON tug.user_id = tut.userId AND tug.group_id = tut.groupId
  LEFT JOIN (SELECT a.user_id userId,a.career_id carerrId,b.name NAME FROM tb_userinfo_career a LEFT JOIN tfw_dict b ON a.career_id = b.ID) tucr ON tug.user_id = tucr.userId
  LEFT JOIN tb_userinfo_domain tud ON tug.user_id = tud.user_id
  LEFT JOIN tb_userinfo_professional tup ON tup.user_id = tud.user_id
GROUP BY tug.id
ORDER BY tug.id DESC