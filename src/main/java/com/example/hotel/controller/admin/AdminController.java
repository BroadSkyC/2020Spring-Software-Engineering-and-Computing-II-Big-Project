package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.po.User;
import com.example.hotel.vo.ReceptionistForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserVO userVO){

        return adminService.addManager(userVO);
    }

    @GetMapping("/getManagerList")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getManagerList());
    }

    @PostMapping("/delManager")
    public ResponseVO delManager(@RequestBody UserForm userForm){
        return adminService.delManager(userForm);
    }

    @PostMapping("/addReceptionist")
    public ResponseVO addReceptionist(@RequestBody ReceptionistForm receptionistForm){

        return adminService.addReceptionist(receptionistForm);
    }
    @GetMapping("/{hotelId}/hotelAllReceptionist")
    public ResponseVO gethotelAllReceptionist(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(adminService.getHotelReceptionistList(hotelId));
    }

    @PostMapping("/delReceptionist")
    public ResponseVO delReceptionistAPI(@RequestBody User user){
        return adminService.delReceptionist(user);
    }
}
