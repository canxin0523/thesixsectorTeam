package city.thesixsectorteam.wheelworld.plague.service.impl;

import city.thesixsectorteam.wheelworld.plague.dao.PlagueDao;
import city.thesixsectorteam.wheelworld.plague.dao.PlagueInfoDao;
import city.thesixsectorteam.wheelworld.plague.domain.Plague;
import city.thesixsectorteam.wheelworld.plague.service.PlagueService;
import city.thesixsectorteam.wheelworld.system.dao.DeptMapper;
import city.thesixsectorteam.wheelworld.system.domain.Dept;
import city.thesixsectorteam.wheelworld.system.service.DeptService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Slf4j
@Service("plagueService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlagueServiceImpl extends ServiceImpl<PlagueDao, Plague> implements PlagueService {

    @Autowired
    PlagueDao plagueDao;
    @Autowired
    PlagueInfoDao plagueInfoDao;

    /**
     * 查找所有瘟疫信息
     */
    @Override
    public Map findAll() {
        Map map = new HashMap();
        map.put("plague_rows",plagueDao.findAll());
        return map;
    }

    /**
     * 新增瘟疫
     * @param plague
     */
    @Override
    @Transactional
    public void savePlague(Plague plague) {
        if(plague.getCreateTime() == null){
            plague.setCreateTime(new Date());
        }
        this.save(plague);
    }

    /**
     * 删除瘟疫
     *
     * @param plagueId
     */
    @Override
    @Transactional
    public void deletePlagueById(String plagueId) {
        if(plagueId != null){
            Long id = Long.valueOf(plagueId);
            Plague plague = plagueDao.findById(id);
            if(plague == null){
                return;
            }
            try {
                //删除详细
                int i = plagueInfoDao.deleteById(id);
                if( i == 0){
                    log.warn("删除瘟疫详细失败。");
                }
                plagueDao.deleteByPlague(id);
            }catch (Exception e){
                log.error("删除瘟疫发生异常",e.getMessage());
                return;
            }
        }else {
            log.error("删除瘟疫发生异常,瘟疫id为空");
        }
    }

}
