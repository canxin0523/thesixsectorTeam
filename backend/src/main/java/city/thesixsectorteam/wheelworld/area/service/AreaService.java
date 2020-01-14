package city.thesixsectorteam.wheelworld.area.service;

import city.thesixsectorteam.wheelworld.area.dao.AreaDao;
import city.thesixsectorteam.wheelworld.area.domain.Area;
import city.thesixsectorteam.wheelworld.common.exception.SixsectorException;
import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 类/方法名称：AreaService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 *          @Transactional 注解不生效的场景,spring的事务隔离级别
 *
 *       REQUIRED：如果存在一个事务，则支持当前事务。如果没有事务则开启一个新的事务。
 *       REPEATABLE_READ：这种事务隔离级别可以防止脏读，不可重复读。但是可能出现幻像读。它除了保证一个事务不能读取另一个事务未提交的数据外，还保证了避免下面的情况产生(不可重复读)
 *       readOnly：不允许只读 rollbackFor:回滚策略为Exception出现异常之后
 *       TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 函数内捕获异常时需要来设置事务回滚状态
 *
 *       Spring Transactional一直是RD的事务神器，但是如果用不好，反会伤了自己。
 *       下面总结@Transactional经常遇到的几个场景:
 *         @Transactional 加于private方法, 无效
 *         @Transactional 加于未加入接口的public方法, 再通过普通接口方法调用, 无效
 *         @Transactional 加于接口方法, 无论下面调用的是private或public方法, 都有效
 *         @Transactional 加于接口方法后, 被本类普通接口方法直接调用, 无效
 *         @Transactional 加于接口方法后, 被本类普通接口方法通过接口调用, 有效
 *         @Transactional 加于接口方法后, 被它类的接口方法调用, 有效
 *         @Transactional 加于接口方法后, 被它类的私有方法调用后, 有效
 *
 */
@Service
@Transactional
public class AreaService {

    @Autowired
    private AreaDao areaDao;
    
    /**
     * @Author zmf
     * @Description //TODO 查询所有地区
     * @Date 16:33 2019/12/23
     * @Param [area]
     * @return java.lang.String
    **/
    public List<Area> queryAll(Area area) {
        List<Area> list = areaDao.queryAll(area);
        return list;
    }
    
    /**
     * @Author zmf
     * @Description //TODO 新增地区
     * @Date 17:13 2019/12/23
     * @Param [area]
     * @return java.lang.String
    **/
    public String addArea(Area area) {
        areaDao.addArea(area);
        return MsgUtil.success();
    }
    
    /**
     * @Author zmf
     * @Description //TODO 地区修改
     * @Date 14:43 2019/12/24
     * @Param [area]
     * @return java.lang.String
    **/
    public String updateArea(Area area) {
        if(area!=null){
            areaDao.updateArea(area);
            return MsgUtil.success();
        }else{
            return MsgUtil.fail("地区对象为空");
        }
    }
    
    /**
     * @Author zmf
     * @Description //TODO  地区删除
     * @Date 14:59 2019/12/24
     * @Param [ids]
     * @return java.lang.String
    **/
    public String deteleArea(String ids) throws SixsectorException {
        try {
            String[] split = ids.split(",");
            for (String str:split) {
                areaDao.deteleArea(str);
            }
            return MsgUtil.success();
        }catch (Exception e){
            String msg = "删除失败";
            throw new SixsectorException(msg);
        }
    }
}
