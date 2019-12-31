package city.thesixsectorteam.wheelworld.lifeAndDie.dao;

import city.thesixsectorteam.wheelworld.lifeAndDie.domain.LifeAndDie;
import city.thesixsectorteam.wheelworld.system.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：LifeAndDieDao
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/30
 * 修改备注：
 */
@Repository
public interface LifeAndDieDao {

    List<Map<String,Object>> getListByLife(@Param("user") User user, @Param("life") LifeAndDie life);

    void saveLife(@Param("life") LifeAndDie lifeAndDie);

    void updateLifeByStatu();

    void updateStatuByOverAge();

    boolean updateTotalAge(@Param("life")LifeAndDie life);
}
