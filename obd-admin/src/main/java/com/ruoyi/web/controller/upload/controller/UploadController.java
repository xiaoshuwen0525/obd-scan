package com.ruoyi.web.controller.upload.controller;


import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoListVO;
import com.ruoyi.web.controller.upload.domain.ObdVO;
import com.ruoyi.web.controller.upload.service.IUploadService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * 图片上传
 *
 * @author
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private IUploadService uploadService;

    private static final Lock lock = new ReentrantLock();

    @ApiOperation(value = "上传信息")
    @PostMapping("/uploadInformation")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult uploadInformation(ObdVO obdVO, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        if(!"".equals(obdVO.getBoxCode())){
            if (StringUtils.isNoneBlank(obdVO.getBoxCode())) {
                if (!isNumber(obdVO.getBoxCode())) {
                    return AjaxResult.error("OBD箱子扫描的二维码不对");
                }
            }
        }else {
            return AjaxResult.error("OBD箱子串码无效");
        }

        AjaxResult ajaxResult;
        try {
            ajaxResult = uploadService.uploadInformation(obdVO);
        } catch (Exception e) {
            return AjaxResult.error("上传失败");
        }
        return ajaxResult;
    }

    @ApiOperation(value = "根据工号查询分页")
    @GetMapping("/selectObdByJobNumber")
    @ResponseBody
    public AjaxResult selectObdByJobNumber(@RequestParam(value = "jobNumber") String jobNumber, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("工号：" + jobNumber + "  ,pageNum:"+pageNum+"  , pageSize:"+pageSize);
        PageInfo<ObdBoxVO> pages =  uploadService.selectObdByJobNumber(jobNumber, pageNum, pageSize);
        System.out.println("controller selectObdByJobNumber pages: "+pages);
        return AjaxResult.successOBD(pages);
    }

    @ApiOperation(value = "根据id查询obd整个")
    @GetMapping("/selectObdById")
    @ResponseBody
    public AjaxResult selectObdById(@RequestParam(value = "id") int id, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("参数id：" + id );
        return AjaxResult.successOBD(uploadService.selectObdById(id));
    }

    @ApiOperation(value = "更新obd")
    @PostMapping("/updateObd")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult updateObd(String obdInfoVOList, String boxCode, Integer boxId, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("参数 boxCode:"+boxCode+",boxId:"+boxId);
        ObdBoxVO obdBoxVO = new ObdBoxVO();
        int length = 19;
        if(boxCode!=null){
            if (StringUtils.isNoneBlank(boxCode) ){
                if(boxCode.length()>=length){
                    if (!isNumber(boxCode.substring(1, boxCode.length() - 1))) {
                        return AjaxResult.error("OBD箱子扫描的二维码不对");
                    }else {
                        obdBoxVO.setBoxCode(boxCode.substring(1, boxCode.length() - 1));
                    }
                }else {
                    if (!isNumber(boxCode)){
                        return AjaxResult.error("OBD箱子扫描的二维码不对");
                    }else {
                        obdBoxVO.setBoxCode(boxCode);
                    }
                }
            }
        }
        obdBoxVO.setId(boxId);
        ObdInfoListVO obdInfoListVO = JSONUtil.toBean("{obdInfoVOList:" + obdInfoVOList + "}", ObdInfoListVO.class);
        obdBoxVO.setObdInfoVOList(obdInfoListVO.getObdInfoVOList());
        log.info("参数 obdInfoListVO:"+obdInfoListVO.toString());
        AjaxResult ajaxResult;
        try {
            ajaxResult = uploadService.updateObd(obdBoxVO);
        } catch (Exception e) {
            return AjaxResult.error("更新失败");
        }
        return ajaxResult;
    }


    private boolean isNumber(String string) {
        int start = 17;
        int end  = 18;
        String regex = "^[0-9]*[0-9][0-9]*$";
        if(string.length() != end && string.length() != start){
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string).matches();
    }

}
