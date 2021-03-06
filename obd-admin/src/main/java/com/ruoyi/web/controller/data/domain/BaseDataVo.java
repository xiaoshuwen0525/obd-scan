package com.ruoyi.web.controller.data.domain;

import com.ruoyi.common.utils.StringUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 曾志伟
 * @date: 2020-10-27 16:58
 */
@Data
public class BaseDataVo {

    private Integer boxId;

    private String boxCode;

    private String labelCode;

    private String campService;

    private String boxName;

    private String area;

    private String businessBureau;

    private int obdSize;

    private List<Integer> id;

    private List<String> obdName;

    private List<String> boxBelong;

    private List<Integer> portCount;

    private List<String> boxUniqueId;

    private List<BaseUpdate> baseUpdateList;


    public void makeBaseUpdateList() {
        List<BaseUpdate> baseUpdates = new ArrayList<>();
        for (int i = 0; i < obdSize; i++) {
            BaseUpdate baseUpdate = new BaseUpdate();
            baseUpdate.setBoxId(boxId);
            baseUpdate.setArea(area);
            baseUpdate.setCampService(campService);
            baseUpdate.setBusinessBureau(businessBureau);
            baseUpdate.setLabelCode(labelCode);
            baseUpdate.setBoxCode(boxCode);
            baseUpdate.setBoxName(boxName);
            baseUpdate.setBoxBelong(boxBelong.get(i));
            baseUpdate.setObdName(obdName.get(i));
            baseUpdate.setPortCount(portCount.get(i));
            if (CollectionUtils.isEmpty(boxUniqueId)) {
                baseUpdate.setBoxUniqueId(null);
            } else {
                baseUpdate.setBoxUniqueId(boxUniqueId.get(i));
            }
            baseUpdate.setId(id.get(i));
            baseUpdates.add(baseUpdate);
        }
        this.baseUpdateList = baseUpdates;
    }

    public void makeBaseUpdateListNull() {
        List<BaseUpdate> baseUpdates = new ArrayList<>();
        for (int i = 0; i < obdSize; i++) {
            BaseUpdate baseUpdate = new BaseUpdate();
            baseUpdate.setBoxId(boxId);
            baseUpdate.setArea(area);
            baseUpdate.setCampService(campService);
            baseUpdate.setBusinessBureau(businessBureau);
            baseUpdate.setLabelCode(labelCode);
            baseUpdate.setBoxCode(boxCode);
            baseUpdate.setBoxName(boxName);
            baseUpdate.setBoxBelong(boxBelong.get(i));
            baseUpdate.setObdName("");
            baseUpdate.setPortCount(0);
            if (CollectionUtils.isEmpty(boxUniqueId)) {
                baseUpdate.setBoxUniqueId(null);
            } else {
                baseUpdate.setBoxUniqueId(boxUniqueId.get(i));
            }
            baseUpdate.setId(id.get(i));
            baseUpdates.add(baseUpdate);
        }
        this.baseUpdateList = baseUpdates;
    }

}
