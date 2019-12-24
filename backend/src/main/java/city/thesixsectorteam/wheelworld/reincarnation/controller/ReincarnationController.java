package city.thesixsectorteam.wheelworld.reincarnation.controller;

import city.thesixsectorteam.wheelworld.reincarnation.service.ReincarnationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类/方法名称：ReincarnationController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/19
 * 修改备注：
 */
@Controller
@RequestMapping("reincarnation")
public class ReincarnationController {

    @Autowired
    private ReincarnationService reincarnationService;
}
