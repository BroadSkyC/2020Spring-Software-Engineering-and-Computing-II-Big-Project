package com.example.hotel.data.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    int updateRoomInfo(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType,@Param("curNum") Integer curNum,@Param("price") Double price);

    int insertRoom(HotelRoom hotelRoom);

    List<HotelRoom> selectRoomsByHotelId(@Param("hotelId") Integer hotelId);

    int getRoomCurNum(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType,@Param("price") Double price);

    void modifyRoomInfo(@Param("id") Integer id, @Param("hotelId") Integer hotelId,
                        @Param("roomType") RoomType roomType,
                        @Param("price") Double price,
                        @Param("total") Integer total,
                        @Param("curNum") Integer curNum);

    void deleRoom(HotelRoom hotelRoom);

    void updateAvailableRoom(@Param("hotelId") Integer hotelId,
                             @Param("roomType") String roomType,
                             @Param("price") Double price,
                             @Param("availableRoom") String availableRoom);

    String getAvailableRoom(@Param("hotelId") Integer hotelId,
                            @Param("roomType") String roomType,
                            @Param("price") Double price);

    String getBeginDate(@Param("hotelId") Integer hotelId,
                        @Param("roomType") String roomType,
                        @Param("price") Double price);

    int getTotalNum(@Param("id") Integer id);

    int getCurNum(@Param("id") Integer id);

    HotelRoom getRoomById(@Param("id") Integer id);
}
