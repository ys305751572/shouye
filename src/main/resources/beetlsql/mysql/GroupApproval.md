list
===
SELECT
  tga.id AS id,
  tga.group_id AS groupId,
  tga.user_id AS userId,
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
  tui.organization AS organization,
  tui.org_type AS orgType,
  org.name AS orgName,
  org.num AS orgId,
  tui.product_type AS productType,
  tui.industry_ranking AS industryRanking,
  tui.qualification AS qualification,
  tui.key_word AS keyWord,
  tga.validate_info AS validateInfo,
  tga.status AS approvalStatus,
  tga.type AS approvalType,
  tga.match_type AS matchType,
  tga.target_type AS targetType,
  tga.paied AS paied,
  o.status ostatus,
  DATE_FORMAT(FROM_UNIXTIME(tga.create_time/1000),'%Y-%m-%d') AS createTime
FROM
  tb_group_approval tga
LEFT JOIN tb_group tg ON tga.group_id = tg.id
LEFT JOIN tb_user_info tui ON tui.user_id = tga.user_id
LEFT JOIN (SELECT id,num,NAME FROM tfw_dict WHERE CODE=907) org ON tui.org_type = org.id
LEFT JOIN tb_order o ON tga.`id` = o.`ga_id`
ORDER BY tga.id DESC
