package cc.mrbird.febs.job.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.job.dao.JobLogMapper;
import cc.mrbird.febs.job.domain.JobLog;
import cc.mrbird.febs.job.service.JobLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

    @Override
    public IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog) {
        try {
            LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(jobLog.getBeanName())) {
                queryWrapper.eq(JobLog::getBeanName, jobLog.getBeanName());
            }
            if (StringUtils.isNotBlank(jobLog.getMethodName())) {
                queryWrapper.eq(JobLog::getMethodName, jobLog.getMethodName());
            }
            if (StringUtils.isNotBlank(jobLog.getParams())) {
                queryWrapper.like(JobLog::getParams, jobLog.getParams());
            }
            if (StringUtils.isNotBlank(jobLog.getStatus())) {
                queryWrapper.eq(JobLog::getStatus, jobLog.getStatus());
            }
            if (StringUtils.isNotBlank(jobLog.getCreateTimeFrom()) && StringUtils.isNotBlank(jobLog.getCreateTimeTo())) {
                queryWrapper
                        .ge(JobLog::getCreateTime, jobLog.getCreateTimeFrom())
                        .le(JobLog::getCreateTime, jobLog.getCreateTimeTo());
            }
            Page<JobLog> page = new Page<>(request.getPageNum(), request.getPageSize());
            SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
            return this.page(page, queryWrapper);

        } catch (Exception e) {
            log.error("获取调度日志信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void saveJobLog(JobLog log) {
        this.save(log);
    }

    @Override
    @Transactional
    public void deleteJobLogs(String[] jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds);
        this.baseMapper.deleteBatchIds(list);
    }

}
