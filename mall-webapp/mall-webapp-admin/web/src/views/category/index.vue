<template>
    <div>
        <top-menu></top-menu>
        <el-container>
            <el-main>
                <el-row>
                    <el-col :span="24">
                        <el-button @click="loadTree" size="small" type="primary">刷新</el-button>
                        <el-button @click="appendRoot" size="small" type="primary">新增根节点</el-button>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 10px;">
                    <el-col :span="4">
                        <el-input clearable placeholder="搜索" size="small" v-model="treeFilterText"></el-input>
                        <el-tree :data="categoryList" :expand-on-click-node="false" :filter-node-method="filterNode"
                                 @node-click="nodeClick" class="category-tree"
                                 default-expand-all node-key="categoryId" ref="tree">
                            <span class="custom-tree-node" slot-scope="{node, data}">
                                <span>{{data.categoryName}}  </span>
                                <span>
                                    <el-button @click="() => append(data)" @click.stop size="mini" type="text"
                                               v-if="data.parentId === '0'">新增</el-button>
                                    <el-button @click="() => remove(data)" @click.stop size="mini" type="text"
                                               v-if="data.children == null && data.isDefault !== 1">删除</el-button>
                                </span>
                            </span>
                        </el-tree>
                    </el-col>
                    <el-col :span="5" style="margin-left: 20px;" v-if="showRuleForm">
                        <el-form :model="ruleForm" :rules="rules" label-width="100px" ref="ruleForm">
                            <el-form-item label="分类名称" prop="categoryName">
                                <el-input size="small" v-model="ruleForm.categoryName"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button @click="save" size="small" type="primary">保存</el-button>
                            </el-form-item>
                        </el-form>
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
    .category-tree {
        font-size: 14px;
        margin-top: 10px;
    }
</style>