list
====
SELECT a.`id`,a.`title`,a.content,a.`create_time` pushTime,IFNULL(a.`cover`,"") cover,
g.`name` groupname
FROM tb_article_show as1 
LEFT JOIN tb_article a
ON as1.`article_id` = a.`id`
LEFT JOIN `tb_group` g
ON a.`from_id` = g.`id`
WHERE as1.`to_id` = #{userId} 
@if(!isEmpty(provinceId)) {
AND a.`province_id` = #{provinceId}
@}
@if(!isEmpty(cityId)) {
AND a.`city_id` = #{cityId}
@}
@if(!isEmpty(time)) {
AND FROM_UNIXTIME(a.`create_time`/1000,'%Y%m%d')  = #{time}
@}
AND 
(a.`article_type` = 232 
OR (as1.`is_all` = 1)
OR (as1.`type` = 4 AND a.`from_id` IN (#{dailyIds}))
OR (as1.`type` = 5 AND a.`from_id` IN (#{maganzineIds})))



detail
======
SELECT a.`id` activityId,a.`title`,a.`content`,a.`cover`,
a.`from_id`,a.`start_time` startTime,a.`end_time` endTime,a.apply_end_time applyEndTime,
a.ceiling,
a.`address`,a.`cost`,a.`limit`,a.`create_time` pushTime,g.`id` groupId, g.`name` groupname
FROM `tb_article` a 
LEFT JOIN tb_group g
ON a.`from_id` = g.`id`
WHERE a.id = #{id}

applyUserList
=============
SELECT
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
    ui.org_is_open
FROM `tb_activity_apply` aa
LEFT JOIN  tb_user_info ui
ON aa.`user_id` = ui.`user_id`
LEFT JOIN tb_user_approval ua ON ((ui.user_id = ua.from_user_id OR ui.user_id = ua.to_user_id)
AND (ua.from_user_id = #{userId} OR ua.to_user_id = #{userId}))
WHERE aa.`article_id` = #{activityId}
GROUP BY userId

myList
======
SELECT a.id, a.`title`,a.`create_time` pushTime,g.id,g.`name` groupname,
a.start_time startTime, a.end_time endTime
FROM `tb_activity_apply` aa
LEFT JOIN `tb_article` a
ON aa.`article_id` = a.`id`
LEFT JOIN `tb_group` g
ON a.`from_id` = g.`id`
WHERE aa.`user_id` = #{userId}