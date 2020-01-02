package city.thesixsectorteam.wheelworld.eighteen.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.eighteen.domain.EighteenLog;
import city.thesixsectorteam.wheelworld.eighteen.service.EighteenLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    @RequestMapping("saveLog")
    @RequiresPermissions("eightLog:add")
    public String saveLog(EighteenLog eighteenLog){
        return eighteenLogService.saveLog(eighteenLog);
    }

    @Log("查询出数据用户信息，狱名信息，日志信息")
    @RequestMapping("logList")
    @RequiresPermissions("eightLog:view")
    public List<Map<String,Object>> logList(String userName,String eightName){
        return eighteenLogService.logList(userName,eightName);
    }
}
