package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if(coupon.getCouponType()!=4) return false;
        LocalDate checkIn= LocalDate.parse(orderVO.getCheckInDate());
        LocalDate checkOut=LocalDate.parse(orderVO.getCheckOutDate());
        LocalDate startDate=LocalDate.parse(coupon.getStartTime());
        LocalDate endDate=LocalDate.parse(coupon.getEndTime());
        int start_to_checkin = (int) ChronoUnit.DAYS.between(startDate,checkIn);
        int checkout_to_end = (int) ChronoUnit.DAYS.between(checkOut,endDate);
        if(start_to_checkin>=0 && checkout_to_end>=0) return true;
        return false;
    }
}
