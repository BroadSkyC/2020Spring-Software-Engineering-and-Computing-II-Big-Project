package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {
    // TODO: 2020/5/25  
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //if (orderVO.)
        return false;
    }
}

