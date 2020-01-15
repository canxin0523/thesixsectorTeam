package city.thesixsectorteam.wheelworld.hadesCurrency.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.hadesCurrency.domain.HadesCurrency;
import city.thesixsectorteam.wheelworld.hadesCurrency.service.CurrencyService;
import city.thesixsectorteam.wheelworld.system.domain.User;
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
 * 类/方法名称：CurrencyController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/19
 * 修改备注：
 */
@Slf4j
@Validated
@RestController
@RequestMapping("currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    /**
     * @Author zmf
     * @Description //TODO
     * @Date 10:26 2019/12/19
     * @Param 参数用户对象
     * @return java.lang.String
    **/
    @Log("查询，根据用户名称，用户状态，用户邮箱,用户id条件查询")
    @GetMapping("currencyList")
    @RequiresPermissions("currency:view")
    @ApiOperation(value = "获取列表",notes = "获取列表数据,支持根据用户名称，用户状态，用户邮箱进行条件查询",httpMethod = "GET")
    public List<Map<String, Object>> currencyList(User currency){
        return currencyService.getCurrencyList(currency);
    }
    /**
     * @Author zmf
     * @Description //TODO 
     * @Date 9:41 2019/12/23
     * @Param []  用户id  总数  id
     * @return java.lang.String
    **/
    @Log("添加或者修改")
    @PostMapping("saveUpdateCurrency")
    @RequiresPermissions("currency:add")
    @ApiOperation(value = "新增",notes = "新增一条记录,传入参数参考HadesCurrency实体类",httpMethod = "POST")
    public String addCurrency(HadesCurrency hadesCurrency){
        return currencyService.saveUpdateCurrency(hadesCurrency);
    }

}
