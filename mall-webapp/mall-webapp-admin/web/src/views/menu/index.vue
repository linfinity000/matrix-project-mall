<template>
    <div>
        <top-menu></top-menu>
        <el-container>
            <el-main>
                <el-row>
                    <el-col :span="24">
                        <el-button size="small" type="primary">刷新</el-button>
                        <el-button size="small" type="primary">新增根节点</el-button>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 10px;">
                    <el-col :span="5">
                        <el-input clearable placeholder="搜索" size="small" v-model="treeFilterText"></el-input>
                        <el-tree :data="menuList" :expand-on-click-node="false" :filter-node-method="filterNode"
                                 class="menu-tree"
                                 default-expand-all node-key="menuId" ref="tree">
                            <span class="custom-tree-node" slot-scope="{node, data}">
                                <span>{{data.menuName}}  </span>
                                <span>
                                    <el-button @click="() => append(data)" size="mini" type="text">新增</el-button>
                                    <el-button @click="() => remove(node, data)" size="mini" type="text"
                                               v-if="data.children == null && data.isDefault !== 1">删除</el-button>
                                </span>
                            </span>
                        </el-tree>
                    </el-col>
                </el-row>
            </el-main>
        </el-container>
    </div>
</template>
<script>
    import {data} from './index.js';

    export default data;
</script>
<style scoped type="text/css">
    .menu-tree {
        font-size: 14px;
        margin-top: 10px;
    }
</style>