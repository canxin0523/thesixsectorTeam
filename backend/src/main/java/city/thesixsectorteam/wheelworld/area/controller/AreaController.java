package city.thesixsectorteam.wheelworld.area.controller;

import city.thesixsectorteam.wheelworld.area.domain.Area;
import city.thesixsectorteam.wheelworld.area.service.AreaService;
import city.thesixsectorteam.wheelworld.common.annotation.Log;
import city.thesixsectorteam.wheelworld.common.exception.SixsectorException;
import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.system.domain.User;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 类/方法名称：AreaController
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 */
@Slf4j
@Validated
@RestController
@RequestMapping("area")
public class AreaController{

    @Autowired
    private AreaService areaService;

    @Log("查询所有地区")
    @GetMapping("queryAll")
    @RequiresPermissions("area:view")
    @ApiOperation(value = "查询地区数据",notes = "查询数据参数,支持条件查询传入参数name",httpMethod = "GET")
    public String queryAll(Area area){
        return MsgUtil.success(areaService.queryAll(area), Date.class);
    }

    @Log("新增地区信息")
    @PostMapping("addArea")
    @RequiresPermissions("area:add")
    @ApiOperation(value = "新增一条数据",notes = "传入参数Area对象",httpMethod = "POST")
    public String addArea(Area area){
        return areaService.addArea(area);
    }


    @Log("poi批量导出地区数据")
    @PostMapping("excelEnter")
    @RequiresPermissions("area:excel")
    @ApiOperation(value = "导出地区信息",notes = "导出地区信息",httpMethod = "POST")
    public void  excelEnter(Area area, HttpServletResponse response) throws SixsectorException {
        try {
            List<Area> list = this.areaService.queryAll(area);
            ExcelKit.$Export(User.class, response).downXlsx(list, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new SixsectorException(message);
        }
    }

    @Log("地区修改")
    @PostMapping("updateArea")
    @RequiresPermissions("area:update")
    @ApiOperation(value = "修改地区信息",notes = "修改地区信息参数为Area对象",httpMethod = "POST")
    public  String  updateArea(Area area){
        return areaService.updateArea(area);
    }

    @Log("删除地区")
    @GetMapping("deteleArea")
    @RequiresPermissions("area:delete")
    @ApiOperation(value = "删除一条数据",notes = "删除一条数据,支持批量删除,ids ,号拼接的id字符串",httpMethod = "GET")
    public String  deteleArea(@NotBlank(message = "{required}") @PathVariable String ids) throws SixsectorException {
        return areaService.deteleArea(ids);
    }
}
