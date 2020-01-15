package city.thesixsectorteam.wheelworld.plague.dao;

import city.thesixsectorteam.wheelworld.plague.domain.Plague;
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
public interface PlagueDao extends BaseMapper<Plague> {
    /**
     * 查找所有的
     */
    List<Plague> findAll();

    /**
     * 根据 PLAGUE_ID 查找单个
     */
    Plague findById(@Param("plagueId") Long plagueId);
    /**
     * 根据 plague 进行更新
     */
    void updateByPlagueEntity(@Param("plague") Plague plague);
    /**
     * 新增 PLAGUE
     */
    void addPlague(@Param("plague") Plague plague);
    /**
     * 通过PLAGUE_ID删除单个 plague
     */
    void deleteByPlague(@Param("plagueId") Long plagueId);
    /**
     * 通过PLAGUE_ID 批量删除
     */
    void deleteByPlagues(@Param("list") List list);
}
