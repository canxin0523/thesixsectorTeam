package city.thesixsectorteam.wheelworld.eighteen.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.eighteen.domain.Eighteen;
import city.thesixsectorteam.wheelworld.eighteen.service.EighteenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：EighteenController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Slf4j
@Validated
@RestController
@RequestMapping("eight")
public class EighteenController {

    @Autowired
    private EighteenService eighteenService;

    @Log("查询所有")
    @RequestMapping("queryEightList")
    @RequiresPermissions("eight:view")
    public List<Map<String,Object>> queryEightList(Eighteen eighteen){
        return eighteenService.queryEightList(eighteen);
    }


    @Log("新增数据")
    @RequestMapping("addEighteen")
    @RequiresPermissions("eight:add")
    public String  addEighteen(Eighteen eighteen){
        return eighteenService.addEighteen(eighteen);
    }

    @Log("修改数据")
    @RequestMapping("updateEighteen")
    @RequiresPermissions("eight:update")
    public String updateEighteen(Eighteen eighteen){
        return eighteenService.updateEighteen(eighteen);
    }
}
