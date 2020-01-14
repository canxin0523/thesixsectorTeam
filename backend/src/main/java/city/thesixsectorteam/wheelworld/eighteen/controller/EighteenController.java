package city.thesixsectorteam.wheelworld.eighteen.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.eighteen.domain.Eighteen;
import city.thesixsectorteam.wheelworld.eighteen.service.EighteenService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("queryEightList")
    @RequiresPermissions("eight:view")
    @ApiOperation(value = "查询列表",notes = "查询出列表信息,条件查询参数为eightName",httpMethod = "GET")
    public List<Map<String,Object>> queryEightList(Eighteen eighteen){
        return eighteenService.queryEightList(eighteen);
    }


    @Log("新增数据")
    @PostMapping("addEighteen")
    @RequiresPermissions("eight:add")
    @ApiOperation(value = "新增",notes = "新增一条数据,传入参数为Eighteen实体属性",httpMethod = "POST")
    public String  addEighteen(Eighteen eighteen){
        return eighteenService.addEighteen(eighteen);
    }

    @Log("修改数据")
    @PostMapping("updateEighteen")
    @RequiresPermissions("eight:update")
    @ApiOperation(value = "修改",notes = "修改一条数据,传入参数为Eighteen实体属性",httpMethod = "POST")
    public String updateEighteen(Eighteen eighteen){
        return eighteenService.updateEighteen(eighteen);
    }
}
