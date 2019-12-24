package city.thesixsectorteam.wheelworld.hadesCurrency.service;

import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.common.utils.Util;
import city.thesixsectorteam.wheelworld.hadesCurrency.dao.CurrencyDao;
import city.thesixsectorteam.wheelworld.hadesCurrency.domain.HadesCurrency;
import city.thesixsectorteam.wheelworld.hadesCurrency.domain.HadesCurrencyLog;
import city.thesixsectorteam.wheelworld.system.domain.User;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * 类/方法名称：CurrencyService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/19
 * 修改备注：
 */
@Service
@Transactional
public class CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private CurrencyLogService currencyLogService;
    /**
     * @Author zmf
     * @Description //TODO
     * @Date 10:27 2019/12/19
     * @Param [currency]
     * @return java.lang.String
    **/
    public List<Map<String, Object>> getCurrencyList(User currency) {
        List<Map<String, Object>> currencyList = currencyDao.getCurrencyList(currency);
        return Util.mapRtnFmt(currencyList, "CREATE_TIME,MODIFY_TIME,LAST_LOGIN_TIME");
    }

    public String saveUpdateCurrency(HadesCurrency hadesCurrency) {
        int status = 0;
        String str = "";
        if(StringUtil.isNotEmpty(String.valueOf(hadesCurrency.getId()))){   //修改
            currencyDao.updateCurrency(hadesCurrency);
            status = 2;
            str = "消费"+hadesCurrency.getTotal()+"MB";
        }else{
            currencyDao.saveCurrency(hadesCurrency);
            status = 1;//新增
            str = "充值"+hadesCurrency.getTotal()+"MB";
        }
        HadesCurrencyLog hadesCurrencyLog = new HadesCurrencyLog();
        hadesCurrencyLog.setCreateTime(new Date());
        hadesCurrencyLog.setStatus(status);
        hadesCurrencyLog.setUserId(hadesCurrency.getUserId());
        hadesCurrencyLog.setInfo(str);
        currencyLogService.saveCurrencyLog(hadesCurrencyLog);
        return MsgUtil.success();
    }
}
