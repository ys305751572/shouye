list
===
select r.*,d.simpleName DEPTNAME,GROUP_CONCAT(rg.groups) groups,(select name from tfw_role where id=r.pId) PNAME from tfw_role r left join tfw_dept d on r.deptId=d.id LEFT JOIN (SELECT a.roleid roleid, b.group groups FROM tfw_role_group a LEFT JOIN tfw_group b ON a.groupid = b.id) rg ON r.ID = rg.roleid GROUP BY r.id