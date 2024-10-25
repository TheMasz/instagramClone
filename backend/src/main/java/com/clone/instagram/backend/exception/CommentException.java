package com.clone.instagram.backend.exception;

public class CommentException extends BaseException {
    public CommentException(String code) {
        super("comment."+code);
    }

    public static CommentException postIdNull(){
        return new CommentException("post.id.null");
    }

    public static CommentException commentIdNull(){
        return new CommentException("id.null");
    }

    public static CommentException messageNull(){
        return new CommentException("message.null");
    }

    public static CommentException commentNotFound(){
        return new CommentException("not.found");
    }
}
