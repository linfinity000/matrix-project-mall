<template>
    <div>
        <div class="bg"></div>
        <div class='middle-line'></div>
        <div class='middle-line-tip'>Matrix开源电商管理平台</div>
        <div class='login-wrapper'>
            <div class="title">用户登录</div>
            <div class="form-line">
                <span class="input-label">用户名</span>
                <input class='input' id='username' type="text" v-model="username"/>
            </div>
            <div class="form-line">
                <span class="input-label">密码</span>
                <input class='input' id='password' type="password" v-model="password"/>
            </div>
            <input @click="loginClick" class="login-btn" id='login-btn' type="button" value="登录"/>
        </div>
    </div>
</template>
<style scoped type="text/css">
    .bg {
        position: fixed;
        top: 0px;
        left: 0px;
        width: 100%;
        height: 100%;
        background: url(~@/assets/img/login_img.jpg) no-repeat;
        background-size: 100%;
    }

    .middle-line {
        width: 100%;
        height: 237px;
        background: #347ebc;
        opacity: 0.6;
        position: fixed;
        left: 0px;
        top: 0px;
        right: 0px;
        bottom: 0px;
        margin: auto;
    }

    .middle-line-tip {
        width: 510px;
        height: 237px;
        color: #FFFFFF;
        font-size: 45px;
        position: fixed;
        left: 15%;
        top: 58%;
        bottom: 0px;
        margin: auto;
    }

    .login-wrapper {
        width: 375px;
        height: 337px;
        background: #fff;
        position: fixed;
        left: 56%;
        top: 0px;
        right: 0px;
        bottom: 0px;
        margin: auto;
        z-index: 1;
        box-shadow: 0px 0px 50px rgba(0, 0, 0, 0.4);
        border-radius: 5px;
    }

    .login-wrapper .title {
        width: 375px;
        height: 35px;
        color: #347ebc;
        font-size: 25px;
        margin-top: 25px;
        text-align: center;
        margin-bottom: 30px;
    }

    .form-line {
        width: 90%;
        margin-left: 5%;
        height: 40px;
        line-height: 40px;
        padding-bottom: 2px;
        border-bottom: 1px solid #347ebc;
        margin-bottom: 30px;
    }

    .login-wrapper .input-label {
        display: inline-block;
        width: 50px;
    }

    .login-wrapper .input {
        border: 0;
        height: 30px;
        line-height: 30px;
        width: 80%;
        text-indent: 20px;
        outline: none;
    }

    .login-wrapper .login-btn {
        margin-top: 20px;
        margin-left: 5%;
        border: none;
        color: #fff;
        width: 90%;
        height: 40px;
        text-align: center;
        background-color: #347ebc;
        cursor: pointer;
    }
</style>
<script>
    import {data} from './index.js';

    export default data;
</script>