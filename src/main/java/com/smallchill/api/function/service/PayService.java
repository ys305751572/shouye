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
}
