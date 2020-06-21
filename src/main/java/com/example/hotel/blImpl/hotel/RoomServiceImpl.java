package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        if (minPrice==0){
            minPrice=hotelRoom.getPrice();
        }
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
        LocalDate beginDate = LocalDate.parse(hotelRoom.getBeginDate());
        LocalDate endDate = LocalDate.parse(hotelRoom.getEndDate());
        int days = 0;
        if (beginDate.getMonth()!=endDate.getMonth()){
            days = endDate.getDayOfMonth()-beginDate.getDayOfMonth();
        }else {
          days = endDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth();
        }
        hotelRoom.setAlldays(days);
        String availableRoom = "";
        for (int i=1;i<=days;i++){
            availableRoom += i + "*" + hotelRoom.getTotal();
        }
        hotelRoom.setAvaliableRoom(availableRoom);
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

    @Override
    public void modifyRoomInfo(HotelRoom hotelRoom) {
        //update roomInfo
        roomMapper.modifyRoomInfo(hotelRoom.getId(),
                hotelRoom.getHotelId(),
                hotelRoom.getRoomType(),
                hotelRoom.getPrice(),
                hotelRoom.getTotal(),
                hotelRoom.getCurNum());
        //update minPrice and maxPrice
        int hotel_id = hotelRoom.getHotelId();
        List<HotelRoom> hotelRooms = retrieveHotelRoomInfo(hotelRoom.getHotelId());
        double price = hotelRoom.getPrice();
        double minPrice = price;
        double maxPrice = price;
        for (HotelRoom room : hotelRooms) {
            if (room.getPrice() <= minPrice) {
                minPrice = room.getPrice();
            }
            if (room.getPrice() >= maxPrice) {
                maxPrice = room.getPrice();
            }
        }
        hotelMapper.updateMinPrice(hotel_id, minPrice); //update
        hotelMapper.updateMaxPrice(hotel_id, maxPrice);
        /*roomMapper.modifyRoomInfo(hotelRoom.getId(),
                hotelRoom.getHotelId(),
                hotelRoom.getRoomType(),
                hotelRoom.getPrice(),
                hotelRoom.getTotal(),
                hotelRoom.getCurNum());*/
    }


    @Override
    public void deleteRoom(HotelRoom hotelRoom){
        roomMapper.deleRoom(hotelRoom);
    }
}
