package city.thesixsectorteam.wheelworld.soul.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.soul.domain.Soul;
import city.thesixsectorteam.wheelworld.soul.service.SoulService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：SoulController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Slf4j
@Validated
@RestController
@RequestMapping("soul")
public class SoulController {

    @Autowired
    private SoulService soulService;

    @Log("进入待勾魂状态状态")
    @Scheduled(cron="0 0 3 * * ?" )
    public void saveTrial(){
        soulService.saveSoul();
    }

    @Log("修改")
    @PostMapping("updateSoul")
    @RequiresPermissions("soul:update")
    public String updateSoul(Soul soul){
        return soulService.updateSoul(soul);
    }

    @Log("查询")
    @GetMapping("queryList")
    @RequiresPermissions("soul:view")
    public List<Map<String,Object>> queryList(Soul soul){
        return soulService.queryList(soul);
    }
}
