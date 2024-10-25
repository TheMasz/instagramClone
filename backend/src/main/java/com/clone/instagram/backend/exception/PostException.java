package com.clone.instagram.backend.exception;

public class PostException extends BaseException {
    public PostException(String code) {
        super("post." + code);
    }

    public static PostException filesNull(){
        return new PostException("files.null");
    }

    public static PostException descNull(){
        return new PostException("description.null");
    }

    // POST
    public static PostException postIDNull(){
        return new PostException("id.null");
    }

    public static PostException postNotFound(){
        return new PostException("not.found");
    }
}
