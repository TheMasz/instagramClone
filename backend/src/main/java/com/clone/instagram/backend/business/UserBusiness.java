package com.clone.instagram.backend.business;

import ch.qos.logback.core.util.StringUtil;
import com.clone.instagram.backend.entity.Follower;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.FileException;
import com.clone.instagram.backend.exception.UserException;
import com.clone.instagram.backend.mapper.CustomUserMapper;
import com.clone.instagram.backend.mapper.UserMapper;
import com.clone.instagram.backend.model.*;
import com.clone.instagram.backend.service.FileStorageService;
import com.clone.instagram.backend.service.FollowerService;
import com.clone.instagram.backend.service.TokenService;
import com.clone.instagram.backend.service.UserService;
import com.clone.instagram.backend.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserBusiness {
    private final UserMapper userMapper;
    private final CustomUserMapper customUserMapper;

    private final UserService userService;
    private final FollowerService followerService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    private final EmailBusiness emailBusiness;

    public UserBusiness(UserService userService, UserMapper userMapper, CustomUserMapper customUserMapper, FollowerService followerService,
                        TokenService tokenService, PasswordEncoder passwordEncoder,
                        FileStorageService fileStorageService, EmailBusiness emailBusiness
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.customUserMapper = customUserMapper;
        this.followerService = followerService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
        this.emailBusiness = emailBusiness;
    }

    public MSignInResponse signin(MSignInRequest request) throws BaseException {
        boolean isEmail = isEmail(request.getEmailOrUsername());

        Optional<User> opt;

        if (isEmail) {
            opt = userService.findByEmail(request.getEmailOrUsername());
        } else {
            opt = userService.findByUsername(request.getEmailOrUsername());
        }

        if (opt.isEmpty()) {
            throw UserException.loginFailEmailOrUsernameNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();
        }

        if (!user.isActivated()) {
            throw UserException.loginFailUserUnActivated();
        }

        MSignInResponse response = new MSignInResponse();
        response.setToken(tokenService.tokenize(user));

        return response;
    }

    public MSignUpResponse signup(MSignUpRequest request) throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(
                request.getEmail(),
                request.getUsername(),
                request.getPassword(),
                token,
                nextXMinute(30)
        );

        sendActivateEmail(user);
        return userMapper.toSignUpResponse(user);
    }

    public String signout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            tokenService.invalidateToken(token);
        }
        return "Successfully signed out";
    }

    public MActivateResponse activate(MActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateNoToken();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.activateFail();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        Date now = new Date();
        Date expireDate = user.getTokenExpire();
        if (now.after(expireDate)) {
            throw UserException.activateTokenExpire();
        }

        user.setActivated(true);
        user.setToken(null);
        user.setTokenExpire(null);
        userService.update(user);

        MActivateResponse response = new MActivateResponse();
        response.setSuccess(true);

        return response;
    }

    public void sendTokenResetPassword(MSendTokenResetPasswordRequest request) throws UserException {
        String email = request.getEmail();
        if (StringUtil.isNullOrEmpty(email)) {
            throw UserException.resetPasswordEmailNull();
        }

        Optional<User> opt = userService.findByEmail(email);
        if (opt.isEmpty()) {
            throw UserException.resetPasswordEmailNotFound();
        }

        User user = opt.get();

        user.setResetToken(SecurityUtil.generateToken());
        user.setResetTokenExpire(nextXMinute(30));
        user = userService.update(user);

        sendResetPasswordEmail(user);
    }

    public void resendTokenResetPassword(MResendResetPasswordRequest request) throws UserException {
        String resetToken = request.getResetToken();
        if (resetToken == null) {
            throw UserException.resendResetPasswordTokenNull();
        }

        Optional<User> opt = userService.findByResetToken(resetToken);
        if (opt.isEmpty()) {
            throw UserException.resetPasswordTokenNotFound();
        }

        User user = opt.get();

        user.setResetToken(SecurityUtil.generateToken());
        user.setResetTokenExpire(nextXMinute(30));
        user = userService.update(user);

        sendResetPasswordEmail(user);
    }

    public MResetPasswordResponse resetPassword(MResetPasswordRequest request) throws BaseException {
        String resetToken = request.getResetToken();

        if (resetToken == null) {
            throw UserException.resetPasswordTokenNull();
        }

        Optional<User> opt = userService.findByResetToken(resetToken);
        if (opt.isEmpty()) {
            throw UserException.resetPasswordTokenNotFound();
        }

        User user = opt.get();

        Date now = new Date();
        Date expireDate = user.getResetTokenExpire();
        if (now.after(expireDate)) {
            throw UserException.resetPasswordTokenExpire();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpire(null);
        userService.update(user);

        MResetPasswordResponse response = new MResetPasswordResponse();
        response.setSuccess(true);

        return response;

    }

    public void resendActivationEmail(MResendActivationRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.resendActivationEmailNoToken();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.resendActivationTokenNotFound();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextXMinute(30));
        user = userService.update(user);

        sendActivateEmail(user);
    }

    public void deleteAccount() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        userService.deleteById(userId);
    }

    public MUserProfileResponse getMyUserProfile() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        Optional<User> optUser = userService.findById(userId);

        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }

        return customUserMapper.toUserProfile(optUser.get(), userId);
    }

    public MUserProfileResponse getUserProfileByUsername(String username) throws UserException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findByUsername(username);
        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }

        User user = optUser.get();

        return customUserMapper.toUserProfile(user, userId);
    }

    public MUserProfileResponse uploadProfilePicture(MultipartFile file) throws IOException, BaseException {
        if (file == null) {
            throw FileException.fileNull();
        }

        if (file.getSize() > 1048576 * 2) {
            throw FileException.fileMaxSize();
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            throw FileException.unsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supportedTypes.contains(contentType)) {
            throw FileException.unsupported();
        }

        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }

        User user = optUser.get();
        String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        fileStorageService.saveProfilePicture(file, userId, fileExtension);
        user.setProfilePictureUrl("/profile_pictures/" + userId + fileExtension);
        userService.update(user);

        return customUserMapper.toUserProfile(user, userId);
    }

    public MUserProfileResponse updateProfile(MUpdateProfileRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }

        if (Objects.isNull(request.getBio())) {
            throw UserException.bioNull();
        }

        User user = optUser.get();
        user.setBio(request.getBio());
        userService.update(user);

        return customUserMapper.toUserProfile(user, userId);
    }

    public String newPassword(MNewPasswordRequest request) throws BaseException {

        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        if (Objects.isNull(request.getOldPassword())) {
            throw UserException.newPasswordOldPassNull();
        }

        if (Objects.isNull(request.getNewPassword())) {
            throw UserException.newPasswordNewPassNull();
        }

        String userId = opt.get();
        Optional<User> optUser = userService.findById(userId);

        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }

        User user = optUser.get();

        boolean isMatch = userService.matchPassword(request.getOldPassword(), user.getPassword());

        if (!isMatch) {
            throw UserException.newPasswordWrong();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.update(user);

        return "password updated";
    }

    private Date nextXMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    private boolean isEmail(String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return input.matches(emailRegex);
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return "";
        }
        return fileName.substring(lastIndexOfDot);
    }

    private void sendActivateEmail(User user) {
        String token = user.getToken();
        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getUsername(), token);

        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResetPasswordEmail(User user) {
        String resetToken = user.getResetToken();
        try {
            emailBusiness.sendResetPassword(user.getEmail(), user.getUsername(), resetToken);

        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MUserProfileResponse> getSuggestUser() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt.get();

        List<User> users = userService.findAll();
        List<Follower> currentUserFollowings = followerService.getFollowingsByUserId(userId);

        Set<String> followingUserIds = currentUserFollowings.stream()
                .map(follower -> follower.getFollowing().getId())
                .collect(Collectors.toSet());

        List<User> filteredUsers = users.stream()
                .filter(user -> !user.getId().equals(userId))
                .filter(user -> !followingUserIds.contains(user.getId()))
                .limit(5)
                .collect(Collectors.toList());

        return customUserMapper.toUsersProfile(filteredUsers, userId);

    }
}
