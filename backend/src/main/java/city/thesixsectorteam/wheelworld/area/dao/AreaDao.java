package city.thesixsectorteam.wheelworld.area.dao;

import city.thesixsectorteam.wheelworld.area.domain.Area;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类/方法名称：AreaDao
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 *
 *             @Repository  注解 后有默认value值  也可以等与  @Repository("area")
 *             在其他的业务实现类中需要用到当前类中的某个方法是可以直接注入
 *              如：
 *              public class  XXXXservice{
 *                  @Autowirte
 *                  AreaDao area
 *              }
 *
 *              之后可以直接调用dao层中的方法
 */
@Repository
public interface AreaDao {

    List<Area> queryAll(@Param("area") Area area);

    void addArea(@Param("area") Area area);

}
