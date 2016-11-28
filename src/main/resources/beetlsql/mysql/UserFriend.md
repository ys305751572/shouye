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
ui.user_id userId,ui.username,IFNULL(ui.avater,'') AS avater ,ui.province_city provinceCity,ui.domain,ui.key_word keyWord,
ui.organization,ui.professional,ui.per  ,ua.validate_info,ua.introduce_user_id,ua.status,ua.from_user_id,ua.to_user_id 
,ua2.`status`
FROM 
tb_user_approval ua 
LEFT JOIN tb_user_info ui ON ((ui.`user_id` = ua.`from_user_id` OR ui.`user_id` = ua.`to_user_id`) 
AND (ua.`from_user_id` = #{toUserId} OR ua.`to_user_id` = #{userId}) )
LEFT JOIN tb_user_approval ua2 
ON
  (ui.`user_id` = ua2.`from_user_id` OR ui.`user_id` = ua2.`to_user_id`) AND (ua2.`from_user_id` = #{userId} OR ua2.`to_user_id` = #{userId})
WHERE ui.`user_id` != #{toUserId} AND ua.type = 2 AND (ua.status = 2 OR ua.`status` = 1)