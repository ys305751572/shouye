list
===
SELECT
  tw.id AS id,
  tw.order_no AS orderNo,
  tg.name AS g_name,
  tw.amount AS amount,
  tw.poundage AS poundage,
  tw.bank AS bank,
  DATE_FORMAT(FROM_UNIXTIME(tw.create_time/1000),'%Y-%m-%d') AS createTime,
  tw.type AS w_type
FROM tb_withdrawal tw
  LEFT JOIN tb_group tg ON tw.group_id = tg.id