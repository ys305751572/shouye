list
===
select 
    r.*,d.name AS DIC_STATUS 
from tb_report r
left join (select num,name from tfw_dict where code = 903) d on r.status = d.num



operation
===
SELECT
  tr.id AS id,
  tr.rno AS rno,
  tr.type AS type,
  tr.object_id AS objectId,
  tr.object_name AS objectName,
  COUNT(trr.id) AS num,
  tr.status AS status,
  tr.admin_id AS adminId,
  tr.report_time AS reportTime,
  GROUP_CONCAT(trr.reasons) AS reasons,
  tui.username AS userName,
  tg.name AS groupName
FROM tb_report tr
  LEFT JOIN tb_report_reasons trr ON tr.id = trr.report_id
  LEFT JOIN tb_user_info tui ON tr.object_id = tui.id AND tr.type = 1
  LEFT JOIN tb_group tg ON tr.object_id = tg.id AND tr.type = 2
GROUP BY tr.id