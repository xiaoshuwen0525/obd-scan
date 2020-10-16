package com.ruoyi.web.controller.upload.controller;


import cn.hutool.json.JSONUtil;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.upload.domain.*;
import com.ruoyi.web.controller.upload.service.IUploadService;
import com.ruoyi.web.controller.upload.utils.RequestUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 图片上传
 * 
 * @author
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private IUploadService uploadService;

    @ApiOperation(value = "上传信息")
    @PostMapping("/uploadInformation")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult uploadInformation(ObdVO obdVO, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        if(obdVO.getBoxCode()!=null){
            if(!isNumber(obdVO.getBoxCode()) ){
                return AjaxResult.error("boxCode不是标准en值");
            }
        }else {
            return AjaxResult.error("boxCode不是标准en值");
        }
        AjaxResult ajaxResult;
        try {
            ajaxResult = uploadService.uploadInformation(obdVO);
        } catch (Exception e) {
            return AjaxResult.error("上传失败");
        }
        return ajaxResult;
    }

    @ApiOperation(value = "根据工号查询obd盒子列表")
    @GetMapping("/obdBoxByJobNumber")
    @ResponseBody
    public AjaxResult obdBoxByJobNumber(@RequestParam(value = "jobNumber") String jobNumber, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.obdBoxByJobNumber(jobNumber));
    }

    @ApiOperation(value = "根据盒子Id查询obd列表")
    @GetMapping("/infoByBoxId")
    @ResponseBody
    public AjaxResult infoByJobNumberAndBoxId(@RequestParam(value = "boxId") String boxId, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.infoByBoxId(boxId));
    }

    @ApiOperation(value = "根据obdId查询obd列表")
    @GetMapping("/portByObdId")
    @ResponseBody
    public AjaxResult obdPortByJobNumber(@RequestParam(value = "obdId") String obdId, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.portByObdId(obdId));
    }

    @ApiOperation(value = "根据盒子Id查询单个盒子信息")
    @GetMapping("/selectBoxById")
    @ResponseBody
    public AjaxResult selectBoxById(@RequestParam(value = "id") String id, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.selectBoxById(id));
    }

    @ApiOperation(value = "根据obdId查询单个obd信息")
    @GetMapping("/selectInfoById")
    @ResponseBody
    public AjaxResult selectInfoById(@RequestParam(value = "id") String id, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.selectInfoById(id));
    }

    @ApiOperation(value = "根据postId查询单个端口信息")
    @GetMapping("/selectPortById")
    @ResponseBody
    public AjaxResult selectPortById(@RequestParam(value = "id") String id, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.selectPortById(id));
    }

    @ApiOperation(value = "修改obd盒子")
    @PostMapping("/updateObdBox")
    @ResponseBody
    public AjaxResult updateObdBox(ObdBox obdBox, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.updateObdBox(obdBox));
    }

    @ApiOperation(value = "修改obd")
    @PostMapping("/updateObdInfo")
    @ResponseBody
    public AjaxResult updateObdInfo(ObdInfo obdInfo, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.updateObdInfo(obdInfo));
    }

    @ApiOperation(value = "修改端口")
    @PostMapping("/updateObdPort")
    @ResponseBody
    public AjaxResult updateObdPort(ObdPortInfo obdPortInfo, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.updateObdPort(obdPortInfo));
    }

    @ApiOperation(value = "根据工号查询分页")
    @GetMapping("/selectObdByJobNumber")
    @ResponseBody
    public AjaxResult selectObdByJobNumber(@RequestParam(value = "jobNumber") String jobNumber,@RequestParam(value = "pageNum") Integer pageNum,@RequestParam(value = "pageSize") Integer pageSize, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.selectObdByJobNumber(jobNumber,pageNum,pageSize));
    }

    @ApiOperation(value = "根据id查询obd整个")
    @GetMapping("/selectObdById")
    @ResponseBody
    public AjaxResult selectObdById(@RequestParam(value = "id") int id, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        return AjaxResult.successOBD(uploadService.selectObdById(id));
    }

    @ApiOperation(value = "更新obd")
    @PostMapping("/updateObd")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult updateObd(String obdInfoVOList,String boxCode,Integer boxId, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口---参数： "+RequestUtil.getRequestBody(request));
        ObdBoxVO obdBoxVO = new ObdBoxVO();
        int length = 17;
        if(boxCode.length()< length ){
            return AjaxResult.error("boxCode不是标准en值");
        }
        if(boxCode != null){
            if(!isNumber(boxCode.substring(1,boxCode.length()-1))){
                return AjaxResult.error("boxCode不是标准en值");
            }
        }

        obdBoxVO.setBoxCode(boxCode.substring(1,boxCode.length()-1));
        obdBoxVO.setId(boxId);
        ObdInfoListVO obdInfoListVO = JSONUtil.toBean("{obdInfoVOList:" + obdInfoVOList + "}", ObdInfoListVO.class);
        obdBoxVO.setObdInfoVOList(obdInfoListVO.getObdInfoVOList());
        AjaxResult ajaxResult;
        try {
             ajaxResult = uploadService.updateObd(obdBoxVO);
        } catch (Exception e) {
            return AjaxResult.error("更新失败");
        }
        return ajaxResult;
    }


    private boolean isNumber(String string) {
        int i = 18;
        if(string.length() != i){
            return false;
        }
        if (string == null){
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        return pattern.matcher(string).matches();
    }

}
