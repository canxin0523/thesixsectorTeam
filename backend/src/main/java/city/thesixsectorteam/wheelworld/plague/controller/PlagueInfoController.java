package city.thesixsectorteam.wheelworld.plague.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.common.controller.BaseController;
import city.thesixsectorteam.wheelworld.plague.domain.Plague;
import city.thesixsectorteam.wheelworld.plague.domain.PlagueInfo;
import city.thesixsectorteam.wheelworld.plague.service.PlagueInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("plagueInfo")
public class PlagueInfoController extends BaseController {
    @Autowired
    PlagueInfoService plagueInfoService;

    @Log("查询瘟疫所有信息")
    @GetMapping("/{plagueId}")
    public Map<String,Object> findAll(@PathVariable(name = "plagueId") String plagueId){
        return  plagueInfoService.findPlagueInfoByPlagueId(plagueId);
    }

    @Log("添加瘟疫详细信息")
    @PutMapping("plagueInfoAdd")
    public void addPlague(@Valid PlagueInfo plagueInfo ){
        plagueInfoService.save(plagueInfo);
    }

    @Log("修改瘟疫详细")
    @PutMapping("plagueInfoUpdate")
    public void updatePlague(@Valid PlagueInfo plagueInfo){
        plagueInfoService.updatePlagueInfo(plagueInfo);
    }
}
