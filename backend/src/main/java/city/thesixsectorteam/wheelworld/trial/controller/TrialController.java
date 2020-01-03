package city.thesixsectorteam.wheelworld.trial.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.common.utils.Util;
import city.thesixsectorteam.wheelworld.trial.domain.Trial;
import city.thesixsectorteam.wheelworld.trial.service.TrialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：TrialController
 * 类/方法描述：审判
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Slf4j
@Validated
@RestController
@RequestMapping("trial")
public class TrialController {

    @Autowired
    private TrialService trialService;

    /**
     * @Author zmf
     * @Description //TODO
     * @Date 10:05 2019/12/31
     * @Param []
     * @return void
    **/
    @Log("进入待审状态")
    @Scheduled(cron="0 0 4 * * ?" )
    public void saveTrial(){
        trialService.saveTrial();
    }
    /**
     * @Author zmf
     * @Description //TODO type 1/2
     * @Date 13:57 2019/12/31
     * @Param [trial]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @Log("根据type来查询待审还是已审")
    @GetMapping("queryList")
    @RequiresPermissions("trial:view")
    public List<Map<String,Object>> queryList(Trial trial){
        return trialService.queryList(trial);
    }
    /**
     * @Author zmf
     * @Description //TODO id 描述
     * @Date 13:57 2019/12/31
     * @Param [trial]
     * @return java.lang.String
    **/
    @Log("审问")
    @PostMapping("trialUpdate")
    @RequiresPermissions("trial:update")
    public String  trialUpdate(Trial trial){
        return trialService.trialUpdate(trial);
    }

}
