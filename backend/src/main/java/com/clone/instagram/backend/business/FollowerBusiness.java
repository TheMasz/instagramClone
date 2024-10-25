package com.clone.instagram.backend.business;

import com.clone.instagram.backend.entity.Follower;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.UserException;
import com.clone.instagram.backend.model.MFollowerResponse;
import com.clone.instagram.backend.service.FollowerService;
import com.clone.instagram.backend.service.UserService;
import com.clone.instagram.backend.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FollowerBusiness {
    private final FollowerService followerService;
    private final UserService userService;

    public FollowerBusiness(FollowerService followerService, UserService userService) {
        this.followerService = followerService;
        this.userService = userService;
    }

    public MFollowerResponse follow(String id) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        Optional<User> optFollower = userService.findById(userId);
        if(optFollower.isEmpty()){
            throw UserException.userNotFound();
        }
        User followerUser = optFollower.get();

        Optional<User> optFollowing = userService.findById(id);
        if(optFollowing.isEmpty()){
            throw UserException.userNotFound();
        }
        User followingUser = optFollowing.get();

        Optional<Follower> existingFollower = followerService.findByFollowerAndFollowing(followerUser, followingUser);

        if (existingFollower.isPresent()) {
            followerService.unfollow(existingFollower.get());
            MFollowerResponse response = new MFollowerResponse();
            response.setMessage("unfollow successfully.");
            response.setCount(followingUser.getFollowings().size());
            response.setIsFollowedByCurrentUser(false);
            return response;
        } else {
            Follower follower = new Follower();
            follower.setFollower(followerUser);
            follower.setFollowing(followingUser);
            follower.setCreatedAt(new Date());
            followerService.follow(follower);
            MFollowerResponse response = new MFollowerResponse();
            response.setMessage("follow successfully.");
            response.setCount(followingUser.getFollowings().size());
            response.setIsFollowedByCurrentUser(true);
            return response;
        }

    }


}
