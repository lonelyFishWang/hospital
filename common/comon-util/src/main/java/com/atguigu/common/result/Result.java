package com.atguigu.common.result;

public class Result {


    private String code;
    private String msg;
    private Object data;


    public Result() {
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Result success(String code,String  msg,Object data){
        return new Result(code, msg, data) ;
    }

}
