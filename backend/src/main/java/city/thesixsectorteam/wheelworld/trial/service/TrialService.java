package city.thesixsectorteam.wheelworld.trial.service;

import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.common.utils.SixsectorUtil;
import city.thesixsectorteam.wheelworld.common.utils.Util;
import city.thesixsectorteam.wheelworld.lifeAndDie.domain.LifeAndDie;
import city.thesixsectorteam.wheelworld.lifeAndDie.service.LifeAndDieService;
import city.thesixsectorteam.wheelworld.soul.domain.Soul;
import city.thesixsectorteam.wheelworld.soul.service.SoulService;
import city.thesixsectorteam.wheelworld.system.domain.User;
import city.thesixsectorteam.wheelworld.trial.dao.TrialDao;
import city.thesixsectorteam.wheelworld.trial.domain.Trial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：TrialService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Service
@Transactional
public class TrialService {

    @Autowired
    private TrialDao trialDao;
    @Autowired
    private SoulService soulService;
    
    /**
     * @Author zmf
     * @Description //TODO  定时查询出状态为2的数据  然后存库状态为1 待审
     * @Date 10:08 2019/12/31
     * @Param []
     * @return void
    **/
    public void saveTrial() {
        Soul soul = new Soul();
        soul.setExecutorStatus(2);
        List<Map<String, Object>> list = soulService.queryList(soul);
        for (Map<String, Object> map:list) {
            Trial trial = new Trial();
            trial.setUserId((long) Integer.parseInt(map.get("userId").toString()));
            trial.setType(1);
            trial.setTrialTime(new Date());
            trialDao.saveTrial(trial);
        }

    }
    
    /**
     * @Author zmf
     * @Description //TODO 根据传过来的参数进行查询数据  条件查询  没条件就查询全部 默认加载全部
     * @Date 10:37 2019/12/31
     * @Param [trial]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    public List<Map<String, Object>> queryList(Trial trial) {
        List<Map<String, Object>> list = trialDao.queryList(trial);
        return Util.mapRtnFmt(list,"trialTime");
    }
    
    
    
    /**
     * @Author zmf
     * @Description //TODO  修改数据  审问就是修改 将审判人信息修改就好
     * @Date 11:39 2019/12/31
     * @Param [trial]
     * @return java.lang.String
    **/
    public String trialUpdate(Trial trial) {
        trial.setTrialUserId(SixsectorUtil.getCurrentUser().getUserId());
        trial.setTrialTime(new Date());
        trial.setType(2);
        boolean bo = trialDao.trialUpdate(trial);
        if(bo){
            return MsgUtil.success();
        }
        return MsgUtil.fail();
    }
}
