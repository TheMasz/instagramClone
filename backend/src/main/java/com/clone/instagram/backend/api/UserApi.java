package com.clone.instagram.backend.api;

import com.clone.instagram.backend.business.UserBusiness;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness business;

    public UserApi(UserBusiness business) {
        this.business = business;
    }

    @PostMapping("/signin")
    public ResponseEntity<MSignInResponse> signin(@RequestBody MSignInRequest request) throws BaseException {
        MSignInResponse response = business.signin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<MSignUpResponse> signup(@RequestBody MSignUpRequest request) throws BaseException {
        MSignUpResponse response = business.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signup(HttpServletRequest request) {
        String response = business.signout(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<Void> deleteMyAccount() throws BaseException {
        business.deleteAccount();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate-account")
    public ResponseEntity<MActivateResponse> activateAccount(@RequestBody MActivateRequest request) throws BaseException {
        MActivateResponse response = business.activate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody MResendActivationRequest request) throws BaseException {
        business.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/send-token-reset-password")
    public ResponseEntity<Void> sendTokenResetPassword(@RequestBody MSendTokenResetPasswordRequest request) throws BaseException {
        business.sendTokenResetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/resend-token-reset-password")
    public ResponseEntity<Void> resendTokenResetPassword(@RequestBody MResendResetPasswordRequest request) throws BaseException {
        business.resendTokenResetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity<MResetPasswordResponse> resetPassword(@RequestBody MResetPasswordRequest request) throws BaseException {
        MResetPasswordResponse response = business.resetPassword(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myprofile")
    public ResponseEntity<MUserProfileResponse> myProfile() throws BaseException {
        MUserProfileResponse response = business.getMyUserProfile();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<MUserProfileResponse> userProfile(@PathVariable String username) throws BaseException {
        MUserProfileResponse response = business.getUserProfileByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<MUserProfileResponse> updateProfile(@RequestBody MUpdateProfileRequest request) throws BaseException {
        MUserProfileResponse response = business.updateProfile(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-profile-picture")
    public ResponseEntity<MUserProfileResponse> updateProfilePicture(@RequestPart MultipartFile file) throws BaseException, IOException {
        MUserProfileResponse response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/new-password")
    public ResponseEntity<String> newPassword(@RequestBody MNewPasswordRequest request) throws BaseException {
        String response = business.newPassword(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/suggest")
    public ResponseEntity<List<MUserProfileResponse>> getSuggestUser() throws BaseException{
        List<MUserProfileResponse> response = business.getSuggestUser();
        return ResponseEntity.ok(response);
    }

}
