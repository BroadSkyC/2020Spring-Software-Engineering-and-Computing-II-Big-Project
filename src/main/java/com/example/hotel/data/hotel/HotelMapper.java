package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    int deleteHotel(Hotel hotel);

    void updateMinPrice(@Param("id") Integer id, @Param("minPrice") Double minPrice);

    void updateMaxPrice(@Param("id") Integer id, @Param("maxPrice") Double maxPrice);

    double getMinPrice(@Param("id") Integer id);

    double getMaxPrice(@Param("id") Integer id);

    void updateRate(@Param("id") Integer id,@Param("rate") Double rate,@Param("commentTime") Integer commentTime);

    void updateHotelInfo(@Param("id") Integer id,
                         @Param("hotelName") String hotelName,
                         @Param("hotelDescription") String hotelDescription,
                         @Param("address") String address,
                         @Param("bizRegion") String bizRegion,
                         @Param("phoneNum") String phoneNum);
}
