package city.thesixsectorteam.wheelworld.eighteen.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.eighteen.domain.EighteenLog;
import city.thesixsectorteam.wheelworld.eighteen.service.EighteenLogService;
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
 * 类/方法名称：EighteenLogController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2020/1/2
 * 修改备注：
 */
@Slf4j
@Validated
@RestController
@RequestMapping("eightLog")
public class EighteenLogController {

    @Autowired
    private EighteenLogService eighteenLogService;

    @Log("保存18日志记录信息")
    @PostMapping("saveLog")
    @RequiresPermissions("eightLog:add")
    @ApiOperation(value = "新增",notes = "新增一条数据,传入参数为EighteenLog实体属性",httpMethod = "POST")
    public String saveLog(EighteenLog eighteenLog){
        return eighteenLogService.saveLog(eighteenLog);
    }

    @Log("查询出数据用户信息，狱名信息，日志信息")
    @GetMapping("logList")
    @RequiresPermissions("eightLog:view")
    @ApiOperation(value = "查询列表信息",notes = "支持条件查询,参数为userName,eightName两个",httpMethod = "GET")
    public List<Map<String,Object>> logList(String userName,String eightName){
        return eighteenLogService.logList(userName,eightName);
    }
}
