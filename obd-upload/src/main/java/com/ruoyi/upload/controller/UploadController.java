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
    @PostMapping("/uploadInformation")
    @ResponseBody
    public AjaxResult uploadInformation(@RequestBody Obd obd)
    {
        return uploadService.uploadInformation(obd);
    }

    @ApiOperation(value = "上传盒子")
    @PostMapping("/uploadObdBox")
    @ResponseBody
    public AjaxResult uploadObdBox(@RequestBody ObdBox obdBox)
    {
        return AjaxResult.success(uploadService.uploadObdBox(obdBox));
    }

    @ApiOperation(value = "上传obd")
    @PostMapping("/uploadObdInfo")
    @ResponseBody
    public AjaxResult uploadObdInfo(@RequestBody ObdInfo obdInfo)
    {
        return AjaxResult.success(uploadService.uploadObdInfo(obdInfo));
    }

    @ApiOperation(value = "上传端口")
    @PostMapping("/uploadObdPost")
    @ResponseBody
    public AjaxResult uploadObdPost(@RequestBody ObdPortInfo obdPortInfo)
    {
        return AjaxResult.success(uploadService.uploadObdPost(obdPortInfo));
    }

    @ApiOperation(value = "根据工号查询obd盒子列表")
    @GetMapping("/obdBoxByJobNumber")
    @ResponseBody
    public AjaxResult obdBoxByJobNumber(@RequestParam(value = "jobNumber") String jobNumber)
    {
        return AjaxResult.success(uploadService.obdBoxByJobNumber(jobNumber));
    }

    @ApiOperation(value = "根据盒子Id查询obd列表")
    @GetMapping("/infoByBoxId")
    @ResponseBody
    public AjaxResult infoByJobNumberAndBoxId(@RequestParam(value = "boxId") String boxId)
    {
        return AjaxResult.success(uploadService.infoByBoxId(boxId));
    }

    @ApiOperation(value = "根据obdId查询obd列表")
    @GetMapping("/portByObdId")
    @ResponseBody
    public AjaxResult obdPortByJobNumber(@RequestParam(value = "obdId") String obdId)
    {
        return AjaxResult.success(uploadService.portByObdId(obdId));
    }

    @ApiOperation(value = "根据盒子Id查询单个盒子信息")
    @GetMapping("/selectBoxById")
    @ResponseBody
    public AjaxResult selectBoxById(@RequestParam(value = "id") String id)
    {
        return AjaxResult.success(uploadService.selectBoxById(id));
    }

    @ApiOperation(value = "根据obdId查询单个obd信息")
    @GetMapping("/selectInfoById")
    @ResponseBody
    public AjaxResult selectInfoById(@RequestParam(value = "id") String id)
    {
        return AjaxResult.success(uploadService.selectInfoById(id));
    }

    @ApiOperation(value = "根据postId查询单个端口信息")
    @GetMapping("/selectPortById")
    @ResponseBody
    public AjaxResult selectPortById(@RequestParam(value = "id") String id)
    {
        return AjaxResult.success(uploadService.selectPortById(id));
    }

    @ApiOperation(value = "修改obd盒子")
    @PostMapping("/updateObdBox")
    @ResponseBody
    public AjaxResult updateObdBox(ObdBox obdBox)
    {
        return AjaxResult.success(uploadService.updateObdBox(obdBox));
    }

    @ApiOperation(value = "修改obd")
    @PostMapping("/updateObdInfo")
    @ResponseBody
    public AjaxResult updateObdInfo(ObdInfo obdInfo)
    {
        return AjaxResult.success(uploadService.updateObdInfo(obdInfo));
    }

    @ApiOperation(value = "修改端口")
    @PostMapping("/updateObdPort")
    @ResponseBody
    public AjaxResult updateObdPort(ObdPortInfo obdPortInfo)
    {
        return AjaxResult.success(uploadService.updateObdPort(obdPortInfo));
    }


    @GetMapping("/selectObdByJobNumber")
    @ResponseBody
    public AjaxResult selectObdByJobNumber(@RequestParam(value = "jobNumber") String jobNumber)
    {
        return AjaxResult.success(uploadService.selectObdByJobNumber(jobNumber));
    }
}
