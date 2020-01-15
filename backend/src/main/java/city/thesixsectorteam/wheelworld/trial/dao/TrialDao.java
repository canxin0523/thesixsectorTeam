package city.thesixsectorteam.wheelworld.trial.dao;

import city.thesixsectorteam.wheelworld.trial.domain.Trial;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrialDao {

    void saveTrial(@Param("trial") Trial trial);

    List<Map<String, Object>> queryList(@Param("trial") Trial trial);

    boolean trialUpdate(@Param("trial") Trial trial);
}
