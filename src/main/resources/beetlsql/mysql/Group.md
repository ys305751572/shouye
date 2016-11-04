list
===
SELECT
    tg.id AS id,
    tg.avater AS avater,
    tge.idcard AS idcard,
    tg.name AS gName,
    tfd.name AS gType,
    tg.type AS type,
    tg.targat AS targat,
    tg.member_count AS memberCount,
    tg.admin_count AS adminCount,
    tge.cost AS cost,
    (CASE WHEN tge.cost = 0 THEN '免费' ELSE '收费' END) AS _cost,
    tg.activity_count AS activityCount,
    tg.update_time AS updateTime,
    cUser.NAME AS createName,
    aUser.NAME AS approvalName,
    tge.freeze_time AS status
FROM (SELECT * FROM tb_group WHERE audit_status = '3') tg
    LEFT JOIN tb_group_extend tge ON tg.id = tge.group_id
    LEFT JOIN (select num,name from tfw_dict where code=908) tfd ON tg.type = tfd.num
    LEFT JOIN tfw_user cUser ON tge.create_admin_id = cUser.ID
    LEFT JOIN tfw_user aUser ON tge.approval_admin_id = aUser.ID


audit
===
SELECT
  tg.id AS id,
  tg.avater AS avater,
  tge.idcard AS idcard,
  tg.name AS gName,
  tg.targat AS targat,
  cUser.NAME AS createName,
  aUser.NAME AS approvalName,
  tfd.name AS auditStatus,
  tg.audit_status AS a_status,
  tg.audit_comment AS auditComment,
  tg.update_time AS updateTime
FROM tb_group tg
  LEFT JOIN tb_group_extend tge ON tg.id = tge.group_id
  LEFT JOIN (SELECT num, name FROM tfw_dict WHERE CODE = 911) tfd ON tg.audit_status = tfd.num
  LEFT JOIN tfw_user cUser ON tge.create_admin_id = cUser.ID
  LEFT JOIN tfw_user aUser ON tge.approval_admin_id = aUser.ID


listPage
========
SELECT
    g.id,
    g.avater,
    g.province,
    g.city,
    g.type,
    g.province_city provinceCity,
    g.member_count memberCount,
    g.targat,
    ga.user_id userId,
    ga.status
FROM
    tb_group g
LEFT JOIN
    tb_group_approval ga
ON
    (g.id = ga.group_id AND ga.user_id = #{userId})