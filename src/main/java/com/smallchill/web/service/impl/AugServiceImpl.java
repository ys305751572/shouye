package com.smallchill.web.service.impl;

import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.Aug;
import com.smallchill.web.service.AugService;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated by Blade.
 * 2016-11-08 17:59:49
 */
@Service
public class AugServiceImpl extends BaseService<Aug> implements AugService {

    @Override
    public JqGrid testList() {
        String sql = "SELECT * FROM (SELECT\n" +
                "tui.id AS id,\n" +
                "tui.user_id AS userId,\n" +
                "tui.avater AS avater,\n" +
                "tul.login_username AS accountId,\n" +
                "tui.username AS userName,\n" +
                "tui.gender AS gender,\n" +
                "tui.age AS age,\n" +
                "tui.province_city AS provinceCity,\n" +
                "tui.school AS school,\n" +
                "tui.career AS career,\n" +
                "tui.domain AS domain,\n" +
                "tui.professional AS professional,\n" +
                "tui.zy AS proficient,\n" +
                "tui.sc AS adept,\n" +
                "tui.zl AS seniority,\n" +
                "tui.zy2 AS resources,\n" +
                "tui.organization AS organization,\n" +
                "tui.org_type AS orgType,\n" +
                "tui.product_type AS productType,\n" +
                "tui.industry_ranking AS industryRanking,\n" +
                "tui.qualification AS qualification,\n" +
                "tui.key_word AS keyWord,\n" +
                "GROUP_CONCAT(tug._name) AS groupName,\n" +
                "tug._type AS groupType,\n" +
                "tui.group_status AS groupStatus,\n" +
                "tui.vip_type AS vipType,\n" +
                "tus.organization_num AS organizationNum,\n" +
                "tus.friend_num AS friendNum,\n" +
                "tus.acquaintances_num AS acquaintancesNum,\n" +
                "tus.activity_apply_num AS activityApplyNum,\n" +
                "tus.activity_sign_num AS activitySignNum,\n" +
                "(tus.activity_sign_num / tus.activity_apply_num) AS activityParticipate,\n" +
                "tus.content_num AS contentNum,\n" +
                "tui.mobile AS mobile,\n" +
                "tul.create_time AS createTime,\n" +
                "tul.last_login_time AS lastLoginTime,\n" +
                "tul.status AS STATUS,\n" +
                "GROUP_CONCAT(tup.pro_id) AS proId,\n" +
                "GROUP_CONCAT(tuc.career_id) AS careerId,\n" +
                "GROUP_CONCAT(tud.domain_id) AS domainId\n" +
                "FROM tb_user_info tui\n" +
                "LEFT JOIN tb_userinfo_statistical tus ON tui.id = tus.user_id\n" +
                "LEFT JOIN (SELECT a.user_id AS user_id, b.id AS id, b.name AS _name,b.type AS _type FROM tb_user_group a LEFT JOIN tb_group b ON a.group_id = b.id) tug ON tui.id = tug.user_id\n" +
                "LEFT JOIN tb_user_login tul ON tui.user_id = tul.id\n" +
                "LEFT JOIN tb_userinfo_career tuc ON tui.id = tuc.user_id\n" +
                "LEFT JOIN tb_userinfo_domain tud ON tui.id = tud.user_id\n" +
                "LEFT JOIN tb_userinfo_professional tup ON tui.id = tup.user_id\n" +
                "GROUP BY tui.id) blade_statement WHERE 1=1  AND (domainId = #{domainId} ) ORDER BY id DESC\n";


        Map<String, Object> map = new HashMap<>();
        map.put("domainId", 104);
        map.put("orderBy" , "id desc");
        BladePage page = Db.init().paginate(sql, Map.class, map, 1, 10);
        return null;
    }
}
