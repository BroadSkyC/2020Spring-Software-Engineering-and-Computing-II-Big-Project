package com.example.hotel.blImpl.user;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OrderService orderService;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        user.setImgUrl("https://farsky-seec-homework1.oss-cn-shanghai.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User visitor(UserVO userVO){
        User user=accountMapper.getAccountById(userVO.getId());
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public List<CreditRecord> getUserCreditRecord(Integer userId){
        List<Order> UserOrders= orderService.getUserOrders(userId);
        List<Order> finishedOrders=new ArrayList<>();
        for(int i=0;i<UserOrders.size();i++){
            if(UserOrders.get(i).getOrderState().equals("已完成") || UserOrders.get(i).getOrderState().equals("已取消"))
                finishedOrders.add(UserOrders.get(i));
        }
        List<CreditRecord> creditRecords=new ArrayList<>();
        for(int i=0;i<finishedOrders.size();i++){
            CreditRecord cr=new CreditRecord();
            cr.setId(i);
            cr.setOrderId(finishedOrders.get(i).getId());
            cr.setAction(finishedOrders.get(i).getOrderState().substring(1));
            cr.setUserId(finishedOrders.get(i).getUserId());
            cr.setCreditChange(finishedOrders.get(i).getCreditChange());
            cr.setCurrentCredit(finishedOrders.get(i).getUserCredit());
            creditRecords.add(cr);
        }
        return creditRecords;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber,String imgUrl) {
        try {
            accountMapper.updateAccount(id, password, username, phonenumber,imgUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO addcommonVip(UserVO userVO){
        User user=new User();
        BeanUtils.copyProperties(userVO,user);
        accountMapper.addCommonVip(user);
        return  ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO addcompanyVip(UserVO userVO){
        User user=new User();
        user.setId(userVO.getId());
        user.setCompany(userVO.getCompany());
        accountMapper.addCompanyVip(user);
        return  ResponseVO.buildSuccess(true);
    }
}
