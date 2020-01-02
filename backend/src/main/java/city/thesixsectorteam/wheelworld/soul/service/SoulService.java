package city.thesixsectorteam.wheelworld.soul.service;

import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.common.utils.SixsectorUtil;
import city.thesixsectorteam.wheelworld.common.utils.Util;
import city.thesixsectorteam.wheelworld.lifeAndDie.domain.LifeAndDie;
import city.thesixsectorteam.wheelworld.lifeAndDie.service.LifeAndDieService;
import city.thesixsectorteam.wheelworld.soul.dao.SoulDao;
import city.thesixsectorteam.wheelworld.soul.domain.Soul;
import city.thesixsectorteam.wheelworld.system.domain.User;
import city.thesixsectorteam.wheelworld.trial.domain.Trial;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：SoulService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Service
@Transactional
public class SoulService {

    @Autowired
    private SoulDao soulDao;
    @Autowired
    private LifeAndDieService lifeAndDieService;
    
    /**
     * @Author zmf
     * @Description //TODO 查询出所有的状态为2的ssb的数据，然后保存 定时执行
     * @Date 15:06 2019/12/31
     * @Param []
     * @return void
    **/
    public void saveSoul() {
        User user = new User();
        user.setStatus("1");
        LifeAndDie lifeAndDie = new LifeAndDie();
        lifeAndDie.setStatu(2);
        List<Map<String, Object>> list = lifeAndDieService.getListByLife(user, lifeAndDie);
        for (Map<String, Object> map:list) {
            Soul soul = new Soul();
            soul.setUserId((long) Integer.parseInt(map.get("USER_ID").toString()));
            soul.setExecutorStatus(1);
            soul.setExecutorTime(new Date());
            soulDao.savesoul(soul);
        }
    }

    /**
     * @Author zmf
     * @Description //TODO 修改信息
     * @Date 15:40 2019/12/31
     * @Param [soul]
     * @return java.lang.String
    **/
    public String updateSoul(Soul soul) {
        soul.setExecutorId(SixsectorUtil.getCurrentUser().getUserId());
        soul.setExecutorTime(new Date());
        soul.setExecutorStatus(2);
        boolean bo = soulDao.updateSoul(soul);
        if(bo){
            return MsgUtil.success();
        }
        return MsgUtil.fail();
    }
    
    /**
     * @Author zmf
     * @Description //TODO
     * @Date 16:09 2019/12/31
     * @Param [soul]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    public List<Map<String, Object>> queryList(Soul soul) {
        List<Map<String,Object>> list = soulDao.queryList(soul);
        return Util.mapRtnFmt(list,"executorTime");
    }
}
