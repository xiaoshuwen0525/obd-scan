package com.ruoyi.web.controller.upload.service.impl; 

import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoListVO;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* UploadServiceImpl Tester. 
* @author <Authors name> 
* @since <pre>10/29/2020</pre> 
* @version 1.0 
*/ 
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UploadServiceImplTest { 
    
    @Autowired
    private UploadServiceImpl testUploadServiceImpl;
    
    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 
    
    /** 
    * Method: uploadInformation(ObdBoxVO obdBoxVO, MultipartFile multipartFile) 
    */ 
    @Test
    public void testUploadInformation() throws Exception {
//       String jobNumber ="1234987";
//        String boxCode = "DG||768660000102311";
//        String obdInfoVOList =
//                "[{'seq':null,'id':42,'boxId':null,'boxCode':null,'labelCode':null,'boxBelong':null,'obdName':null,'status':null,'obdPortInfoVOList':[{'seq':null,'id':null,'obdId':null,'portSer':1,'portCode':'DG||768660000102311','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':2,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':3,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':4,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':5,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':6,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':7,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':8,'portCode':'','status':null,'imgUrl':null}]},{'seq':null,'id':43,'boxId':null,'boxCode':null,'labelCode':null,'boxBelong':null,'obdName':null,'status':null,'obdPortInfoVOList':[{'seq':null,'id':null,'obdId':null,'portSer':1,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':2,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':3,'portCode':'518959847518959847','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':4,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':5,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':6,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':7,'portCode':'','status':null,'imgUrl':null},{'seq':null,'id':null,'obdId':null,'portSer':8,'portCode':'','status':null,'imgUrl':null}]}]";
//        String undefined = "undefined";
//        ObdBoxVO obdBoxVO = new ObdBoxVO();
//        if(!undefined.equals(boxCode) && StringUtils.isNotBlank(boxCode)){
//            obdBoxVO.setBoxCode(boxCode);
//        }
//        if(StringUtils.isNotBlank(jobNumber) && !undefined.equals(jobNumber)){
//            obdBoxVO.setJobNumber(jobNumber);
//        }
//        ObdInfoListVO obdInfoListVO = JSONUtil.toBean("{obdInfoVOList:" + obdInfoVOList + "}", ObdInfoListVO.class);
//        obdBoxVO.setObdInfoVOList(obdInfoListVO.getObdInfoVOList());
//        testUploadServiceImpl.uploadInformation(obdBoxVO);
    } 
    
    /** 
    * Method: uploadObdBox(ObdBox obdBox) 
    */ 
    @Test
    public void testUploadObdBox() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: uploadObdPicture(ObdBox obdBox, MultipartFile multipartFile) 
    */ 
    @Test
    public void testUploadObdPicture() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: uploadObdInfo(ObdInfo obdInfo) 
    */ 
    @Test
    public void testUploadObdInfo() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: uploadObdPost(ObdPortInfo obdPortInfo) 
    */ 
    @Test
    public void testUploadObdPost() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: obdBoxByJobNumber(String JobNumber) 
    */ 
    @Test
    public void testObdBoxByJobNumber() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: infoByBoxId(String boxId) 
    */ 
    @Test
    public void testInfoByBoxId() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: portByObdId(String obdId) 
    */ 
    @Test
    public void testPortByObdId() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: selectBoxById(String id) 
    */ 
    @Test
    public void testSelectBoxById() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: selectInfoById(String id) 
    */ 
    @Test
    public void testSelectInfoById() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: selectPortById(String id) 
    */ 
    @Test
    public void testSelectPortById() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: updateObdPort(ObdPortInfo obdPortInfo) 
    */ 
    @Test
    public void testUpdateObdPort() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: updateObdBox(ObdBox obdBox) 
    */ 
    @Test
    public void testUpdateObdBox() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: updateObdInfo(ObdInfo obdInfo) 
    */ 
    @Test
    public void testUpdateObdInfo() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: selectObdByJobNumber(String jobNumber, Integer pageNum, Integer pageSize) 
    */ 
    @Test
    public void testSelectObdByJobNumber() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: selectObdById(int id) 
    */ 
    @Test
    public void testSelectObdById() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * Method: updateObd(ObdBoxVO obdBoxVO) 
    */ 
    @Test
    public void testUpdateObd() throws Exception { 
    //TODO: Test goes here... 
    } 
    
        
    /** 
    * Method: getPortList(List<ObdPortInfoVO> list) 
    */ 
    @Test
    public void testGetPortList() throws Exception { 
    //TODO: Test goes here... 
        /* 
        try { 
           Method method = UploadServiceImpl.getClass().getMethod("getPortList", List<ObdPortInfoVO>.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
        } 
    
/** 
    * Method: changeStatus(String status) 
    */ 
    @Test
    public void testChangeStatus() throws Exception { 
    //TODO: Test goes here... 
        /* 
        try { 
           Method method = UploadServiceImpl.getClass().getMethod("changeStatus", String.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
        } 
    
/** 
    * Method: uploadPicture(String jobNumber, MultipartFile multipartFile) 
    */ 
    @Test
    public void testUploadPicture() throws Exception { 
    //TODO: Test goes here... 
        /* 
        try { 
           Method method = UploadServiceImpl.getClass().getMethod("uploadPicture", String.class, MultipartFile.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
        } 
    
/** 
    * Method: isNumber(String string) 
    */ 
    @Test
    public void testIsNumber() throws Exception { 
    //TODO: Test goes here... 
        /* 
        try { 
           Method method = UploadServiceImpl.getClass().getMethod("isNumber", String.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
        } 
    
} 
