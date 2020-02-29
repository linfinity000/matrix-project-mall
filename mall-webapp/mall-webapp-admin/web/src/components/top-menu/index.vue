<template>
    <div class="top-menu">
        <el-menu :default-active="activeIndex" @select="handleSelect" mode="horizontal">
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
</style>

<script type="text/javascript">
    import {data} from './index.js';

    export default data;
</script>