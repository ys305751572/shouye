list
===
SELECT
  tui.id AS id,
  tui.user_id AS userId,
  tui.avater AS avater,
  tul.login_username AS accountId,
  tui.username AS userName,
  tui.gender AS gender,
  tui.age AS age,
  tfd.num AS ageId,
  tui.province_city AS provinceCity,
  tui.school AS school,
  tui.career AS career,
  tui.domain AS domain,
  tui.professional AS professional,
  tui.zy AS proficient,
  tui.sc AS adept,
  tui.zl AS seniority,
  tui.zy2 AS resources,
  tui.organization AS organization,
  tui.org_type AS orgType,
  tui.product_type AS productType,
  tui.industry_ranking AS industryRanking,
  tui.qualification AS qualification,
  tui.key_word AS keyWord,
  GROUP_CONCAT(tug._name) AS groupName,
  tug._type AS groupType,
  tui.group_status AS groupStatus,
  tui.vip_type AS vipType,
  tus.organization_num AS organizationNum,
  tus.friend_num AS friendNum,
  tus.acquaintances_num AS acquaintancesNum,
  tus.activity_apply_num AS activityApplyNum,
  tus.activity_sign_num AS activitySignNum,
  (tus.activity_sign_num / tus.activity_apply_num) AS activityParticipate,
  tus.content_num AS contentNum,
  tui.mobile AS mobile,
  tul.create_time AS createTime,
  tul.last_login_time AS lastLoginTime,
  tul.status AS status,
  GROUP_CONCAT(tup.pro_id) AS proId,
  GROUP_CONCAT(tuc.career_id) AS careerId,
  GROUP_CONCAT(tud.domain_id) AS domainId
FROM tb_user_info tui
  LEFT JOIN tb_userinfo_statistical tus ON tui.id = tus.user_id
  LEFT JOIN (SELECT a.user_id AS user_id, b.id AS id, b.name AS _name,b.type AS _type FROM tb_user_group a LEFT JOIN tb_group b ON a.group_id = b.id) tug ON tui.id = tug.user_id
  LEFT JOIN tb_user_login tul ON tui.user_id = tul.id
  LEFT JOIN tb_userinfo_career tuc ON tui.id = tuc.user_id
  LEFT JOIN tb_userinfo_domain tud ON tui.id = tud.user_id
  LEFT JOIN tb_userinfo_professional tup ON tui.id = tup.user_id
  LEFT JOIN (SELECT id,num,NAME FROM tfw_dict WHERE CODE=904) tfd ON tui.age_interval_id = tfd.id
GROUP BY tui.id

listPage
====
select 
    ui.user_id AS userId,
    ui.username,
    ui.avater,
    ui.province_id province,
    ui.city_id city,
    ui.school,
    ui.province_city proviceCity,
    ui.domain,
    ui.key_word keyWord,
    ui.organization,
    ui.per,
    ui.career,
    ui.desc,
    ua.status,
    ua.from_user_id,
    ua.to_user_id,
    @if(!isEmpty(groupId)) {
        ug.group_id groupId,
    @}
    i.status istatus
from tb_user_info ui
LEFT JOIN tb_user_approval ua ON ((ui.user_id = ua.from_user_id OR ui.user_id = ua.to_user_id) AND (ua.from_user_id = #{userId} OR ua.to_user_id = #{userId})) 
@if(!isEmpty(groupId)){
    RIGHT JOIN tb_user_group ug ON  (ug.user_id = ui.user_id and ug.group_id = #{groupId})
@}
@if(!isEmpty(history)) {
    RIGHT JOIN tb_group_user_record gur ON (ui.user_id = gur.to_user_id and gur.group_id = #{groupId} and gur.user_id = #{userId})
@}
@if(!isEmpty(domain)) {
    RIGHT JOIN tb_userinfo_domain ud ON (ui.user_id = ud.user_id AND ud.domain_id = #{domain})
@}
LEFT JOIN tb_interest_user i ON (i.to_user_id = ui.user_id AND i.status = 0)
GROUP BY userId

userInfoDetail
==============
select 
    ui.user_id userId,
    ui.username,
    ui.avater,
    ui.province_id province,
    ui.city_id city,
    ui.school,
    ui.province_city proviceCity,
    ui.domain,
    ui.key_word keyWord,
    ui.organization,
    ui.per,
    ui.career,
    ui.professional,
    ui.desc
from tb_user_info ui 
where ui.user_id = #{userId}
