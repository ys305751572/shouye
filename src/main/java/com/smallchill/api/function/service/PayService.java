package com.smallchill.api.function.service;

import com.smallchill.api.common.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 支付service
 * Created by yesong on 2016/12/3 0003.
 */
public interface PayService {

    Map<String, Object> getPrepayId(Integer userId, Integer groupId, Double cost, String validateInfo,
                                    Integer matchType, Integer targetType, HttpServletResponse response, HttpServletRequest request)
            throws GroupCostException, UserHasApprovalException, UserHasJoinGroupException, UserInOthersBlankException, GroupLimitException, GroupCloseJoinException;

    void joinGroupWxNotify(HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> getPrepayIdOfValueaddService(Integer userId, Integer type, Integer number, Double money,
                                                     HttpServletResponse response, HttpServletRequest request) throws UserInfoExtendException, FriendExtendPriceException;

    Map<String, Object> getPrepayIdOfValueaddServiceInterest(Integer userId, Integer number, Integer money);

    Map<String, Object> getPrepayIdOfValueaddServiceAcquaintance(Integer userId, Integer number, Integer money);

    void valueaddServiceWxNotify(HttpServletRequest request, HttpServletResponse response);

    void refund(Integer gaId, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> createRenewalOrder(Integer userId, Integer groupId, Double cost, HttpServletRequest request,
                                           HttpServletResponse response) throws GroupCostException;

    void wxRenewalNotify(HttpServletRequest request, HttpServletResponse response);

    void wxApplyNotify(HttpServletRequest request, HttpServletResponse response);
}
