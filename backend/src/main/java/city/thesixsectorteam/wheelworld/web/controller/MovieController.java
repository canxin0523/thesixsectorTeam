package city.thesixsectorteam.wheelworld.web.controller;

import city.thesixsectorteam.wheelworld.common.domain.SixsectorConstant;
import city.thesixsectorteam.wheelworld.common.domain.SixsectorResponse;
import city.thesixsectorteam.wheelworld.common.exception.SixsectorException;
import city.thesixsectorteam.wheelworld.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("movie")
public class MovieController {

    private String message;

    @GetMapping("hot")
    public SixsectorResponse getMoiveHot() throws SixsectorException {
        try {
            String data = HttpUtil.sendSSLPost(SixsectorConstant.TIME_MOVIE_HOT_URL, "locationId=328");
            return new SixsectorResponse().data(data);
        } catch (Exception e) {
            message = "获取热映影片信息失败";
            log.error(message, e);
            throw new SixsectorException(message);
        }
    }

    @GetMapping("coming")
    public SixsectorResponse getMovieComing() throws SixsectorException {
        try {
            String data = HttpUtil.sendSSLPost(SixsectorConstant.TIME_MOVIE_COMING_URL, "locationId=328");
            return new SixsectorResponse().data(data);
        } catch (Exception e) {
            message = "获取即将上映影片信息失败";
            log.error(message, e);
            throw new SixsectorException(message);
        }
    }

    @GetMapping("detail")
    public SixsectorResponse getDetail(@NotBlank(message = "{required}") String id) throws SixsectorException {
        try {
            String data = HttpUtil.sendSSLPost(SixsectorConstant.TIME_MOVIE_DETAIL_URL, "locationId=328&movieId=" + id);
            return new SixsectorResponse().data(data);
        } catch (Exception e) {
            message = "获取影片详情失败";
            log.error(message, e);
            throw new SixsectorException(message);
        }
    }

    @GetMapping("comments")
    public SixsectorResponse getComments(@NotBlank(message = "{required}") String id) throws SixsectorException {
        try {
            String data = HttpUtil.sendSSLPost(SixsectorConstant.TIME_MOVIE_COMMENTS_URL, "movieId=" + id);
            return new SixsectorResponse().data(data);
        } catch (Exception e) {
            message = "获取影片评论失败";
            log.error(message, e);
            throw new SixsectorException(message);
        }
    }
}
