package com.atguigu.common.result;

import lombok.Data;

@Data
public class Result<T> {


    private Integer code;
    private String msg;
    private T data;


    public Result() {
    }


    public static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null){
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(T data,ResultCodeEnum resultCodeEnum){
        Result<T> result = build(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
        return result;
    }

    public static Result success(){
       return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    public static <T> Result success(T data) {
        Result<T> result = Result.build(data, ResultCodeEnum.SUCCESS);
        return result;
    }

    public static Result fail(){
        return Result.build(null,ResultCodeEnum.FAIL);
    }
    public static <T> Result fail(T data){
        return Result.build(data,ResultCodeEnum.FAIL);
    }

}
