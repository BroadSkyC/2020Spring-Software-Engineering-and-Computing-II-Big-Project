package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        System.out.println(hotelVO.getImgUrl());
        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @PostMapping("/delHotel")
    public ResponseVO deleHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        hotelService.delHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/modifyRoom")
    public ResponseVO modifyRoomInfo(@RequestBody HotelRoom hotelRoom){
        roomService.modifyRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{userId}/getManagerHotels")
    public ResponseVO retrieveManagerHotel(@PathVariable Integer userId){

        return ResponseVO.buildSuccess(hotelService.retrieveManagerHotels(userId));
    }
}
