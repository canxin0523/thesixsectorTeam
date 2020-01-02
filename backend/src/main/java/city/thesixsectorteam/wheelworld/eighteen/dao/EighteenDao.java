package city.thesixsectorteam.wheelworld.eighteen.dao;

import city.thesixsectorteam.wheelworld.eighteen.domain.Eighteen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：EighteenDao
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Repository
public interface EighteenDao {
    List<Map<String, Object>> queryEightList(@Param("eight") Eighteen eighteen);

    boolean addEighteen(@Param("eight") Eighteen eighteen);

    boolean updateEighteen(@Param("eight") Eighteen eighteen);
}
