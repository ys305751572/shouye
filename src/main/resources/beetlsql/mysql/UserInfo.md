list
===
SELECT
  tui.id AS id,
  GROUP_CONCAT(DISTINCT(tug.id)) AS groupId,
  tui.user_id AS userId,
  tui.avater AS avater,
  tul.login_username AS accountId,
  tui.username AS userName,
  tui.gender AS gender,
  tui.age AS age,
  tui.age_interval_id AS ageIntervalId,
  tfd.num AS ageId,
  tui.province_id AS provinceCode,
  tui.city_id AS cityCode,
  tui.province_city AS provinceCity,
  tui.school AS school,
  GROUP_CONCAT(DISTINCT(tuc.name)) AS career,
  GROUP_CONCAT(DISTINCT(tud.name)) AS domain,
  GROUP_CONCAT(DISTINCT(tup.`pro_name`)) AS professional,
  tui.zy AS proficient,
  tui.sc AS adept,
  tui.zl AS seniority,
  tui.zy2 AS resources,
  tui.organization AS organization,
  org.name AS orgType,
  org.num AS orgId,
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
  GROUP_CONCAT(DISTINCT(tud.domain_id)) AS domainid,
  GROUP_CONCAT(DISTINCT(tud.p_id)) AS domainPid,
  GROUP_CONCAT(DISTINCT(tup.pro_id)) AS proid,
  GROUP_CONCAT(DISTINCT(tup.p_id)) AS proPid,
  GROUP_CONCAT(DISTINCT(tuc.carerrId)) AS carerrId
FROM tb_user_info tui
  LEFT JOIN tb_userinfo_statistical tus ON tui.id = tus.user_id
  LEFT JOIN (SELECT a.user_id AS user_id,a.vip_type AS vip_type, b.id AS id, b.name AS
_name,b.type AS _type FROM tb_user_group a LEFT JOIN tb_group b ON a.group_id = b.id) tug ON
tui.user_id = tug.user_id
  LEFT JOIN tb_user_login tul ON tui.user_id = tul.id
  LEFT JOIN (SELECT a.user_id userId,a.career_id carerrId,b.name NAME FROM tb_userinfo_career a LEFT JOIN tfw_dict b ON a.career_id = b.ID) tuc ON tug.user_id = tuc.userId
  LEFT JOIN tb_userinfo_domain tud ON tui.user_id = tud.user_id
  LEFT JOIN tb_userinfo_professional tup ON tui.user_id = tup.user_id
  LEFT JOIN (SELECT id,num,name FROM tfw_dict WHERE CODE=904) tfd ON tui.age_interval_id = tfd.id
  LEFT JOIN (SELECT id,num,NAME FROM tfw_dict WHERE CODE=907) org ON tui.org_type = org.id
GROUP BY tui.user_id

listPage
====
select
    ui.user_id AS userId,
    ui.username,
    ui.avater,
    ui.province_id province,
    ui.city_id city,
    ui.school,
    ui.province_city provinceCity,
    ui.domain,
    ui.key_word keyWord,
    ui.organization,
    ui.per,
    ui.career,
    ui.desc,
    ua.status,
    ua.from_user_id,
    ua.to_user_id,
    ui.org_is_open,
    @if(!isEmpty(groupId)) {
        ug.group_id groupId,
    @}
    i.status istatus
from tb_user_info ui
LEFT JOIN tb_user_approval ua ON ((ui.user_id = ua.from_user_id OR ui.user_id = ua.to_user_id)
AND (ua.from_user_id = #{userId} OR ua.to_user_id = #{userId}))
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
    ui.age,
    ui.age_interval_id,
    ui.gender,
    ui.province_id province,
    ui.city_id city,
    ui.school,
    ui.product_type,
    ui.product_service_name,
    ui.province_id province,
    ui.city_id city,
    ui.province_city provinceCity,
    ui.domain,
    ui.key_word keyWord,
    ui.org_type,
    ui.org_is_open,
    d.`NAME` orgTypeName,
    ui.organization,
    ui.per,
    ui.career,
    ui.professional,
    ui.desc,
    ui.zy,
    ui.zy2,
    ui.sc,
    ui.zl,
    ui.age,
    ui.industry_ranking,
    ui.qualification
from tb_user_info ui
left join
    tfw_dict d
on
   ui.org_type = d.id
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
    WHERE ua.`status` = 4 AND ua.`from_user_id` = #{userId}

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
  ga.validate_info validateInfo,
  o.status ostatus
FROM
  tb_group_approval ga
LEFT JOIN
  tb_user_info ui
ON
  ga.`user_id` = ui.`user_id`
LEFT JOIN 
    tb_order o ON ga.`id` = o.`ga_id`
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
  ua.validate_info validateInfo2,
  ua.id
FROM tb_approval_user_group ua 
LEFT JOIN 
tb_user_info ui1 
ON 
ua.`from_user_id` = ui1.`user_id`
LEFT JOIN
tb_user_info ui2
ON
ua.`to_user_id` = ui2.`user_id`
WHERE ua.`group_id` = #{groupId} and ua.status = 1

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
WHERE ufgm.`ufg_id` = #{groupingId} group by ui.user_id

findCareerList
==============
SELECT
uc.`id`,uc.`career_id` careerId, uc.`user_id` userId, d.`NAME` `name`
FROM tb_userinfo_career uc
LEFT JOIN tfw_dict d ON uc.`career_id` = d.`ID`
WHERE uc.`user_id` = #{userId}

updateUserInfo
==============
update `tb_user_info` set 
	`user_id`= #{userId},
	`username`= #{username},
	`avater`= #{avater},
	`mobile`= #{mobile},
	`gender`= #{gender},
	`age`= #{age},
	`birthday`= #{birthday},
	`age_interval_id`= #{ageIntervalId},
	`province_id`= #{provinceId},
	`city_id`= #{cityId},
	`province_city`= #{provinceCity},
	`school`= #{school},
	`career`= #{domain},
	`domain`= #{professional},
	`professional`= #{professionalLevel},
	`product_type`= #{productType},
	`product_service_name`= #{productService},
	`org_type`= #{orgType},
	`organization`= #{organization},
	`zy`= #{zy},
	`sc`= #{sc},
	`zy2`=#{zy2},
	`industry_ranking`= #{industryRanking},
	`qualification`=#{qualification},
	`org_is_open`= #{orgIsOpen},
	`key_word`= #{keyWord},
	`group_status`= #{groupStatus},
	`desc`= #{desc},
	`per`=#{per},
	`create_time`=#{createTime}
 where 1=1 and `id`= #{id}