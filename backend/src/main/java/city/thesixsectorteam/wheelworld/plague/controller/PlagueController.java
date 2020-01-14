package city.thesixsectorteam.wheelworld.plague.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.common.controller.BaseController;
import city.thesixsectorteam.wheelworld.common.domain.QueryRequest;
import city.thesixsectorteam.wheelworld.common.exception.SixsectorException;
import city.thesixsectorteam.wheelworld.plague.domain.Plague;
import city.thesixsectorteam.wheelworld.plague.service.PlagueService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Validated
@RestController
@RequestMapping("plague")
public class PlagueController extends BaseController {

    @Autowired
    PlagueService plagueService;

    @Log("查询瘟疫所有信息")
    @GetMapping("/findAll")
    public Map<String,Object> findAll(){
        return  plagueService.findAll();
    }

    @Log("查询瘟疫所有信息 分页")
    @GetMapping
    public Map<String, Object> findAllPaging(@Valid QueryRequest queryRequest){
        Page<Plague> page = new Page<Plague>(queryRequest.getPageNum(),queryRequest.getPageSize());
        return getDataTable(plagueService.page(page,null));
    }

    @Log("新增瘟疫")
    @PostMapping("plagueAdd")
  //  @RequiresPermissions("plague:add")
    public void addPlague(@Valid Plague plague ){
        plagueService.savePlague(plague);
    }

    @Log("修改瘟疫")
    @PostMapping("plagueUpdate")
    public void updatePlague(@Valid Plague plague ) throws SixsectorException {
        if(plague.getPlagueId() == null ){
            throw  new SixsectorException("修改瘟疫信息时，瘟疫id不能为空");
        }else{
            plagueService.updateById(plague);
        }

    }

    @Log("刪除瘟疫")
    @DeleteMapping("deletePlagueById/{plagueId}")
    public void deletePlagueById(@PathVariable(name = "plagueId") String plagueId){
        plagueService.deletePlagueById(plagueId);
    }
}
