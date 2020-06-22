package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.SearchRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<HotelRoom> retrieveAvailableRoomInfo(SearchRoom searchRoom){
        List<HotelRoom> allRooms=roomMapper.selectRoomsByHotelId(searchRoom.getHotelId());
        List<HotelRoom> availableRooms=new ArrayList<HotelRoom>();
        LocalDate CheckInDate = LocalDate.parse(searchRoom.getCheckInDate());
        LocalDate CheckOutDate = LocalDate.parse(searchRoom.getCheckOutDate());
        int beginNum,endNum;
        LocalDate beginDate;
        String[] avaliable;
        int a=0,b=0,mini=Integer.MAX_VALUE;
        for (HotelRoom allRoom : allRooms) {
            beginDate = LocalDate.parse(allRoom.getBeginDate());
            if (beginDate.getMonth() == CheckInDate.getMonth()) {
                beginNum = CheckInDate.getDayOfMonth() - beginDate.getDayOfMonth() + 1;
            } else {
                beginNum = CheckInDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth() + 1;
            }
            if (beginDate.getMonth() == CheckOutDate.getMonth()) {
                endNum = CheckOutDate.getDayOfMonth() - beginDate.getDayOfMonth();
            } else {
                endNum = CheckOutDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth();
            }
            avaliable = allRoom.getAvailableRoom().split(",");
            for (int j = beginNum; j <= endNum; j++) {
                a = Integer.parseInt(avaliable[j - 1].substring(avaliable[j - 1].length() - 1));
                if (a == 0) {
                    b = 1;
                    break;
                }
                mini = Math.min(a, mini);
            }
            if (b == 1) {
                break;
            }
            allRoom.setCurNum(mini);
            availableRooms.add(allRoom);
        }
        return availableRooms;
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
        if (beginDate.getMonth()==endDate.getMonth()){
            days = endDate.getDayOfMonth()-beginDate.getDayOfMonth();
        }else {
          days = endDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth();
        }
        hotelRoom.setAlldays(days+1);
        String availableRoom = "";
        for (int i=1;i<days;i++){
            availableRoom += i + "*" + hotelRoom.getTotal() + ",";
        }
        availableRoom += days+"*"+hotelRoom.getTotal();
        hotelRoom.setAvailableRoom(availableRoom);
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
