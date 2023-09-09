package com.atguigu.yygh.controller.set;


import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.yygh.service.HospitalSetService;
import com.atguigu.yygh.common.util.MD5;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("/admin/hospset/hospitalSet")
public class HospitalSetController {
    @Resource
    private HospitalSetService hospitalSetService;

    @PostMapping("/{current}/{limit}")
    public Result findAllHospitalSet(@PathVariable Integer current,
                                     @PathVariable Integer limit,
                                     @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>();
        page.setCurrent(current);
        page.setSize(limit);
        QueryWrapper<HospitalSet> qw = new QueryWrapper();
        if (hospitalSetQueryVo != null) {
            if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())) {
                qw.eq("hoscode", hospitalSetQueryVo.getHoscode());
            }
            if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())) {
                qw.like("hosname", hospitalSetQueryVo.getHosname());
            }
        }
        page = hospitalSetService.page(page, qw);
        return Result.ok(page);
    }


    @DeleteMapping("/{id}")
    public Result deleteHospSet(@PathVariable Integer id) {

        boolean deleteOrNot = hospitalSetService.removeById(id);
        if (deleteOrNot) {
            return Result.ok();
        }
        return Result.fail();
    }

    @DeleteMapping("batchRemove")
    public Result deleteByList(@RequestBody List<Integer> idList) {
        boolean deleteOrNot = hospitalSetService.removeByIds(idList);
        if (deleteOrNot) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockStatus(@PathVariable Integer id,
                             @PathVariable Integer status) {
        if (status != 1 && status != 0) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setStatus(status);
        hospitalSet.setId(id.longValue());
        boolean updateById = hospitalSetService.updateById(hospitalSet);
        if (updateById) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/saveHospitalSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet) {
        if (hospitalSet == null) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        hospitalSet.setStatus(1);
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        boolean saveOrNot = hospitalSetService.save(hospitalSet);
        if (saveOrNot) {
            return Result.ok();
        }
        return Result.fail();
    }
}
