package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyCouponStrategyImpl implements CouponMatchStrategy {

    @Autowired
    AccountMapper accountMapper;
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if (coupon.getCouponType()==5){
            int userId = orderVO.getUserId();
            String company = accountMapper.getCompany(userId);
            if (company==null)
                return false;
            else
                if (company.equals(coupon.getCompany()))
                    return true;
        }
        return false;
    }
}
