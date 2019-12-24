package city.thesixsectorteam.wheelworld.reincarnation.service;

import city.thesixsectorteam.wheelworld.reincarnation.dao.ReincarnationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类/方法名称：ReincarnationService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/19
 * 修改备注：
 */
@Service
@Transactional
public class ReincarnationService {

    @Autowired
    private ReincarnationDao reincarnationDao;
}
