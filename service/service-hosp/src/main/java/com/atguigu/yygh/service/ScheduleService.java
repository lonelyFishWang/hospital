package com.atguigu.yygh.service;

import java.util.Map;

public interface ScheduleService {
     Map<String, Object> getSchedulePage(Integer pageNum, Integer pageSize);

    void saveSchedule(Map<String, Object> stringObjectMap);

    void remove(String hoscode, String hosScheduleId);
}
