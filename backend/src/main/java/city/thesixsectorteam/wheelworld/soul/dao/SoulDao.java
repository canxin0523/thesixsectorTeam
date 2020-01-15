package city.thesixsectorteam.wheelworld.soul.dao;

import city.thesixsectorteam.wheelworld.soul.domain.Soul;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：SoulDao
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Repository
public interface SoulDao {

    void savesoul(@Param("soul") Soul soul);

    boolean updateSoul(@Param("soul")Soul soul);

    List<Map<String, Object>> queryList(@Param("soul") Soul soul);
}
