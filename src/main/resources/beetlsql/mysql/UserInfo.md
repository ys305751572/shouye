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
  GROUP_CONCAT(DISTINCT(tug._name)) AS groupName,
  tug._type AS groupType,
  tui.group_status AS groupStatus,
  GROUP_CONCAT(DISTINCT(tug.vip_type)) AS vipType,
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
  GROUP_CONCAT(DISTINCT(tup.pro_id)) AS proId,
  GROUP_CONCAT(DISTINCT(tuc.career_id)) AS careerId,
  GROUP_CONCAT(DISTINCT(tud.domain_id)) AS domainId
FROM tb_user_info tui
  LEFT JOIN tb_userinfo_statistical tus ON tui.id = tus.user_id
  LEFT JOIN (SELECT a.user_id AS user_id,a.vip_type AS vip_type, b.id AS id, b.name AS _name,b.type AS _type FROM tb_user_group a LEFT JOIN tb_group b ON a.group_id = b.id) tug ON tui.user_id = tug.user_id
  LEFT JOIN tb_user_login tul ON tui.user_id = tul.id
  LEFT JOIN tb_userinfo_career tuc ON tui.user_id = tuc.user_id
  LEFT JOIN tb_userinfo_domain tud ON tui.user_id = tud.user_id
  LEFT JOIN tb_userinfo_professional tup ON tui.user_id = tup.user_id
  LEFT JOIN (SELECT id,num,name FROM tfw_dict WHERE CODE=904) tfd ON tui.age_interval_id = tfd.id
GROUP BY tui.user_id
ORDER BY tui.user_id DESC

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
LEFT JOIN tb_interest_user i ON (i.to_user_id = ui.user_id AND i.status = 0 AND i.user_id = #{userId})
GROUP BY userId

userInfoDetail
==============
select 
    ui.user_id userId,
    ui.username,
    ui.avater,
    ui.mobile,
    ui.province_id province,
    ui.city_id city,
    ui.school,
    ui.province_city provinceCity,
    ui.domain,
    ui.key_word keyWord,
    ui.organization,
    ui.per,
    ui.career,
    ui.professional,
    ui.desc
from tb_user_info ui 
where ui.user_id = #{userId}

userInfoDetailWithUa
==============
SELECT
  ui.user_id userId,
  ui.username,
  ui.avater,
  ui.mobile,
  ui.province_id province,
  ui.city_id city,
  ui.school,
  ui.province_city provinceCity,
  ui.domain,
  ui.key_word keyWord,
  ui.organization,
  ui.per,
  ui.career,
  ui.professional,
  ui.desc,
  ua.status
FROM
  tb_user_info ui
  LEFT JOIN tb_user_approval ua
    ON (
      ui.user_id = ua.from_user_id
      OR ui.user_id = ua.to_user_id
    )
    AND ((
      ua.from_user_id = #{toUserId}
      AND ua.`to_user_id` = #{userId}
    )
    OR (
      ua.to_user_id = #{toUserId}
      AND ua.from_user_id = #{userId}
    ) )
    WHERE ui.user_id = #{toUserId}

blanklist
====================
SELECT
  ui.user_id userId,
  ui.username,
  IFNULL(ui.avater, '') AS avater,
  ui.province_city provinceCity,
  ui.domain,
  ui.key_word keyWord,
  ui.organization,
  ui.professional,
  ui.per
FROM
  tb_user_approval ua
  LEFT JOIN tb_user_info ui
    ON ui.`user_id` = ua.`to_user_id`
    WHERE ua.`status` = 3 AND ua.`from_user_id` = #{userId}

userlistByjoinGroup
==============================================
SELECT
  ui.user_id userId,
  ui.username,
  ui.avater,
  ui.mobile,
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
  ui.desc,
  ga.status,
  ga.paied,
  ga.validate_info validateInfo
FROM
  tb_group_approval ga
LEFT JOIN
  tb_user_info ui
ON
  ga.`user_id` = ui.`user_id`
WHERE ga.`group_id` = #{groupId}
  AND ga.`type` = 1 AND ga.`status` = 1
  
introduceUserList
=================
SELECT 
  ui1.user_id AS userId1,
  ui1.username AS username1,
  ui1.avater AS avater1,
  ui1.mobile AS mobile1,
  ui1.province_id province1,
  ui1.city_id city1,
  ui1.school school1,
  ui1.province_city proviceCity1,
  ui1.domain domain1,
  ui1.key_word keyWord1,
  ui1.organization organization1,
  ui1.per per1,
  ui1.career career1,
  ui1.professional professional1,
  ui1.desc desc1,
  ua.validate_info validateInfo1,
  ui2.user_id userId2,
  ui2.username username2,
  ui2.avater avater2,
  ui2.mobile mobile2,
  ui2.province_id province2,
  ui2.city_id city2,
  ui2.school school2,
  ui2.province_city proviceCity2,
  ui2.domain domain2,
  ui2.key_word keyWord2,
  ui2.organization organization2,
  ui2.per per2,
  ui2.career career2,
  ui2.professional professional2,
  ui2.desc desc2,
  ua.validate_info validateInfo2
FROM tb_user_approval ua 
LEFT JOIN 
tb_user_info ui1 
ON 
ua.`from_user_id` = ui1.`user_id` 
LEFT JOIN
tb_user_info ui2
ON
ua.`to_user_id` = ui2.`user_id`
WHERE ua.`group_id` = #{groupId}

findUserListByGroupig
=====================
SELECT
  ui.user_id userId,
  ui.username,
  ui.avater,
  ui.mobile,
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
  ui.desc,
  uf.`label`
FROM tb_user_friend_grouping_member ufgm 
LEFT JOIN tb_user_friend uf
ON uf.`friend_id` = ufgm.`friend_id` 
LEFT JOIN tb_user_info ui 
ON ui.`user_id` = ufgm.`friend_id`
WHERE ufgm.`ufg_id` = #{groupingId}