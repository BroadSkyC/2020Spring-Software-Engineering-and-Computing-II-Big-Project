package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {
    @Autowired
    private AccountService accountService;
    // TODO: 2020/5/25  
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        User user = accountService.getUserInfo(orderVO.getUserId());
        //if (orderVO.)
        return false;
    }
}

