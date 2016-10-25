package com.smallchill.web.service.impl;

import com.smallchill.api.function.modal.UserDomain;
import com.smallchill.api.function.modal.UserProfessional;
import com.smallchill.api.function.service.UserDomainService;
import com.smallchill.api.function.service.UserprofessionalService;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.kit.NetKit;
import com.smallchill.platform.model.UserLogin;
import com.smallchill.platform.service.UserLoginService;
import com.smallchill.web.model.UserInfo;
import com.smallchill.web.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


/**
 * Generated by Blade.
 * 2016-10-18 09:47:31
 */
@Service
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserprofessionalService userprofessionalService;


    @Transactional
    @Override
    public UserInfo updateUserInfo(UserInfo userInfo, HttpServletRequest request) {

        if (userInfo.getId() != null) {
            _update(userInfo);
        } else {
            // 新建userlogin信息
            int userId = createUserLogin(userInfo, request);
            userInfo.setUserId(userId);
            _save(userInfo);
        }
        processUserInfo(userInfo);
        return userInfo;
    }

    /**
     * 查询用户详细信息
     * @param userId
     * @return
     */
    @Override
    public UserInfo findByUserId(int userId) {
        return this.findFirstBy("where user_id = #{userId}", Record.create().set("userId", userId));
    }

    /**
     * 创建用户
     *
     * @param userInfo 用户详细信息
     */
    private int createUserLogin(UserInfo userInfo, HttpServletRequest request) {
        UserLogin userLogin = new UserLogin();
        String ip = NetKit.getRealIp(request);
        userLogin.setLastLoginIp(ip);
        userLogin.setLastLoginTime(DateTimeKit.nowLong());
        userLogin.setUsername(userInfo.getMobile());
        return userLoginService.saveRtId(userLogin);
    }

    /**
     * 处理userinfo
     * 处理行业/职业关联关系
     *
     * @param userInfo 用户详细信息
     */
    private void processUserInfo(UserInfo userInfo) {
        String domain = userInfo.getDomain();
        String professional = userInfo.getProfessional();
        LOGGER.info("domain:" + domain + "==professional:" + professional);

        // 处理行业（领域）
        if (StringUtils.isNotBlank(domain)) {
            // TODO 先删除该用户所有领域信息
            userDomainService.deleteBy("user_id = #{userId}", Record.create().set("userId", userInfo.getId()));
            String[] domains = domain.split("\\|");
            for (String c : domains) {
                if (StringUtils.isNotBlank(c)) {
                    String[] ss = c.split(",");
                    int userId = Integer.parseInt(ss[0]);
                    int domainId = Integer.parseInt(ss[1]);
                    int pid = Integer.parseInt(ss[2]);
                    String name = ss[3];

                    UserDomain ud = new UserDomain();
                    ud.setUserId(userId);
                    ud.setDomainId(domainId);
                    ud.setPid(pid);
                    ud.setName(name);
                    userDomainService.save(ud);
                }
            }
        }

        // ---------------------- 处理专业-------------------------
        if (StringUtils.isNotBlank(professional)) {
            // TODO 先删除该用户所有专业信息
            userprofessionalService.deleteBy("user_id = #{userId}", Record.create().set("userId", userInfo.getId()));
            String[] professionals = professional.split("\\|");
            for (String c : professionals) {
                if (StringUtils.isNotBlank(c)) {
                    String[] ss = c.split(",");
                    int userId = Integer.parseInt(ss[0]);
                    int proId = Integer.parseInt(ss[1]);
                    int pid = Integer.parseInt(ss[2]);
                    String name = ss[3];

                    UserProfessional up = new UserProfessional();
                    up.setUserId(userId);
                    up.setProId(proId);
                    up.setProName(name);
                    up.setPid(pid);
                    userprofessionalService.save(up);
                }
            }
        }
    }

    /**
     * 新增
     *
     * @param userInfo 用户详细信息
     */
    private void _save(UserInfo userInfo) {
        this.save(userInfo);
    }

    /**
     * 编辑
     *
     * @param userInfo 用户详细信息
     */
    private void _update(UserInfo userInfo) {
        this.update(userInfo);
    }
}
