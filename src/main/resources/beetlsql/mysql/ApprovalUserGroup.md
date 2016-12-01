list
===
SELECT
  tua.id AS id,
  tua.from_user_id AS fromUserId,
  tua.to_user_id AS toUserId,
  tua.group_id AS groupId,
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
  tug.vip_type AS vipType,
  tua.validate_info AS validateInfo,
  tua.status AS approvalStatus,
  DATE_FORMAT(FROM_UNIXTIME(tua.create_time/1000),'%Y-%m-%d') AS createTime
FROM tb_approval_user_group tua
LEFT JOIN tb_user_group tug ON tua.group_id = tug.group_id AND tua.from_user_id = tug.user_id
LEFT JOIN tb_group tg ON tua.group_id = tg.id
LEFT JOIN tb_user_info tui ON tui.user_id = tua.from_user_id
ORDER BY tua.id DESC

