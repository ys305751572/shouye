list
===
select * from tb_order

groupOrderList
===
SELECT
  tor.id AS id,
  tor.group_id AS groupId,
  tor.order_no AS orderNo,
  tor.order_type AS orderType,
  tor.order_amount AS orderAmount,
  tui.username AS userName,
  tui.mobile AS mobile,
  DATE_FORMAT(FROM_UNIXTIME(tor.create_time/1000),'%Y-%m-%d') AS createTime ,
  tor.status AS status
FROM
  tb_order tor
  LEFT JOIN tb_group tg ON tg.id = tor.group_id
  LEFT JOIN tb_user_info tui ON tui.user_id = tor.user_id
WHERE tor.order_type = '1' OR tor.order_type = '2'
ORDER BY tor.id DESC

