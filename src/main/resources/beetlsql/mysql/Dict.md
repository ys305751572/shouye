list
===
select d.*,(select name from tfw_dict  where id=d.pId) PNAME from tfw_dict d 

diy
===
select ID,pId as PID,name as TEXT from  TFW_DICT 

list2
=====
select
    d.ID,
    d.PID,
    d.CODE,
    d.NAME,
    d.NUM,
    IFNULL(at.URL,"") AS URL
from
    TFW_DICT d
LEFT JOIN
    tfw_attach at
ON 
    d.path = at.id
where d.code = #{code} order by num