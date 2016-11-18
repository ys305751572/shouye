list
===
SELECT
  tt.id AS id,
  tt.user_id AS userId,
  tt.order_no AS orderNo,
  tt.type AS t_type,
  tt.num AS num,
  tt.amount AS amount,
  tt.flow AS flow,
  tui.username AS userName,
  tt.platform AS platform,
  tui.mobile AS mobile,
  DATE_FORMAT(FROM_UNIXTIME(tt.create_time/1000),'%Y-%m-%d') AS createTime,
  tt.status AS t_status
FROM tb_trading tt
  LEFT JOIN tb_user_info tui ON tt.user_id = tui.id

record
===
SELECT
  id AS id,
  create_time AS createTime,
  DATE_FORMAT(FROM_UNIXTIME(create_time/1000),'%Y-%m-%d') AS createTime,
  SUM(amount) AS amount
FROM tb_trading
GROUP BY createTime