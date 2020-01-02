package city.thesixsectorteam.wheelworld.eighteen.service;

import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.common.utils.Util;
import city.thesixsectorteam.wheelworld.eighteen.dao.EighteenDao;
import city.thesixsectorteam.wheelworld.eighteen.domain.Eighteen;
import city.thesixsectorteam.wheelworld.trial.service.TrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 类/方法名称：EighteenService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Service
@Transactional
public class EighteenService {

    @Autowired
    private EighteenDao eighteenDao;
    @Autowired
    private TrialService trialService;

    public void saveEighteen() {
    }

    /**
     * @Author zmf
     * @Description //TODO 查询出所有数据
     * @Date 17:23 2019/12/31
     * @Param [eighteen]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    public List<Map<String, Object>> queryEightList(Eighteen eighteen) {
        List<Map<String, Object>> list = eighteenDao.queryEightList(eighteen);
        return Util.mapRtnFmt(list,"createTime");
    }
    
    /**
     * @Author zmf
     * @Description //TODO 新增数据
     * @Date 17:42 2019/12/31
     * @Param [eighteen]
     * @return java.lang.String
    **/
    public String addEighteen(Eighteen eighteen) {
        eighteen.setCreateTime(new Date());
        boolean bo =  eighteenDao.addEighteen(eighteen);
        if(bo){
            return MsgUtil.success();
        }
        return MsgUtil.fail();
    }
    
    /**
     * @Author zmf
     * @Description //TODO 修改数据
     * @Date 17:56 2019/12/31
     * @Param [eighteen]
     * @return java.lang.String
    **/
    public String updateEighteen(Eighteen eighteen) {
        eighteen.setCreateTime(new Date());
        boolean bo = eighteenDao.updateEighteen(eighteen);
        if(bo){
            return MsgUtil.success();
        }
        return MsgUtil.fail();
    }
}
