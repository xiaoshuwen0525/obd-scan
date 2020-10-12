package com.ruoyi.upload.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.upload.service.impl.QueryInfoServiceImpl;
import com.ruoyi.upload.domain.ViewObd;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 曾志伟
 * @date: 2020-09-30 11:28
 */
@Controller
@RequestMapping("/upload")
public class QueryInfoController extends BaseController {

    @Autowired
    private QueryInfoServiceImpl iQueryInfoService;

    @ApiOperation(value = "查询机箱OBD和端口的所有信息")
    @GetMapping("/queryInfo")
    public AjaxResult queryInfo() {
        return AjaxResult.success(iQueryInfoService.queryInfo());
    }

    @ApiOperation(value = "根据工号查询该工号下的所有信息")
    @GetMapping("/queryInfoByJobId")
    public AjaxResult queryInfoByJobId(ViewObd viewObd) {
        return AjaxResult.success(iQueryInfoService.queryInfoByJobId(viewObd));
    }

    @ApiOperation(value = "根据工号和机箱号查询该工号下的所有指定机箱信息")
    @GetMapping("/queryInfoForBox")
    public AjaxResult queryInfoForBox(ViewObd viewObd) {
        return AjaxResult.success(iQueryInfoService.queryInfoForBox(viewObd));
    }

    @ApiOperation(value = "根据工号和机箱号和OBD号查询该工号下的所有指定OBD信息")
    @GetMapping("/queryInfoForObd")
    public AjaxResult queryInfoForObd(ViewObd viewObd) {
        return AjaxResult.success(iQueryInfoService.queryInfoForObd(viewObd));
    }

    @ApiOperation(value = "根据工号和机箱号和OBD号和端口号查询该工号下的所有指定端口信息")
    @GetMapping("/queryInfoForPort")
    public AjaxResult queryInfoForPort(ViewObd viewObd) {
        return AjaxResult.success(iQueryInfoService.queryInfoForPort(viewObd));
    }
}
