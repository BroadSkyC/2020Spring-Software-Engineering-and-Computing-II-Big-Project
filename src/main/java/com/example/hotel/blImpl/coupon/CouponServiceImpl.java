package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {


    private final TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;
    private final RoomNumCouponStrategyImpl roomNumCouponStrategy;
    private final TimeCouponStrategyImpl timeCouponStrategy;
    private final BirthdayCouponStrategyImpl birthdayCouponStrategy;
    private final CompanyCouponStrategyImpl companyCouponStrategy;
    private final CouponMapper couponMapper;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             RoomNumCouponStrategyImpl roomNumCouponStrategy,
                             BirthdayCouponStrategyImpl birthdayCouponStrategy,
                             CompanyCouponStrategyImpl companyCouponStrategy,
                             CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        this.roomNumCouponStrategy=roomNumCouponStrategy;
        this.birthdayCouponStrategy = birthdayCouponStrategy;
        this.companyCouponStrategy = companyCouponStrategy;
        strategyList.add(roomNumCouponStrategy);
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(birthdayCouponStrategy);
        strategyList.add(companyCouponStrategy);
    }



    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }

        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
        return hotelCoupons;
    }

    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelTimeCoupon(TimeCouponVO couponVO){
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setStartTime(couponVO.getStartTime());
        coupon.setEndTime(couponVO.getEndTime());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setStatus(1);
        int result=couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelTargetRoomNumCoupon(HotelTargetRoomNumCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setTargetRoomNum(couponVO.getTargetRoomNum());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setTargetMoney(-1);//只用于每间优惠判断标志
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    // TODO: 2020/5/25
    @Override
    public CouponVO addHotelBirthdayCoupon(HotelBirthdayCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    public CouponVO addCompanyCoupon(CompanyCouponVO couponVO){
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setCompany(couponVO.getCompany());
        coupon.setStatus(1);
        coupon.setCouponType(couponVO.getType());
        coupon.setDescription(couponVO.getDescription());
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    public ResponseVO delCoupon(CouponVO couponVO){
        Coupon coupon=new Coupon();
        coupon.setId(couponVO.getId());
        couponMapper.deleteCoupon(coupon);
        return  ResponseVO.buildSuccess(true);
    }
}
