/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.core.controller;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiniu.util.StringMap;
import com.smallchill.core.toolbox.kit.UploadKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.file.BladeFile;
import com.smallchill.core.toolbox.file.FileRender;
import com.smallchill.core.toolbox.file.UploadFileUtils;
import com.smallchill.core.toolbox.kit.PathKit;

@Controller
@RequestMapping("/kindeditor")
public class KindEditorController extends BladeController {

    @ResponseBody
    @RequestMapping("/upload_json")
    public Record upload_json(@RequestParam("imgFile") MultipartFile file) {
        Record rd = Record.create();
        if (null == file) {
            rd.set("error", 1);
            rd.set("message", "请选择要上传的图片");
            return rd;
        }
        String originalFileName = file.getOriginalFilename();
        String dir = getParameter("dir", "image");
        // 测试后缀
        boolean ok = UploadFileUtils.testExt(dir, originalFileName);
        if (!ok) {
            rd.set("error", 1);
            rd.set("message", "上传文件的类型不允许");
            return rd;
        }
//		BladeFile bf = getFile(file);
//		bf.transfer();
//		Object fileId = bf.getFileId();
//		String url = "/kindeditor/renderFile/" + fileId;
//		rd.set("error", 0);
//		rd.set("title", fileId);
//		rd.set("url", Cst.me().getContextPath() + url);
//		rd.set("name", originalFileName);

        StringMap map = UploadKit.upload(UploadKit.changeFile(file));

        BladeFile bf = getFile(file);
        bf.setThirdUrl(UploadKit.domain + map.get("key"));
        Object fileId = bf.getFileIdByQiniu();
        String url = "/kindeditor/renderFile/" + fileId;
        rd.set("error", 0);
        rd.set("title", fileId);
        rd.set("url", Cst.me().getContextPath() + url);
        rd.set("name", originalFileName);
        return rd;
    }

    @ResponseBody
    @RequestMapping("/file_manager_json")
    public Object file_manager_json() {
        String dir = getParameter("dir", "image");
        // 考虑安全问题
        String path = getParameter("path", "");
        // 不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            return "Access is not allowed.";
        }
        // 最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            return "Parameter is not valid.";
        }
        String order = getParameter("order", "name");

        Map<String, Object> result = UploadFileUtils.listFiles(dir, path, order);
        return result;
    }

    @ResponseBody
    @RequestMapping("/initimg")
    public AjaxResult initimg(@RequestParam String id) {
        Map<String, Object> img = Db.init().findById("TFW_ATTACH", id);
        if (null != img) {
            return json(img);
        } else {
            return fail("获取图片失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/initfile")
    public AjaxResult initfile(@RequestParam String ids) {
        Db dao = Db.init();
        List<Record> file = dao.selectList("select ID as \"id\",NAME as \"name\",URL as \"url\" from TFW_ATTACH where ID in (#{join(ids)})", Record.create().set("ids", ids.split(",")));
        if (null != file) {
            return json(file);
        } else {
            return fail("获取附件失败！");
        }
    }

    @RequestMapping("/renderFile/{id}")
    public void renderFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        Map<String, Object> file = Db.init().findById("TFW_ATTACH", id);
        String url = file.get("URL").toString();
        String path = "";
        try {
            path = download(url, System.currentTimeMillis()+"",request);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        File f = new File((Cst.me().isRemoteMode() ? "" : PathKit.getWebRootPath()) + url);
        File f = new File(path);
        FileRender.init(request, response, f).render();
    }

    public static String download(String urlString, String filename,HttpServletRequest request) throws Exception {

        String path = request.getSession().getServletContext().getRealPath("/");
        path = path + File.separator + "image" + File.separator ;

        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(path+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();

        return path+filename;

    }

}
