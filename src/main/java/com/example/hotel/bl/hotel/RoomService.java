package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.SearchRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId
     * @return
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    List<HotelRoom> retrieveAvailableRoomInfo(SearchRoom searchRoom);
    /**
     * 添加酒店客房信息
     * @param hotelRoom
     */
    void insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 预订酒店后更新客房房间数量
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms, Double price);

    /**
     * 获取酒店指定房间剩余数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId, String roomType,Double price);

    void modifyRoomInfo(HotelRoom hotelRoom);

    void deleteRoom(HotelRoom hotelRoom);

    int getTotalNum(Integer id);
}
