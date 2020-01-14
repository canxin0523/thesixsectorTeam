package city.thesixsectorteam.wheelworld.plague.dao;

import city.thesixsectorteam.wheelworld.plague.domain.Plague;
import city.thesixsectorteam.wheelworld.plague.domain.PlagueInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类/方法名称：PlagueDao
 * 类/方法描述：
 * 创建人：mango
 * 创建时间： 2019/12/28
 * 修改备注：
 */
@Repository
public interface PlagueInfoDao extends BaseMapper<PlagueInfo> {
    /**
     * 根据主键查询
     */
    List<PlagueInfo> findPlagueInfoByPrimaryKey(@Param("plagueInfoId") Long plagueInfoId);
    /**
     * 根据瘟疫id查询
     */
    List<PlagueInfo> findPlagueInfoByPlagueId(@Param("plagueId") Long plagueId);
    /**
     * 根据发生地区的id查询
     */
    List<PlagueInfo> findPlagueInfoByAreaId(@Param("areaId") Long areaId);

}

