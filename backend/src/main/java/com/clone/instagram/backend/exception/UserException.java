package com.clone.instagram.backend.exception;

public class UserException extends BaseException{

    public UserException(String code) {
        super("user."+code);
    }

    // signup
    public static UserException emailNull(){

        return new UserException("signup.email.null");
    }

    public static UserException usernameNull(){

        return new UserException("signup.username.null");
    }

    public static UserException passwordNull(){

        return new UserException("signup.password.null");
    }

    public static UserException createEmailDuplicate(){

        return new UserException("create.email.duplicate");
    }

    public static UserException createUsernameDuplicate(){

        return new UserException("create.username.duplicate");
    }

    // signin
    public static UserException loginFailEmailOrUsernameNotFound() {
        return new UserException("login.fail");
    }

    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }

    public static UserException loginFailUserUnActivated() {
        return new UserException("login.fail.unactivated");
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }

    // activation
    public static UserException activateNoToken(){
        return new UserException("activate.no.token");
    }

    public static UserException activateFail(){
        return new UserException("activate.fail");
    }

    public static UserException activateTokenExpire(){
        return new UserException("activate.token.expire");
    }

    public static UserException activateAlready() {
        return new UserException("activate.already");
    }

    // resend activation
    public static UserException resendActivationEmailNoToken() {
        return new UserException("resend.activation.no.token");
    }

    public static UserException resendActivationTokenNotFound() {
        return new UserException("resend.activation.fail");
    }

    // reset password
    public static UserException resetPasswordEmailNull() {
        return new UserException("reset.password.email.null");
    }

    public static UserException resetPasswordEmailNotFound() {
        return new UserException("reset.password.not.found.email");
    }

    public static UserException resetPasswordTokenNotFound() {
        return new UserException("reset.password.not.found.token");
    }

    public static UserException resetPasswordTokenExpire() {
        return new UserException("reset.password.token.expire");
    }

    public static UserException resetPasswordTokenNull() {
        return new UserException("reset.password.token.null");
    }

    // resend reset token password
    public static UserException resendResetPasswordTokenNull() {
        return new UserException("resend.reset.password.token.null");
    }


    // profile
    public static UserException userNotFound() {
        return new UserException("not.found");
    }

    // update profile
    public static UserException bioNull() {
        return new UserException("update.bio.null");
    }

    // new password
    public static UserException newPasswordWrong() {
        return new UserException("new.password.fail");
    }

    public static UserException newPasswordOldPassNull() {
        return new UserException("new.password.old.pass.null");
    }

    public static UserException newPasswordNewPassNull() {
        return new UserException("new.password.new.pass.null");
    }
}
