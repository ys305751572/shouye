package com.smallchill.api.function.service;

import com.smallchill.api.common.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * Created by yesong on 2016/12/3 0003.
 */
public interface PayService {

    Map<String, Object> getPrepayId(Integer userId, Integer groupId, Integer cost, String validateInfo,
                                    String matchType, HttpServletResponse response, HttpServletRequest request)
    throws GroupCostException, UserHasApprovalException, UserHasJoinGroupException, UserInOthersBlankException, GroupLimitException;

    void joinGroupWxNotify(HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> getPrepayIdOfValueaddService(Integer userId, Integer type, Integer number, Integer money,
                                                     HttpServletResponse response, HttpServletRequest request) throws UserInfoExtendException;

    Map<String, Object> getPrepayIdOfValueaddServiceInterest(Integer userId, Integer number, Integer money);

    Map<String, Object> getPrepayIdOfValueaddServiceAcquaintance(Integer userId, Integer number, Integer money);

    void valueaddServiceWxNotify(HttpServletRequest request, HttpServletResponse response);
}
