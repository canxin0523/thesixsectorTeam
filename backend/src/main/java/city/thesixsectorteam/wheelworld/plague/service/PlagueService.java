package city.thesixsectorteam.wheelworld.plague.service;

import city.thesixsectorteam.wheelworld.plague.dao.PlagueDao;
import city.thesixsectorteam.wheelworld.plague.domain.Plague;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：PlagueService
 * 类/方法描述：
 * 创建人：mango
 * 创建时间： 2019/12/28
 * 修改备注：
 */
public interface PlagueService extends IService<Plague> {
    /**
     * 查找所有瘟疫信息
     */
    Map findAll();
    /**
     * 新增瘟疫
     */
    void savePlague(Plague plague);
    /**
     * 删除瘟疫
     */
    void deletePlagueById(String plagueId);
}
