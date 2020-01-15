package city.thesixsectorteam.wheelworld.eighteen.dao;

import city.thesixsectorteam.wheelworld.eighteen.domain.EighteenLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：EighteenLogDao
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2020/1/2
 * 修改备注：
 */
@Repository
public interface EighteenLogDao {

    boolean saveLog(@Param("elog") EighteenLog eighteenLog);

    List<Map<String, Object>> logList(@Param("userName") String userName,@Param("eightName") String eightName);
}
