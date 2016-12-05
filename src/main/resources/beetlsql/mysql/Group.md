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
    member.num AS memberCount,
    admin.num AS adminCount,
    tge.cost AS cost,
    tge.cost_status AS costStaus,
    tg.activity_count AS activityCount,
    tg.update_time AS updateTime,
    cUser.NAME AS createName,
    aUser.NAME AS approvalName,
    tge.freeze_time AS status,
    tge.freeze_status AS freezeStatus
FROM (SELECT * FROM tb_group WHERE audit_status = '3') tg
    LEFT JOIN tb_group_extend tge ON tg.id = tge.group_id
    LEFT JOIN (select num,name from tfw_dict where code=908) tfd ON tg.type = tfd.num
    LEFT JOIN tfw_user cUser ON tge.create_admin_id = cUser.ID
    LEFT JOIN tfw_user aUser ON tge.approval_admin_id = aUser.ID
    LEFT JOIN (SELECT a.group_id AS group_id,COUNT(1) AS num FROM tb_user_group a LEFT JOIN tb_group b ON b.id = a.group_id WHERE a.vip_type = '1' GROUP BY a.group_id ) member ON member.group_id = tg.id
    LEFT JOIN (SELECT a.group_id AS group_id,COUNT(1) AS num FROM tb_user_group a LEFT JOIN tb_group b ON b.id = a.group_id WHERE a.vip_type = '2' GROUP BY a.group_id ) admin ON admin.group_id = tg.id
ORDER BY tg.id DESC

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
ORDER BY tg.id DESC

load
===
SELECT
  tgl.id AS id,
  tg.name AS name,
  tg.idcard AS idcard
FROM tb_group_load tgl
LEFT JOIN (SELECT a.id,a.name,b.idcard FROM tb_group a LEFT JOIN tb_group_extend b ON b.group_id = a.id) tg ON tgl.group_id = tg.id

listPage
========
SELECT
    g.id,
    g.name,
    IFNULL(g.avater,"") avater,
    g.province,
    g.city,
    g.type,
    g.province_city provinceCity,
    (select count(*) from tb_user_group ug where ug.group_id = g.id) memberCount,
    g.targat,
    ga.user_id userId,
    ga.status
FROM
    tb_group g
LEFT JOIN
    tb_group_approval ga
ON
    (g.id = ga.group_id AND ga.user_id = #{userId})

groupDetailWithUa
=======================
SELECT
    g.id,
    g.name,
    IFNULL(g.avater,"") avater,
    g.province,
    g.city,
    g.type,
    g.province_city provinceCity,
    (select count(*) from tb_user_group ug where ug.group_id = g.id) memberCount,
    g.targat,
    ga.user_id userId,
    ga.status
FROM
    tb_group g
LEFT JOIN
    tb_group_approval ga
ON
    (g.id = ga.group_id AND ga.user_id = #{userId})
WHERE
    g.id = #{groupId}

   
