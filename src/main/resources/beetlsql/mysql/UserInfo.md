list
===
select * from tb_user_info

listPage
====
select 
    ui.user_id userId,
    ui.username,
    ui.avater,
    ui.province_city proviceCity,
    ui.domain,
    ui.key_word keyWord,
    ui.organization,
    ui.professional
    ua.status,
    ua.from_user_id,
    ua.to_user_id,
    @if(!isEmpty(groupId)) {
        ug.group_id,
    @}
    i.status istatus
from tb_user_info ui
LEFT JOIN tb_user_approval ua ON (ui.user_id = ua.from_user_id OR ui.user_id = ua.to_user_id) 
LEFT JOIN tb_interest i ON (i.to_id = ui.user_id AND i.type = 0 AND i.status = 0) 
@if(!isEmpty(groupId)){
    RIGHT JOIN tb_user_group ug ON  (ug.user_id = ui.user_id and ug.group_id = #{groupId})
@}
 WHERE ui.user_id != #{userId} 