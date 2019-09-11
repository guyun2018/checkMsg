package com.kayisoft.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.mapper.QueueUserInfoMapper;
import com.kayisoft.model.QueueBean;
import com.kayisoft.model.QueueUserInfo;
import com.kayisoft.util.CommonUtil;
import com.kayisoft.util.DateUtil;
import com.kayisoft.util.HttpUtils;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
@Service
public class CreateCodeServiceImpl implements CreateCodeService {

    @Autowired
    AccessTokenService accessTokenService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${save_path}")
    private String savePath;

    @Value("${app_id}")
    private String appId;

    @Value("${secret}")
    private String secret;

    @Autowired
    QueueUserInfoMapper queueUserInfoMapper;

    /**
     * 二维码类型 "1": 临时二维码  "2": 永久二维码
     */
    @Value("${code_type}")
    private String codeType;

    @Autowired
    CreateCodeService createCodeService;

    @Autowired
    HttpServletRequest request;

    @Value("${code_Image_path}")
    private String codeImagePath;

    /**
     * 生成二维码
     *
     * @param queueUserInfo bean
     * @return result
     */
    @Override
    public String createCode(QueueUserInfo queueUserInfo) {
        //获取accessoken
        String wxAccessToken = accessTokenService.getAccToken("AccessTokenCache");
        System.out.println("获取二维码的token:" + wxAccessToken);
        Map<String, Object> map = new HashMap<>();
        String str1 = "1";
        String str2 = "2";
        QueueUserInfo info = queueUserInfoMapper.selectHospitalCode(queueUserInfo);
        String scene = "";
        if (info == null) {
            //将accessNo和hospitalCode存入表
            QueueUserInfo queueUserInfo1 = new QueueUserInfo();
            queueUserInfo1.setId(CommonUtil.getGUID());
            queueUserInfo1.setHospitalCode(queueUserInfo.getHospitalCode());
            queueUserInfo1.setAccessNo(queueUserInfo.getAccessNo());
            queueUserInfoMapper.insert(queueUserInfo1);
            scene = queueUserInfo1.getId();
        }
        // 临时二维码
        if (str1.equals(codeType)) {
            map.put("expire_seconds", createCodeService.getQrCodeExpire());
            map.put("action_name", "QR_LIMIT_STR_SCENE");
            Map<String, Object> sceneMap = new HashMap<>();
            Map<String, Object> sceneStrMap = new HashMap<>();
            sceneMap.put("scene", sceneStrMap);
            sceneStrMap.put("scene_str", scene);
            map.put("action_info", sceneMap);
            // 永久二维码
        } else if (str2.equals(codeType)) {
            map.put("action_name", "QR_LIMIT_SCENE");
            Map<String, Object> sceneMap = new HashMap<>();
            Map<String, Object> sceneIdMap = new HashMap<>();
            sceneIdMap.put("scene_id", scene);

            sceneMap.put("scene", sceneIdMap);
            map.put("action_info", sceneMap);
        }
        String res = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + wxAccessToken, map, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String ticket = jsonObject.getString("ticket");
        //以检查日期为文件夹名称，方便执行删除
        String folderName = DateUtil.getCurrentDate("yyyy-MM-dd");
        //以检查唯一号为文件名
        String fileName = CommonUtil.getGUID();
        String filePath = HttpUtils.httpsRequestPicture("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket),
                "GET", null, savePath, folderName, fileName, "png");
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        String path = codeImagePath + "/" + folderName + "/" + fileName + ".png";
        return basePath + path;
    }

    /**
     * 获取二维码过期时间
     *
     * @return Integer
     */
    @Override
    public Integer getQrCodeExpire() {
        //根据某些规则设置二维码过期时间
        return 3600;
    }
}
