package com.atguigu.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.hosp.repository.HospitalRepository;
import com.atguigu.hosp.repository.ScheduleRepository;
import com.atguigu.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private HospitalRepository hospitalRepository;
    @Resource
    private ScheduleRepository scheduleRepository;

    @Override
    public Map<String, Object> getSchedulePage(Integer pageNum, Integer pageSize) {


        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);


        Page<Schedule> all = scheduleRepository.findAll(pageable);


        List<Schedule> content = all.getContent();
        int numberOfElements = all.getNumberOfElements();


        HashMap<String, Object> pageMap = new HashMap<>();
        pageMap.put("content", content);
        pageMap.put("total", numberOfElements);
        return pageMap;
    }

    @Override
    public void saveSchedule(Map<String, Object> stringObjectMap) {
        String stringMap = JSONObject.toJSONString(stringObjectMap);
        Schedule schedule = JSONObject.parseObject(stringMap, Schedule.class);

        Schedule exist = hospitalRepository.findScheduleByHoscode(schedule.getHoscode());
        if (exist == null) {
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
            return;
        }
        schedule.setCreateTime(exist.getCreateTime());
        schedule.setUpdateTime(new Date());
        schedule.setIsDeleted(0);
        scheduleRepository.save(schedule);
    }

    @Override
    public void remove(String hoscode, String hosScheduleId) {

        Schedule schedule = new Schedule();
        schedule.setHoscode(hoscode);
        schedule.setHosScheduleId(hosScheduleId);
        scheduleRepository.delete(schedule);
    }
}
