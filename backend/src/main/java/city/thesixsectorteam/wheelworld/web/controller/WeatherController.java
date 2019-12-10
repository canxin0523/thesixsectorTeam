package city.thesixsectorteam.wheelworld.web.controller;

import city.thesixsectorteam.wheelworld.common.domain.SixsectorConstant;
import city.thesixsectorteam.wheelworld.common.domain.SixsectorResponse;
import city.thesixsectorteam.wheelworld.common.exception.SixsectorException;
import city.thesixsectorteam.wheelworld.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("weather")
public class WeatherController {

    @GetMapping
    @RequiresPermissions("weather:view")
    public SixsectorResponse queryWeather(@NotBlank(message = "{required}") String areaId) throws SixsectorException {
        try {
            String data = HttpUtil.sendPost(SixsectorConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return new SixsectorResponse().data(data);
        } catch (Exception e) {
            String message = "天气查询失败";
            log.error(message, e);
            throw new SixsectorException(message);
        }
    }
}
