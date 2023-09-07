package com.atguigu.yygh;


import com.atguigu.common.result.Result;
import com.atguigu.yygh.vo.order.OrderQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrderController {

    @GetMapping("/order/submitOrder")
    public Result submitOrder(OrderQueryVo orderQueryVo){


        return  null;
    }
}
