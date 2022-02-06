<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 项目管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.name" placeholder="项目名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="项目名称"></el-table-column>

                <el-table-column prop="address" label="项目类型"></el-table-column>
                <el-table-column prop="address" label="所属学院"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template #default="scope">
                        <el-tag :type="
                                scope.row.state === '成功'
                                    ? 'success'
                                    : scope.row.state === '失败'
                                    ? 'danger'
                                    : ''
                            ">{{ scope.row.state }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="380" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-top" @click="handleEdit(scope.$index, scope.row)">结课
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除&nbsp&nbsp</el-button>
                        <div class="sudo">
                            <el-button type="text" icon="el-icon-check" @click="handleEdit(scope.$index, scope.row)">通过</el-button>
                            <el-button type="text" icon="el-icon-back" class="red" @click="handleEdit(scope.$index, scope.row)">驳回</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="60%" top="3vh">
            <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="项目名称" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="负责人" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="项目类型" prop="region">
                        <el-select v-model="form.region" placeholder="请选择">
                            <el-option key="bbk" label="步步高" value="bbk"></el-option>
                            <el-option key="xtc" label="小天才" value="xtc"></el-option>
                            <el-option key="imoo" label="imoo" value="imoo"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="学科" prop="options">
                        <el-cascader :options="options" v-model="form.options"></el-cascader>
                    </el-form-item>

                    <el-form-item label="预期结果" prop="desc">
                        <el-input type="textarea" rows="2" v-model="form.desc"></el-input>
                    </el-form-item>

                    <el-form-item label="立项目的" prop="desc">
                        <el-input type="textarea" rows="2" v-model="form.desc"></el-input>
                    </el-form-item>

                    <el-form-item label="现有条件" prop="desc">
                        <el-input type="textarea" rows="2" v-model="form.desc"></el-input>
                    </el-form-item>

                    <el-form-item label="方案分析" prop="desc">
                        <el-input type="textarea" rows="2" v-model="form.desc"></el-input>
                    </el-form-item>

                    <el-form-item label="效益分析" prop="desc">
                        <el-input type="textarea" rows="2" v-model="form.desc"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                        <el-button @click="editVisible = false">取 消</el-button>
                    </el-form-item>
                </el-form>
        </el-dialog>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchData } from "../api/index";

export default {
    name: "basetable",
    setup() {
        const query = reactive({
            address: "",
            name: "",
            pageIndex: 1,
            pageSize: 10,
        });
        const tableData = ref([]);
        const pageTotal = ref(0);
        // 获取表格数据
        const getData = () => {
            fetchData(query).then((res) => {
                tableData.value = res.list;
                pageTotal.value = res.pageTotal || 50;
            });
        };
        getData();

        // 查询操作
        const handleSearch = () => {
            query.pageIndex = 1;
            getData();
        };
        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            getData();
        };

        // 删除操作
        const handleDelete = (index) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    ElMessage.success("删除成功");
                    tableData.value.splice(index, 1);
                })
                .catch(() => {});
        };

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
            name: "",
            address: "",
        });
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            editVisible.value = true;
        };
        const saveEdit = () => {
            editVisible.value = false;
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
            });
        };

        return {
            query,
            tableData,
            pageTotal,
            editVisible,
            form,
            handleSearch,
            handlePageChange,
            handleDelete,
            handleEdit,
            saveEdit,
        };
    },
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
