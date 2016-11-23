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
