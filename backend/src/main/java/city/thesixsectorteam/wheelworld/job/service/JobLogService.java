package city.thesixsectorteam.wheelworld.job.service;

import city.thesixsectorteam.wheelworld.common.domain.QueryRequest;
import city.thesixsectorteam.wheelworld.job.domain.JobLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface JobLogService extends IService<JobLog> {

    IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog);

    void saveJobLog(JobLog log);

    void deleteJobLogs(String[] jobLogIds);
}
