list
===
select 
    ui.user_id userId,ui.username,ui.avater,ui.province_city provinceCity,ui.domain,ui.key_word keyWord,ui.organization,ui.professional,uf.label
from 
    tb_user_friend uf 
left join 
    tb_user_info ui 
on 
    uf.friend_id = ui.user_id


findAcquaintancesByUserId
=========================
SELECT 
  b.user_id userId,
  b.username,
  b.avater,
  b.mobile,
  b.province_id province,
  b.city_id city,
  b.school,
  b.province_city provinceCity,
  b.domain,
  b.key_word keyWord,
  b.organization,
  b.per,
  b.career,
  b.professional,
  b.desc,
  ua2.from_user_id,
  ua2.to_user_id,
  ua2.`status`
FROM tb_user_approval ua2 RIGHT JOIN
 ( 
  SELECT ui.* FROM tb_user_approval ua LEFT JOIN tb_user_info ui ON
  (ui.`user_id` = ua.`from_user_id` OR ui.`user_id` = ua.`to_user_id`) AND (ua.`from_user_id` = #{toUserId} OR ua.`to_user_id` = #{toUserId})
   WHERE ui.`user_id` != 31 AND ua.`type` = 2 AND ua.`status` = 2)
   
  AS b ON (ua2.`from_user_id` = b.user_id OR ua2.`to_user_id` = b.user_id) AND (ua2.`from_user_id` = #{userId} OR ua2.to_user_id = #{userId})
  WHERE b.user_id != #{userId} group by b.user_id
  