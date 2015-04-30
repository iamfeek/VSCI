/**
 * Created by WhatTheFeeK on 30/4/15.
 */

document.getElementById("openLoginModal").onclick = function () {
    $('#loginModal').modal();
};

document.getElementById("openRegisterModal").onclick = function () {
    $('#loginModal').modal('hide');
    $('#registerModal').modal();
};

document.getElementById("backToLogin").onclick = function () {
    $('#registerModal').modal('hide');
    $("#loginModal").modal();
};