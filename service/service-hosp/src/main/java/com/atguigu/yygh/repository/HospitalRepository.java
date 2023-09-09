package com.atguigu.yygh.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface  HospitalRepository extends MongoRepository<Hospital,String> {

    Hospital getHospitalByHoscode(String hoscode);

    Schedule findScheduleByHoscode(String hoscode);


    List<Hospital> findHospitalByHosnameLike(String hosname);
}
