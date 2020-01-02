package city.thesixsectorteam.wheelworld.lifeAndDie.controller;

import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.lifeAndDie.domain.LifeAndDie;
import city.thesixsectorteam.wheelworld.lifeAndDie.service.LifeAndDieService;
import city.thesixsectorteam.wheelworld.system.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author zmf
 * @Description //TODO 
 * @Date 15:13 2019/12/30
 * @Param
 * @return 
**/
@Slf4j
@Validated
@RestController
@RequestMapping("life")
public class LifeAndDieController {

    @Autowired
    private LifeAndDieService lifeAndDieService;

    /**
     * @Author zmf
     * @Description //TODO 根据用户的邮箱 用户名 用户状态 生死簿状态
     * @Date 15:55 2019/12/30
     * @Param [user, life]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    **/
    @Log("查询生/死者数据")
    @RequestMapping("lifeList")
    @RequiresPermissions("life:view")
    public List<Map<String,Object>> lifeList(User user,LifeAndDie life){
        return lifeAndDieService.getListByLife(user,life);
    }

    @Log("初始化数据")
    @RequestMapping("saveLife")
    @RequiresPermissions("life:add")
    public void saveLife(User user){
        lifeAndDieService.saveLife(user);
    }
    @Log("根据状态修改年龄")
    @Scheduled(cron = "0 0 1 * * ?")
    public void  updateLifeByStatu(){
        lifeAndDieService.updateLifeByStatu();
    }
    @Log("根据剩余年龄修改状态")
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateStatuByOverAge(){
        lifeAndDieService.updateStatuByOverAge();
    }
    
    /**
     * @Author zmf
     * @Description //TODO 根据用户id来修改自己的寿命
     * @Date 17:12 2019/12/30
     * @Param [user]
     * @return java.lang.String
    **/
    @Log("修改寿命")
    @RequestMapping("updateTotalAge")
    @RequiresPermissions("life:update")
    public String updateTotalAge(LifeAndDie life){
        return lifeAndDieService.updateTotalAge(life);
    }

}
