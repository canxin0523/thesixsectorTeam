package city.thesixsectorteam.wheelworld.system.service;

import city.thesixsectorteam.wheelworld.common.domain.QueryRequest;
import city.thesixsectorteam.wheelworld.system.domain.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface DictService extends IService<Dict> {

    IPage<Dict> findDicts(QueryRequest request, Dict dict);

    void createDict(Dict dict);

    void updateDict(Dict dicdt);

    void deleteDicts(String[] dictIds);

}
