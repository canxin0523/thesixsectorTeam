package city.thesixsectorteam.wheelworld.plague.service.impl;


import city.thesixsectorteam.wheelworld.plague.dao.PlagueInfoDao;
import city.thesixsectorteam.wheelworld.plague.domain.PlagueInfo;
import city.thesixsectorteam.wheelworld.plague.service.PlagueInfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("plagueInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlagueInfoServiceImpl extends ServiceImpl<PlagueInfoDao, PlagueInfo> implements PlagueInfoService {

    @Autowired
    PlagueInfoDao plagueInfoDao;
    /**
     * 更加瘟疫Id 查询瘟疫详细
     * @param plagueId
     */
    @Override
    public Map findPlagueInfoByPlagueId(String plagueId) {
        Long idStr = Long.valueOf(plagueId);
        List<PlagueInfo> infoList = plagueInfoDao.findPlagueInfoByPlagueId(idStr);
        if(infoList.size() == 1 ){
            Map map = new HashMap();
            map.put("plagueInfo",infoList.get(0));
            return map;
        }else{
            return  null;
        }
    }

    /**
     * 添加瘟疫详细
     *
     * @param plagueInfo
     */
    @Override
    public void addPlagueInfo(PlagueInfo plagueInfo) {
        if(plagueInfo.getHappenTime() == null){
            plagueInfo.setHappenTime(new Date());
        }
        this.save(plagueInfo);
    }

    /**
     * 更新瘟疫详细
     *
     * @param plagueInfo
     */
    @Override
    public void updatePlagueInfo(PlagueInfo plagueInfo) {
        PlagueInfo info = this.getById(plagueInfo.getPlagueInfoId());
        if(info == null ){
            this.addPlagueInfo(plagueInfo);
        }else{
            this.updateById(plagueInfo);
        }
    }


}
