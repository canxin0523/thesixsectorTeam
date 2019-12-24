package city.thesixsectorteam.wheelworld.hadesCurrency.dao;

import city.thesixsectorteam.wheelworld.hadesCurrency.domain.HadesCurrency;
import city.thesixsectorteam.wheelworld.system.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：CurrencyDao
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/19
 * 修改备注：
 */
@Repository
public interface CurrencyDao {

    List<Map<String,Object>>getCurrencyList(@Param("user") User currency);
    //修改
    void updateCurrency(@Param("currency") HadesCurrency hadesCurrency);
    //新增
    void saveCurrency(@Param("currency") HadesCurrency hadesCurrency);
}
