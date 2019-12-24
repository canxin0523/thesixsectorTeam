package city.thesixsectorteam.wheelworld.hadesCurrency.service;

import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.hadesCurrency.dao.CurrencyLogDao;
import city.thesixsectorteam.wheelworld.hadesCurrency.domain.HadesCurrencyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 类/方法名称：CurrencyLogService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 */
@Service
@Transactional
public class CurrencyLogService {

    @Autowired
    private CurrencyLogDao currencyLogDao;

    public void saveCurrencyLog(HadesCurrencyLog hadesCurrencyLog){
        currencyLogDao.saveCurrencyLog(hadesCurrencyLog);
    }

    public String queryAll(HadesCurrencyLog hadesCurrencyLog,String beginTime,String endTime) {
        List<HadesCurrencyLog> list = currencyLogDao.queryAll(hadesCurrencyLog,beginTime,endTime);
        return MsgUtil.success(list, Date.class);
    }

    public String deteleByIds(String ids) {
        String[] split = ids.split(",");
        for (String str:split) {
            System.out.println("删除日志");
            currencyLogDao.deteleByIds(str);
        }
        return MsgUtil.success();
    }
}
