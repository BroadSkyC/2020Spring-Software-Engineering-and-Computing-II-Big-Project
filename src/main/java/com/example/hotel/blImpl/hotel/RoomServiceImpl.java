package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.SearchRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
            b=0;
            beginDate = LocalDate.parse(allRoom.getBeginDate());
            /*if (beginDate.getMonth() == CheckInDate.getMonth()) {
                beginNum = CheckInDate.getDayOfMonth() - beginDate.getDayOfMonth() + 1;
            } else {
                beginNum = CheckInDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth() + 1;
            }*/
            beginNum= (int)ChronoUnit.DAYS.between(beginDate, CheckInDate)+1;
            /*if (beginDate.getMonth() == CheckOutDate.getMonth()) {
                endNum = CheckOutDate.getDayOfMonth() - beginDate.getDayOfMonth();
            } else {
                endNum = CheckOutDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth();
            }*/
            endNum=(int)ChronoUnit.DAYS.between(beginDate, CheckOutDate);
            avaliable = allRoom.getAvailableRoom().split(",");
            if(endNum>avaliable.length||beginNum<1){
                continue;
            }
            for (int j = beginNum; j <= endNum; j++) {
                String[] slice=avaliable[j - 1].split("\\*");
                a = Integer.parseInt(slice[1]);
                if (a == 0) {
                    b = 1;
                    break;
                }
                mini = Math.min(a, mini);
            }
            if (b == 1) {
                continue;
            }
            allRoom.setCurNum(mini);
            mini=Integer.MAX_VALUE;
            availableRooms.add(allRoom);
        }

        return availableRooms;
    }

    @Override
    public void insertRoomInfo(HotelRoom hotelRoom) {
        try {
            List<HotelRoom> hotelRooms = retrieveHotelRoomInfo(hotelRoom.getHotelId());
            int hotel_id = hotelRoom.getHotelId();
            double minPrice = hotelMapper.getMinPrice(hotel_id); //获取该hotel的最低价格
            double maxPrice = hotelMapper.getMaxPrice(hotel_id); //获取该hotel的最高价格
            //如果最低价格为0，说明该hotel没有room信息，直接设为要添加房间的价格
            if (minPrice==0){
                minPrice=hotelRoom.getPrice();
            }
            //修改最低价格/最高价格
            if (hotelRoom.getPrice()<=minPrice){
                minPrice = hotelRoom.getPrice();
            }
            if (hotelRoom.getPrice()>=maxPrice){
                maxPrice = hotelRoom.getPrice();
            }
            LocalDate beginDate = LocalDate.parse(hotelRoom.getBeginDate());
            LocalDate endDate = LocalDate.parse(hotelRoom.getEndDate());
            int days = (int)ChronoUnit.DAYS.between(beginDate,endDate)+1;
            /*if (beginDate.getMonth()==endDate.getMonth()){
                days = endDate.getDayOfMonth()-beginDate.getDayOfMonth();
            }else {
                days = endDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth();
            }
            days++;*/
            hotelRoom.setAlldays(days);
            //设置availableRoom字符串 格式为1*roomNum，2*roomNum，3*roomNum，4*roomNum···
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
        catch (Exception e){
            System.out.println(e);
        }
        /*List<HotelRoom> hotelRooms = retrieveHotelRoomInfo(hotelRoom.getHotelId());
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
*//*        for (HotelRoom room: hotelRooms){
            if (room.getPrice()<=minPrice){
                minPrice = room.getPrice();
            }
            if (room.getPrice()>=maxPrice){
                maxPrice = room.getPrice();
            }
        }*//*
        LocalDate beginDate = LocalDate.parse(hotelRoom.getBeginDate());
        LocalDate endDate = LocalDate.parse(hotelRoom.getEndDate());
        int days = 0;
        if (beginDate.getMonth()==endDate.getMonth()){
            days = endDate.getDayOfMonth()-beginDate.getDayOfMonth();
        }else {
          days = endDate.getDayOfMonth() + beginDate.lengthOfMonth() - beginDate.getDayOfMonth();
        }
        days++;
        hotelRoom.setAlldays(days);
        String availableRoom = "";
        for (int i=1;i<days;i++){
            availableRoom += i + "*" + hotelRoom.getTotal() + ",";
        }
        availableRoom += days+"*"+hotelRoom.getTotal();
        hotelRoom.setAvailableRoom(availableRoom);
        hotelMapper.updateMinPrice(hotel_id, minPrice);
        hotelMapper.updateMaxPrice(hotel_id, maxPrice);
        roomMapper.insertRoom(hotelRoom);
*/
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms, Double price) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms,price);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType,Double price) {
        return roomMapper.getRoomCurNum(hotelId,roomType,price);
    }

    @Override
    public void modifyRoomInfo(HotelRoom hotelRoom) {
        //get Origin total
        int origin_total = roomMapper.getTotalNum(hotelRoom.getId());
        //get Origin curNum
        int origin_curNum = roomMapper.getCurNum(hotelRoom.getId());
        //update roomInfo
        roomMapper.modifyRoomInfo(hotelRoom.getId(),
                hotelRoom.getHotelId(),
                hotelRoom.getRoomType(),
                hotelRoom.getPrice(),
                hotelRoom.getTotal(),
                origin_curNum+hotelRoom.getTotal()-origin_total);
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
        //update availableRoom
        String availableRoom = roomMapper.getAvailableRoom(hotel_id,hotelRoom.getRoomType().name(),hotelRoom.getPrice());
        String rooms[] = availableRoom.split(",");
        int diff = hotelRoom.getTotal()-origin_total;
        availableRoom = "";
        for (int i=0;i<rooms.length;i++){
            if (i!=rooms.length-1){
                availableRoom += rooms[i].split("\\*")[0] + "*" + (Integer.parseInt(rooms[i].split("\\*")[1]) + diff) + ",";
            }else {
                availableRoom += rooms[i].split("\\*")[0] + "*" + (Integer.parseInt(rooms[i].split("\\*")[1]) + diff);
            }
        }
        roomMapper.updateAvailableRoom(hotel_id,hotelRoom.getRoomType().name(),hotelRoom.getPrice(),availableRoom);

        hotelMapper.updateMinPrice(hotel_id, minPrice); //update
        hotelMapper.updateMaxPrice(hotel_id, maxPrice);
    }


    @Override
    public void deleteRoom(HotelRoom hotelRoom){
        roomMapper.deleRoom(hotelRoom);
    }

    @Override
    public int getTotalNum(Integer id){
        return roomMapper.getTotalNum(id);
    }
}
