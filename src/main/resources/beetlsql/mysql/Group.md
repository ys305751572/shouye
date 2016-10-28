list
===
SELECT
    tg.id AS id,
    tg.avater AS avater,
    tge.idcard AS idcard,
    tg.name AS gName,
    tfd.name AS gType,
    tg.targat AS targat,
    tg.member_count AS memberCount,
    tg.admin_count AS adminCount,
    tge.cost AS cost,
    tg.activity_count AS activityCount,
    tga.through_time AS throughTime,
    cUser.NAME AS createName,
    aUser.NAME AS approvalName,
    tge.freeze_time AS status
FROM tb_group tg
    LEFT JOIN tb_group_extend tge ON tg.id = tge.group_id
    LEFT JOIN tb_group_approval tga ON tg.id = tga.group_id
    LEFT JOIN (select num,name from tfw_dict where code=908) tfd ON tg.type = tfd.num
    LEFT JOIN tfw_user cUser ON tge.create_admin_id = cUser.ID
    LEFT JOIN tfw_user aUser ON tge.approval_admin_id = aUser.ID
