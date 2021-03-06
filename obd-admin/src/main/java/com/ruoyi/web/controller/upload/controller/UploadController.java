package com.ruoyi.web.controller.upload.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.upload.domain.ObdBox;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoListVO;

import com.ruoyi.web.controller.upload.domain.ObdPicture;
import com.ruoyi.web.controller.upload.service.IUploadService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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

    @ApiOperation(value = "根据工号查询分页")
    @GetMapping("/selectObdByJobNumber")
    @ResponseBody
    public AjaxResult selectObdByJobNumber(@RequestParam(value = "jobNumber") String jobNumber, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("工号：" + jobNumber + "  ,pageNum:" + pageNum + "  , pageSize:" + pageSize);
        PageInfo<ObdBoxVO> pages = uploadService.selectObdByJobNumber(jobNumber, pageNum, pageSize);
        System.out.println("controller selectObdByJobNumber pages: " + pages);
        return AjaxResult.successOBD(pages);
    }

    @ApiOperation(value = "根据id查询obd整个")
    @GetMapping("/selectObdById")
    @ResponseBody
    public AjaxResult selectObdById(@RequestParam(value = "id") int id, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("参数id：" + id);
        return AjaxResult.successOBD(uploadService.selectObdById(id));
    }

    @ApiOperation(value = "更新obd")
    @PostMapping("/updateObd")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult updateObd(String obdInfoVOList, String boxCode, Integer boxId, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("参数 boxCode:" + boxCode + ",boxId:" + boxId);
        String undefined = "undefined";

        ObdBoxVO obdBoxVO = new ObdBoxVO();
        ObdInfoListVO obdInfoListVO = JSONUtil.toBean("{obdInfoVOList:" + obdInfoVOList + "}", ObdInfoListVO.class);
        String boxUniqueId = obdInfoListVO.getObdInfoVOList().get(0).getBoxUniqueId();
        if(StringUtils.isBlank(boxUniqueId) || undefined.equals(boxUniqueId)){
            return AjaxResult.warn("机箱唯一id不应为空");
        }
        ObdBoxVO boxVO = uploadService.selectPcObdByCode(boxUniqueId);
        String boxCode1 = boxVO.getBoxCode();
        String labelCode = boxVO.getLabelCode();
        obdBoxVO.setId(boxId);
        obdBoxVO.setBoxCode(boxCode1);
        obdBoxVO.setLabelCode(labelCode);
        obdBoxVO.setBoxUniqueId(boxVO.getBoxUniqueId());
        obdBoxVO.setBoxName(boxVO.getBoxName());
        obdBoxVO.setObdInfoVOList(obdInfoListVO.getObdInfoVOList());
        log.info("参数 obdInfoListVO:" + obdInfoListVO.toString());
        AjaxResult ajaxResult;
        try {
            ajaxResult = uploadService.updateObd(obdBoxVO);
        } catch (Exception e) {
            return AjaxResult.error("更新失败");
        }
        return ajaxResult;
    }

    @ApiOperation(value = "保存图片")
    @PostMapping("/uploadPicture")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult uploadPicture(String boxUniqueId,MultipartFile file, String boxCode, String jobNumber, HttpServletRequest request) throws IOException {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("参数 boxCode:" + boxCode + ",jobNumber:" + jobNumber + ",file:" + file+",boxUniqueId:"+boxUniqueId);
        String undefined = "undefined";
        ObdPicture obdPicture = new ObdPicture();
        if(StringUtils.isBlank(boxUniqueId) || undefined.equals(boxUniqueId)){
            return AjaxResult.warn("机箱唯一id不应为空");
        }
        ObdBoxVO boxVO = uploadService.selectPcObdByCode(boxUniqueId);
        String boxCode1 = boxVO.getBoxCode();
        String labelCode = boxVO.getLabelCode();

        obdPicture.setBoxCode(boxCode1);
        obdPicture.setLabelCode(labelCode);
        if (StringUtils.isNotBlank(jobNumber) && !undefined.equals(jobNumber)) {
            obdPicture.setJobNumber(jobNumber);
        } else {
            return AjaxResult.warn("工号不应为空");
        }
        String s = "保存图片成功";
        int i = uploadService.uploadObdPicture(obdPicture, file, boxCode);
        if (i <= 0) {
            return AjaxResult.error("图片上传失败，请重试");
        }

        return AjaxResult.success("200", "操作成功", s);
    }


    @ApiOperation(value = "保存obd")
    @PostMapping("/uploadInformation")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult uploadInformation(String boxUniqueId,String obdInfoVOList, String boxCode, String jobNumber, HttpServletRequest request) {
        log.info("成功进入【" + request.getRequestURI() + "】接口");
        log.info("参数 boxCode:" + boxCode + ",jobNumber:" + jobNumber+",boxUniqueId:"+boxUniqueId);
        String undefined = "undefined";
        ObdBoxVO obdBoxVO = new ObdBoxVO();
        if (!undefined.equals(boxCode) && StringUtils.isNotBlank(boxCode)) {
            obdBoxVO.setBoxCode(boxCode);
        }
        if (StringUtils.isNotBlank(jobNumber) && !undefined.equals(jobNumber)) {
            obdBoxVO.setJobNumber(jobNumber);
        } else {
            return AjaxResult.warn("工号不应为空");
        }
        if(StringUtils.isNotBlank(boxUniqueId) && !undefined.equals(boxUniqueId)){
            obdBoxVO.setBoxUniqueId(boxUniqueId);
        }else {
            return AjaxResult.warn("机箱唯一id为空,请联系管理员");
        }

        log.info("参数未转 obdInfoListVO:" + obdInfoVOList);
        ObdInfoListVO obdInfoListVO = JSONUtil.toBean("{obdInfoVOList:" + obdInfoVOList + "}", ObdInfoListVO.class);
        obdBoxVO.setObdInfoVOList(obdInfoListVO.getObdInfoVOList());
        log.info("参数 obdInfoListVO:" + obdInfoListVO.toString());
        return uploadService.uploadInformation(obdBoxVO);
    }

    @ApiOperation(value = "删除端口数据")
    @PostMapping("/deleteByPortId")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult deleteByPortId(Integer id) {
        log.info("删除端口数据入参 "+id);
        if(id == null || id == 0){
            return AjaxResult.error("端口id不应为空");
        }
        int i = uploadService.deleteByPortId(id);
        if(i>0){
            return AjaxResult.successOBD(i);
        }else {
            return  AjaxResult.error("操作失败");
        }
    }


    /**
     * desc: 图片显示
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    @ApiOperation(value = "图片显示")
    @PostMapping("/showImg")
    @ResponseBody
    @RepeatSubmit
    public Object showImg(HttpServletResponse response, @RequestParam(value = "path") String path) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtil.readBytes(path));
            IoUtil.close(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
