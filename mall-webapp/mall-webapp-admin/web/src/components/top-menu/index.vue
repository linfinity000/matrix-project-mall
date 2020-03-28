<template>
    <div class="top-menu">
        <el-menu :default-active="activeIndex" @select="handleSelect" active-text-color="#409eff"
                 mode="horizontal" text-color="#000">
            <div class="outDiv">
                <el-submenu index="avatar">
                    <template slot="title">
                        <div class="avatar">{{avatarSpan}}</div>
                        <span style="margin-left:20px;"></span>
                    </template>
                    <el-menu-item index="user-info">
                        <span v-on:click="userInfo">个人信息</span>
                    </el-menu-item>
                    <el-menu-item index="user-exit">
                        <span v-on:click="exit">退出登录</span>
                    </el-menu-item>
                </el-submenu>
            </div>
            <div :index="item.menuId" :key="item.menuId" class="outDiv" v-for="item in menuList">
                <el-menu-item :index="item.menuId" v-if="item.children == null || item.children.length <= 0">
                    {{item.menuName}}
                </el-menu-item>
                <el-submenu :index="item.menuId" v-if="item.children != null && item.children.length > 0">
                    <template slot="title">{{item.menuName}}<span style="margin-left:20px;"></span></template>
                    <el-menu-item :index="sonItem.menuId" v-for="sonItem in item.children">
                        {{sonItem.menuName}}
                    </el-menu-item>
                </el-submenu>
            </div>
        </el-menu>
    </div>
</template>
<style scoped type="text/css">
    .outDiv {
        display: inline-block;
    }

    .avatar {
        display: inline-block;
        width: 35px;
        height: 35px;
        line-height: 35px;
        text-align: center;
        border-radius: 35px;
        font-size: 18px;
        color: white;
        background-color: rgb(192, 196, 204);
    }
</style>

<script type="text/javascript">
    import {data} from './index.js';

    export default data;
</script>