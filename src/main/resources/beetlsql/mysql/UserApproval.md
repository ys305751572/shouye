findByUserId
============
select 
    ui.user_id,ui.username,ui.avater,ui.province_city,ui.domain,ui.key_word,ui.organization,ui.professional
from 
    tb_user_approval ua 
join 
    tb_user_info ui