package com.smallchill.web.meta.task;

import com.smallchill.api.function.meta.consts.MessageConts;
import com.smallchill.api.function.modal.Message;
import com.smallchill.api.function.modal.vo.SendVo;
import com.smallchill.common.task.TimeWork;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时发送消息
 * Created by yesong on 2016/11/3 0003.
 */
public class SendTimeWork extends TimeWork{

    @Override
    protected void doExecute() {
        System.out.println("=======================执行定时任务.SendTimeWork=======================");
        String tableIds = "";
        Message msg = new Message();

        try{
            msg.setFromId(-1);
            msg.setSendType(SendVo.getSendType());
            msg.setReceiveType(SendVo.getReceiveType());
            msg.setSendTime(SendVo.getSendTime());
            msg.setTitle(MessageConts.MSG_SYS + SendVo.getTitle());
            msg.setContent(SendVo.getContent());
            msg.setCreateTime(DateTimeKit.nowLong());

            if(SendVo.getReceiveType()==1){
                tableIds = "userInfoIds";
            }else {
                tableIds = "groupIds";
            }

            if(SendVo.getToId()!=null){
                msg.setToId(SendVo.getToId());
                Blade.create(Message.class).save(msg);
            }else {
                List<Integer> ids = (List<Integer>) ShiroKit.getSession().getAttribute(tableIds);
                if(!ids.isEmpty()){
                    for (Integer _id : ids) {
                        msg.setToId(_id);
                        Blade.create(Message.class).save(msg);
                    }
                }
            }

            System.out.println("=======================end=======================");
            SendVo.setToId(null);

        }catch (RuntimeException e){
            e.printStackTrace();
        }


    }
}
