list
===
SELECT
  SS.juli,
  ua.`status`,
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
  (SELECT
    s.*,
    ROUND(
      6378.138 * 2 * ASIN(
        SQRT(
          POW(SIN((#{lat} * PI() / 180- lat * PI() / 180) / 2), 2) + COS(#{lat} * PI() / 180) * COS(lat * PI() / 180) * POW(SIN((#{lon} * PI() / 180- lon * PI() / 180) / 2), 2)
        )
      ) * 1000
    ) AS juli
  FROM
    tb_user_local AS s) SS
  LEFT JOIN tb_user_approval ua
    ON (
      SS.user_id = ua.`from_user_id`
      OR SS.user_id = ua.`to_user_id`
    )
    AND (
      ua.`from_user_id` = #{userId}
      OR ua.`to_user_id` = #{userId}
    )
  LEFT JOIN tb_user_info ui
    ON (SS.user_id = ui.`user_id`)
WHERE SS.juli < 50
  AND SS.user_id != #{userId}
ORDER BY SS.juli ASC

isInSameGroup
=============
SELECT
	COUNT(ug.`id`) counts
FROM
	tb_user_group ug
WHERE
	ug.`user_id` = #{userId} AND ug.`vip_type` = 2
AND ug.`group_id` IN
(SELECT ug2.`group_id` FROM tb_user_group ug2 WHERE ug2.`user_id` = #{toUserId})

findMemberByUserId
==================
SELECT
    ug.group_id,
    g.name
FROM
    tb_user_group ug
LEFT JOIN
    tb_group g
ON
    ug.group_id = g.id
WHERE
    ug.user_id = #{userId} 
and
    ug.vip_type = 2