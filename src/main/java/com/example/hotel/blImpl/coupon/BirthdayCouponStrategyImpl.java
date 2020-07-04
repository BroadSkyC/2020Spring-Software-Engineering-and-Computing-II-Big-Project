package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

    @Autowired
    private AccountService accountService;
    // TODO: 2020/5/25  
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if (coupon.getCouponType()==1) {
            User user = accountService.getUserInfo(orderVO.getUserId());
            String bir = user.getBirthday();
            if (bir!=null){
                //int index = bir.indexOf('T');
                bir = bir.substring(0, 10);
                LocalDate birthday = LocalDate.parse(bir);
                LocalDate today = LocalDate.now();
                return birthday.getMonth() == today.getMonth() && birthday.getDayOfMonth() == today.getDayOfMonth();
            }
        }
        return false;
    }
}

