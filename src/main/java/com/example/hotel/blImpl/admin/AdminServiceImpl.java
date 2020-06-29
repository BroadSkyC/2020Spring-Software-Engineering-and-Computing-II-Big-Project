package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.ReceptionistForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    AdminMapper adminMapper;
    @Override
    public ResponseVO addManager(UserVO userVO) {
        User user = new User();
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUserName(userVO.getUserName());
        user.setUserType(UserType.HotelManager);
        user.setImgUrl("https://farsky-seec-homework1.oss-cn-shanghai.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
        try {
            adminMapper.addManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getManagerList() {
        return adminMapper.getAllManagers();
    }

    @Override
    public ResponseVO delManager(UserForm userForm){
        User user =new User();
        BeanUtils.copyProperties(userForm,user);
        adminMapper.delManager(user);
        return ResponseVO.buildSuccess(true);
    }

    public ResponseVO addReceptionist(ReceptionistForm receptionistForm){
        User user=new User();
        user.setEmail(receptionistForm.getEmail());
        user.setPassword(receptionistForm.getPassword());
        user.setPhoneNumber(receptionistForm.getPhoneNum());
        user.setHotelId(receptionistForm.getHotelId());
        user.setUserType(UserType.HotelReceptionist);
        try {
            adminMapper.addReceptionist(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    public List<User> getHotelReceptionistList(Integer hotelId){
        return adminMapper.getHotelReceptionistList(hotelId);
    }

    public ResponseVO delReceptionist(User user){
        adminMapper.delManager(user);
        return ResponseVO.buildSuccess(true);
    }
}
