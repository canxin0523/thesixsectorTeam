package city.thesixsectorteam.wheelworld.area.controller;

import city.thesixsectorteam.wheelworld.area.domain.Area;
import city.thesixsectorteam.wheelworld.area.service.AreaService;
import city.thesixsectorteam.wheelworld.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类/方法名称：AreaController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 */
@Slf4j
@Validated
@RestController
@RequestMapping("area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @Log("查询所有地区")
    @RequestMapping("queryAll")
    public String queryAll(Area area){
        return areaService.queryAll(area);
    }

    @Log("新增地区信息")
    @RequestMapping("addArea")
    public String addArea(Area area){
        return areaService.addArea(area);
    }

    @Log("poi批量导入地区数据")
    @RequestMapping("excelEnter")
    public String  excelEnter(){
        return null;
    }
}
