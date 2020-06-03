package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public void insertRoomInfo(HotelRoom hotelRoom) {
        List<HotelRoom> hotelRooms = retrieveHotelRoomInfo(hotelRoom.getHotelId());
        int hotel_id = hotelRoom.getHotelId();
        double minPrice = hotelMapper.getMinPrice(hotel_id);
        double maxPrice = hotelMapper.getMaxPrice(hotel_id);
        if (hotelRoom.getPrice()<=minPrice){
            minPrice = hotelRoom.getPrice();
        }
        if (hotelRoom.getPrice()>=maxPrice){
            maxPrice = hotelRoom.getPrice();
        }
/*        for (HotelRoom room: hotelRooms){
            if (room.getPrice()<=minPrice){
                minPrice = room.getPrice();
            }
            if (room.getPrice()>=maxPrice){
                maxPrice = room.getPrice();
            }
        }*/
        hotelMapper.updateMinPrice(hotel_id, minPrice);
        hotelMapper.updateMaxPrice(hotel_id, maxPrice);
        roomMapper.insertRoom(hotelRoom);

    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType,Double price) {
        return roomMapper.getRoomCurNum(hotelId,roomType,price);
    }


}
