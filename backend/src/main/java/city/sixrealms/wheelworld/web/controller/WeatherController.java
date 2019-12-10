package city.sixrealms.wheelworld.web.controller;

import city.sixrealms.wheelworld.common.domain.FebsConstant;
import city.sixrealms.wheelworld.common.domain.FebsResponse;
import city.sixrealms.wheelworld.common.exception.FebsException;
import city.sixrealms.wheelworld.common.utils.HttpUtil;
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
    public FebsResponse queryWeather(@NotBlank(message = "{required}") String areaId) throws FebsException {
        try {
            String data = HttpUtil.sendPost(FebsConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return new FebsResponse().data(data);
        } catch (Exception e) {
            String message = "天气查询失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
