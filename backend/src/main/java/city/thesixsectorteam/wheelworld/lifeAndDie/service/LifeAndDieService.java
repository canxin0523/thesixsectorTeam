package city.thesixsectorteam.wheelworld.lifeAndDie.service;

import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.lifeAndDie.dao.LifeAndDieDao;
import city.thesixsectorteam.wheelworld.lifeAndDie.domain.LifeAndDie;
import city.thesixsectorteam.wheelworld.system.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：LifeAndDieService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/30
 * 修改备注：
 */
@Service
@Transactional
public class LifeAndDieService {

    @Autowired
    private LifeAndDieDao lifeAndDieDao;
    /**
     * @Author zmf
     * @Description //TODO 生集合
     * @Date 15:23 2019/12/30
     * @Param [life]
     * @return java.lang.String
    **/
    public List<Map<String,Object>> getListByLife(User user,LifeAndDie life) {
        if (life.getStatu()==null) return null;
        return lifeAndDieDao.getListByLife(user,life);
    }

    /**
     * @Author zmf
     * @Description //TODO 注册成功的时候调用当前接口 保存生死簿日志
     * @Date 16:47 2019/12/30
     * @Param [user]
     * @return void
    **/
    public void saveLife(User user){
        LifeAndDie lifeAndDie = new LifeAndDie();
        lifeAndDie.setAge(user.getAge());
        lifeAndDie.setCreateTime(new Date());
        lifeAndDie.setTotalAge(100);
        lifeAndDie.setOverAge(100-user.getAge());
        lifeAndDie.setStatu(1);
        lifeAndDie.setUserId(user.getUserId());
        lifeAndDieDao.saveLife(lifeAndDie);
    }
    
    /**
     * @Author zmf
     * @Description //TODO 修改年龄--修改状态
     * @Date 16:50 2019/12/30
     * @Param []
     * @return void
    **/
    public void updateLifeByStatu() {
        lifeAndDieDao.updateLifeByStatu();
    }
    public void updateStatuByOverAge() {
        lifeAndDieDao.updateStatuByOverAge();
    }
    /**
     * @Author zmf
     * @Description //TODO 根据用户id修改
     * @Date 17:13 2019/12/30
     * @Param [user]
     * @return java.lang.String
    **/
    public String updateTotalAge(LifeAndDie life) {
        boolean b = lifeAndDieDao.updateTotalAge(life);
        if(b){
            return MsgUtil.success();
        }
        return MsgUtil.fail();
    }
}
