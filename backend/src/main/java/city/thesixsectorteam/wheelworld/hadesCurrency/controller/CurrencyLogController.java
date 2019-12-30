package city.thesixsectorteam.wheelworld.hadesCurrency.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.hadesCurrency.domain.HadesCurrencyLog;
import city.thesixsectorteam.wheelworld.hadesCurrency.service.CurrencyLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类/方法名称：CurrencyLogController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：  codingZhangxin
 */
@Slf4j
@Validated
@RestController
@RequestMapping("currencyLog")
public class CurrencyLogController {

    @Autowired
    private CurrencyLogService currencyLogService;

    /**
     * @Author zmf
     * @Description //TODO 查询所有日志
     * @Date 16:29 2019/12/23
     * @Param [hadesCurrencyLog, beginTime, endTime]    日志对象,开始时间,结束时间
     * @return java.lang.String
    **/
    @Log("查询所有日志")
    @RequiresPermissions("currencyLog:list")
    @RequestMapping("queryAll")
    public String queryAll(HadesCurrencyLog hadesCurrencyLog,String beginTime,String endTime){
        return currencyLogService.queryAll(hadesCurrencyLog,beginTime,endTime);
    }
    
    /**
     * @Author zmf
     * @Description //TODO 删除日志记录（包含批量删除）
     * @Date 16:31 2019/12/23
     * @Param [ids]
     * @return java.lang.String
    **/
    @Log("删除日志")
    @RequestMapping("deteleByIds")
    @RequiresPermissions("currencyLog:delete")
    public String deteleByIds(String ids){
        return currencyLogService.deteleByIds(ids);
    }
}
