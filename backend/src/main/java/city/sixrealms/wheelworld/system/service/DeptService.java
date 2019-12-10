package city.sixrealms.wheelworld.system.service;


import city.sixrealms.wheelworld.common.domain.QueryRequest;
import city.sixrealms.wheelworld.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface DeptService extends IService<Dept> {

    Map<String, Object> findDepts(QueryRequest request, Dept dept);

    List<Dept> findDepts(Dept dept, QueryRequest request);

    void createDept(Dept dept);

    void updateDept(Dept dept);

    void deleteDepts(String[] deptIds);
}
