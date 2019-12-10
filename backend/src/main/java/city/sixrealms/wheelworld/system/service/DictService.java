package city.sixrealms.wheelworld.system.service;

import city.sixrealms.wheelworld.common.domain.QueryRequest;
import city.sixrealms.wheelworld.system.domain.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface DictService extends IService<Dict> {

    IPage<Dict> findDicts(QueryRequest request, Dict dict);

    void createDict(Dict dict);

    void updateDict(Dict dicdt);

    void deleteDicts(String[] dictIds);

}
