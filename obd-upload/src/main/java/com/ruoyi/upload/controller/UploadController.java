package com.ruoyi.upload.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;


import com.ruoyi.upload.domain.Obd;
import com.ruoyi.upload.domain.ObdBox;
import com.ruoyi.upload.domain.ObdInfo;
import com.ruoyi.upload.domain.ObdPortInfo;
import com.ruoyi.upload.service.IUploadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

/**
 * 图片上传
 * 
 * @author
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController
{

    @Autowired
    private IUploadService uploadService;


    @ApiOperation(value = "上传信息")
    @GetMapping("/uploadInformation")
    @ResponseBody
    public AjaxResult uploadInformation(Obd obd)
    {
        return uploadService.uploadInformation(obd);
    }

    @ApiOperation(value = "上传盒子")
    @GetMapping("/uploadObdBox")
    @ResponseBody
    public AjaxResult uploadObdBox(ObdBox obdBox)
    {
        return AjaxResult.success(uploadService.uploadObdBox(obdBox));
    }

    @ApiOperation(value = "上传obd")
    @GetMapping("/uploadObdInfo")
    @ResponseBody
    public AjaxResult uploadObdInfo(ObdInfo obdInfo)
    {
        return AjaxResult.success(uploadService.uploadObdInfo(obdInfo));
    }

    @ApiOperation(value = "上传端口")
    @GetMapping("/uploadObdPost")
    @ResponseBody
    public AjaxResult uploadObdPost(ObdPortInfo obdPortInfo)
    {
        return AjaxResult.success(uploadService.uploadObdPost(obdPortInfo));
    }


    @GetMapping("/obdBoxByJobNumber")
    @ResponseBody
    public AjaxResult obdBoxByJobNumber(@RequestParam(value = "jobNumber") String jobNumber)
    {
        return AjaxResult.success(uploadService.obdBoxByJobNumber(jobNumber));
    }

    @GetMapping("/InfoByJobNumberAndBoxId")
    @ResponseBody
    public AjaxResult InfoByJobNumberAndBoxId(@RequestParam(value = "jobNumber") String jobNumber,@RequestParam(value = "boxId") String boxId)
    {
        return AjaxResult.success(uploadService.InfoByJobNumberAndBoxId(boxId,jobNumber));
    }


    @GetMapping("/portByObdId")
    @ResponseBody
    public AjaxResult obdPortByJobNumber(@RequestParam(value = "obdId") String obdId)
    {
        return AjaxResult.success(uploadService.portByObdId(obdId));
    }


    @GetMapping("/selectBoxById")
    @ResponseBody
    public AjaxResult selectBoxById(@RequestParam(value = "id") String id)
    {
        return AjaxResult.success(uploadService.selectBoxById(id));
    }

    @GetMapping("/selectInfoById")
    @ResponseBody
    public AjaxResult selectInfoById(@RequestParam(value = "id") String id)
    {
        return AjaxResult.success(uploadService.selectInfoById(id));
    }

    @GetMapping("/selectPortById")
    @ResponseBody
    public AjaxResult selectPortById(@RequestParam(value = "id") String id)
    {
        return AjaxResult.success(uploadService.selectPortById(id));
    }

    @GetMapping("/updateObdBox")
    @ResponseBody
    public AjaxResult updateObdBox(ObdBox obdBox)
    {
        return AjaxResult.success(uploadService.updateObdBox(obdBox));
    }

    @GetMapping("/updateObdInfo")
    @ResponseBody
    public AjaxResult updateObdInfo(ObdInfo obdInfo)
    {
        return AjaxResult.success(uploadService.updateObdInfo(obdInfo));
    }

    @GetMapping("/updateObdPort")
    @ResponseBody
    public AjaxResult updateObdPort(ObdPortInfo obdPortInfo)
    {
        return AjaxResult.success(uploadService.updateObdPort(obdPortInfo));
    }


}
