package com.clone.instagram.backend;

import com.clone.instagram.backend.business.UserBusiness;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.MActivateRequest;
import com.clone.instagram.backend.model.MActivateResponse;
import com.clone.instagram.backend.model.MSignInRequest;
import com.clone.instagram.backend.model.MSignInResponse;
import com.clone.instagram.backend.service.UserService;
import com.clone.instagram.backend.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserBusiness userBusiness;
//
//    @Order(1)
//    @Test
//    void TestCreate() throws BaseException {
//        String token = SecurityUtil.generateToken();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 30);
//
//        User user = userService.create(
//                TestCreateData.email,
//                TestCreateData.username,
//                TestCreateData.password,
//                token,
//                calendar.getTime()
//        );
//
//        // check null
//        Assertions.assertNotNull(user);
//        Assertions.assertNotNull(user.getId());
//
//        // check equals
//        Assertions.assertEquals(user.getEmail(), TestCreateData.email);
//        Assertions.assertEquals(user.getUsername(), TestCreateData.username);
//        boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
//        Assertions.assertTrue(isMatched);
//    }
//
//    @Order(2)
//    @Test
//    void TestActivate() throws BaseException {
//        Optional<User> opt = userService.findByEmail(TestCreateData.email);
//        User user = opt.get();
//        String token = user.getToken();
//
//        MActivateRequest request = new MActivateRequest();
//        request.setToken(token);
//
//        MActivateResponse activated = userBusiness.activate(request);
//
//        Assertions.assertTrue(activated.isSuccess());
//
//    }
//
//    @Order(3)
//    @Test
//    void TestLogin() throws BaseException {
//        MSignInRequest request = new MSignInRequest();
//        request.setEmailOrUsername(TestCreateData.email);
//        request.setPassword(TestCreateData.password);
//        MSignInResponse loginByEmail = userBusiness.signin(request);
//
//        Assertions.assertNotNull(loginByEmail);
//
//        MSignInRequest request2 = new MSignInRequest();
//        request2.setEmailOrUsername(TestCreateData.username);
//        request2.setPassword(TestCreateData.password);
//        MSignInResponse loginByUsername = userBusiness.signin(request2);
//
//        Assertions.assertNotNull(loginByUsername);
//    }
//
//    @Order(4)
//    @Test
//    void TestDelete(){
//        Optional<User> opt = userService.findByEmail(TestCreateData.email);
//        User user = opt.get();
//        String id = user.getId();
//
//        userService.deleteById(id);
//
//        Optional<User> optDeleted = userService.findByEmail(TestCreateData.email);
//
//        Assertions.assertTrue(optDeleted.isEmpty());
//    }
//
//
//    interface TestCreateData {
//        String email = "masz@test.com";
//        String username = "masz";
//        String password = "12345678";
//    }

}
