package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserInfoVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/api/user")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final static String ACCOUNT_INFO_NOEMAIL = "请输入用户名！";
    private final static String ACCOUNT_INFO_NOPASSWORD = "请输入密码！";
    private final static String ACCOUNT_INFO_NOBOTH = "请输入用户名和密码！";
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        if(userForm.getPassword()==null && userForm.getEmail()==null) return  ResponseVO.buildFailure(ACCOUNT_INFO_NOBOTH);
        else if(userForm.getEmail()==null) return ResponseVO.buildFailure(ACCOUNT_INFO_NOEMAIL);
        else if(userForm.getPassword()==null) return ResponseVO.buildFailure(ACCOUNT_INFO_NOPASSWORD);
        User user = accountService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
    if(userVO.getImgUrl()=="") userVO.setImgUrl("https://farsky-seec-homework1.oss-cn-shanghai.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
        return accountService.registerAccount(userVO);
    }

    @PostMapping("/visitor")
    public ResponseVO visitor(@RequestBody UserVO userVO) {
        if(userVO.getImgUrl()=="") userVO.setImgUrl("https://farsky-seec-homework1.oss-cn-shanghai.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
        User user=accountService.visitor(userVO);
        return ResponseVO.buildSuccess(user);
    }

    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id) {
        User user = accountService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }


    @GetMapping("/{userId}/getUserCreditRecord")
    public ResponseVO getUserCreditRecord(@PathVariable int userId) {
        return ResponseVO.buildSuccess(accountService.getUserCreditRecord(userId));
    }


    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
            if(userInfoVO.getImgUrl()=="") userInfoVO.setImgUrl("https://farsky-seec-homework1.oss-cn-shanghai.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber(),userInfoVO.getImgUrl());

    }

    @PostMapping("/addCommonVip")
    public ResponseVO addCommvip(@RequestBody UserVO userVO ){
        return accountService.addcommonVip(userVO);
    }

    @PostMapping("/addCompanyVip")
    public ResponseVO addCompvip(@RequestBody UserVO userVO ){
        return accountService.addcompanyVip(userVO);
    }

    @PostMapping("/changePassword")
    public ResponseVO changePassword(@RequestBody UserVO userVO){
        return accountService.changePassword(userVO);
    }

    @GetMapping("/getAlllClientInfo")
    public ResponseVO getAllClientInfo(){
        return ResponseVO.buildSuccess(accountService.getAllClientInfo());
    }
}
