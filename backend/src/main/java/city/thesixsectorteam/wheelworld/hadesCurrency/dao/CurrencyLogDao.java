package city.thesixsectorteam.wheelworld.hadesCurrency.dao;

import city.thesixsectorteam.wheelworld.hadesCurrency.domain.HadesCurrencyLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类/方法名称：CurrencyLogDao
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 */
@Repository
public interface CurrencyLogDao {

    void saveCurrencyLog(@Param("log") HadesCurrencyLog hadesCurrencyLog);

    List<HadesCurrencyLog> queryAll(@Param("log") HadesCurrencyLog hadesCurrencyLog,@Param("beginTime") String beginTime,@Param("endTime") String endTime);

    void deteleByIds(@Param("id") String str);
}
