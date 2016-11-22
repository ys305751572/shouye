package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.system.model.Demo;
import com.smallchill.system.model.RoleGroup;
import com.smallchill.web.meta.intercept.GroupAdminIntercept;
import com.smallchill.web.model.Classification;
import com.smallchill.web.model.Tag;
import com.smallchill.web.model.UserClassification;
import com.smallchill.web.model.UserTag;
import com.smallchill.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */
@RequestMapping(value = "/userGroup")
@Controller
public class UserGroupController extends BaseController {

    private static String LIST_SOURCE = "UserGroup.list";
    private static String BASE_PATH = "/web/usergroup/";
    private static String CODE = "userGroup";
    private static String PERFIX = "tb_user_group";

    @Autowired
    UserGroupService userGroupService;
    @Autowired
    ClassificationService classificationService;
    @Autowired
    UserClassificationService userClassificationService;
    @Autowired
    UserTagService userTagService;
    @Autowired
    TagService tagService;

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        List<Classification> list = classificationService.findAll();
        List<Tag> list2 = tagService.findAll();
        mm.put("tagList", JsonKit.toJson(list2));
        mm.put("classificationList", JsonKit.toJson(list));
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object loadList() {
        Object object = paginate(LIST_SOURCE,new GroupAdminIntercept());
        return object;
    }

    @RequestMapping("/classification")
    public String classification(ModelMap mm) {
        List<Classification> list = classificationService.findAll();
        mm.put("classificationList", JsonKit.toJson(list));
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_classification.html";
    }


    @ResponseBody
    @RequestMapping("/classification_save")
    public AjaxResult classification_save(String vals) {
        String[] ss = JsonKit.parse(vals,String[].class);
        boolean index = true;
        for(String s : ss){
            Classification classification = new Classification();
            classification.setClassification(s);
            boolean temp = classificationService.save(classification);
            if(!temp){
                index = false;
            }
        }
        if (index) {
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/classification_del")
    public AjaxResult classification_del(Integer id) {
        if(id==null) return error(DEL_FAIL_MSG);
        List<UserClassification> userClassifications = userClassificationService.findBy(" classification_id = #{classificationId}", Record.create().set("classificationId", id));
        for(UserClassification userClassification : userClassifications){
            userClassificationService.delete(userClassification.getId());
        }
        Integer index = classificationService.delete(id);
        if (index!=0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/user_classification_add")
    public AjaxResult userClassificationAdd(Integer classificationId,Integer userId) {
        UserClassification userClassification = new UserClassification();
        List<UserClassification> uc = userClassificationService.findBy("classification_id = #{classificationId} AND user_id = #{userId}",Record.create().set("classificationId", classificationId).set("userId",userId));
        if(!uc.isEmpty()){
            return error("已存在");
        }
        userClassification.setUserId(userId);
        userClassification.setClassificationId(classificationId);
        boolean index = userClassificationService.save(userClassification);
        if (index) {
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/user_classification_del")
    public AjaxResult userClassificationDel(String classification,Integer userId) {
        Classification _c = classificationService.findFirstBy(" classification = #{classification}",Record.create().set("classification",classification));
        UserClassification userClassification = userClassificationService.findFirstBy(" user_id = #{userId} AND classification_id = #{classificationId}",Record.create().set("userId",userId).set("classificationId",_c.getId()));
        Integer index = userClassificationService.delete(userClassification.getId());
        if (index!=0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


    @RequestMapping("/tag")
    public String tag(ModelMap mm) {
        List<Tag> list = tagService.findAll();
        mm.put("tagList", JsonKit.toJson(list));
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_tag.html";
    }

    @ResponseBody
    @RequestMapping("/tag_save")
    public AjaxResult tag_save(String vals) {
        String[] ss = JsonKit.parse(vals,String[].class);
        boolean index = true;
        for(String s : ss){
            Tag tag = new Tag();
            tag.setTag(s);
            boolean temp = tagService.save(tag);
            if(!temp){
                index = false;
            }
        }
        if (index) {
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/tag_del")
    public AjaxResult tag_del(Integer id) {
        if(id==null) return error(DEL_FAIL_MSG);
        List<UserTag> userTags = userTagService.findBy(" tag_id = #{tagId}", Record.create().set("tagId", id));
        for(UserTag userTag : userTags){
            userTagService.delete(userTag.getId());
        }
        Integer index = tagService.delete(id);
        if (index!=0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/user_tag_add")
    public AjaxResult userTagAdd(Integer tagId,Integer userId) {
        UserTag userTag = new UserTag();
        List<UserTag> ut = userTagService.findBy("tag_id = #{tagId} AND user_id = #{userId}",Record.create().set("tagId", tagId).set("userId",userId));
        if(!ut.isEmpty()){
            return error("已存在");
        }
        userTag.setUserId(userId);
        userTag.setTagId(tagId);
        boolean index = userTagService.save(userTag);
        if (index) {
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/user_tag_del")
    public AjaxResult userTagDel(String tag,Integer userId) {
        Tag _t = tagService.findFirstBy(" tag = #{tag}",Record.create().set("tag",tag));
        UserTag userTag = userTagService.findFirstBy(" user_id = #{userId} AND tag_id = #{tagId}",Record.create().set("userId",userId).set("tagId",_t.getId()));
        Integer index = userTagService.delete(userTag.getId());
        if (index!=0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


}
