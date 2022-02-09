<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户管理
                </el-breadcrumb-item>
                
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="Search">搜索</el-button>
                <el-button type="primary" icon="el-icon-plus" @click="dialogVisible = true">添加用户</el-button>
                <!-- 编辑弹出框 -->
                <el-dialog title="添加用户" v-model="dialogVisible" width="30%">
                    <el-form label-width="70px">
                        <el-form-item label="用户名">
                            <el-input v-model="add.name"></el-input>
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="add.password"></el-input>
                        </el-form-item>
                    </el-form>
                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button type="primary" @click="AddUser">确 定</el-button>
                        </span>
                    </template>
                </el-dialog>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="用户名"></el-table-column>
                <el-table-column label="昵称">
                    <template #default="scope">{{ scope.row.nickname }}</template>
                </el-table-column>
                <el-table-column prop="date" label="注册时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名："> 张三 </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.newpassword"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import axios from 'axios'

export default {
    name: "basetable",
    mounted(){
        // 获取全部用户
        axios.get("/api" + "/iresearch/user").then((response=>{
            let data = response.data;
            data.forEach(element => {
                this.tableData.push({
                    "id": element.id,
                    "name" : element.name,
                    "nickname" : element.nickname,
                    "date" : element.createTime
                })
            });
        })).catch((error)=>{
            console.log(error);
        })
    },
    methods:{
        // 搜索
        Search(){
            axios.get("/api" + "/iresearch/user/" + this.query.name).then((response=>{
                let data = response.data;
                this.tableData = [];
                data.forEach(element => {
                    this.tableData.push({
                        "id": element.id,
                        "name" : element.name,
                        "nickname" : element.nickname,
                        "date" : element.createTime
                    })
                });
            })).catch((error)=>{
                console.log(error);
            })   
        },
        // 添加用户
        AddUser(){
            // console.log(this.form.name);
            if (this.add.name == "" || this.add.password == ""){
                ElMessage.error("输入不能为空");
                return;
            }
            axios.post("/api" + "/iresearch/user?name=" + this.add.name + "&password=" + this.add.password).then((response)=>{
                if(response.status == "200"){
                    ElMessage.success("添加成功");
                    location.reload()
                } else {
                    throw false;
                }
            }).catch((error)=>{
                console.log(error);
                ElMessage.error("修改失败");
            })
        },
    },
    setup() {
        const query = reactive({
            address: "",
            name: "",
            pageIndex: 1,
            pageSize: 10,
        });
        const tableData = ref([]);
        const pageTotal = ref(0);

        let add = reactive({
            name: "",
            password: "",
        });
        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            getData();
        };
        // 删除操作
        const handleDelete = (index,row) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            }).then(() => {
                    axios.delete("/api"+ "/iresearch/user/" +row.id ).then((response=>{
                        if(response.status == "200"){
                            ElMessage.success("删除成功");
                            tableData.value.splice(index, 1);
                        } else {
                            throw false;
                        }
                    })).catch((error)=>{
                        ElMessage.error("删除失败");
                    })
                })
                .catch(() => {
                    ElMessage.error("删除失败");
                });
        };
        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
            name: "",
            password: "",
            newpassword:"",
        });
        let editId = -1;
        const handleEdit = (index, row) => {
            editId = row.id;
            editVisible.value = true;
        };
        // 修改密码
        const saveEdit = () => {
            editVisible.value = false;
            axios.post("/api" + "/iresearch/user/id/" + editId + "/" + form.newpassword).then((response)=>{
                if(response.status == "200"){
                    ElMessage.success("修改成功");
                } else {
                    throw false;
                }
            }).catch((error)=>{
                console.log(error);
                ElMessage.error("修改失败");
            })
        };
        const dialogVisible = ref(false)
        return {
            query,
            tableData,
            add,
            pageTotal,
            editVisible,
            form,
            handlePageChange,
            handleDelete,
            handleEdit,
            saveEdit,
            dialogVisible,
        };
    },
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
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
</style>
