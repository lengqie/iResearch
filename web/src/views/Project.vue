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
                <el-button type="primary" icon="el-icon-search" @click="Search">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="项目名称"></el-table-column>

                <el-table-column prop="projectTypeName" label="项目类型"></el-table-column>
                <el-table-column prop="subjectName" label="学科名称"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template #default="scope">
                        <el-tag :type="
                                scope.row.statusName.endsWith('成功')
                                    ? 'success'
                                    : scope.row.statusName.endsWith('中')
                                    ? 'warning'
                                    : 'danger'
                            ">{{ scope.row.statusName }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="380" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-top" @click="handleApply(scope.$index, scope.row)">申报
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
        <el-dialog title="编辑" v-model="editVisible" width="60%" top="3vh">
                <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="项目名称" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="负责人" prop="inCharge">
                        <el-input v-model="form.inCharge"></el-input>
                    </el-form-item>
                    <el-form-item label="项目类型" prop="type">
                        <el-select v-model="form.type" placeholder="请选择">
                            <el-option key="1" label="研究" value="1"></el-option>
                            <el-option key="2" label="设计" value="2"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="学科" prop="options">
                        <el-cascader :options="options" v-model="form.options"  style="width:400px"></el-cascader>
                    </el-form-item>

                    <el-form-item label="立项目的" prop="projectPurpose">
                        <el-input type="textarea" rows="2" v-model="form.projectPurpose"></el-input>
                    </el-form-item>

                    <el-form-item label="可行分析" prop="viableAnalysis">
                        <el-input type="textarea" rows="2" v-model="form.viableAnalysis"></el-input>
                    </el-form-item>

                    <el-form-item label="效益分析" prop="economicAnalysis">
                        <el-input type="textarea" rows="2" v-model="form.economicAnalysis"></el-input>
                    </el-form-item>

                    <el-form-item label="现有条件" prop="existingConditions">
                        <el-input type="textarea" rows="2" v-model="form.existingConditions"></el-input>
                    </el-form-item>

                    <el-form-item label="预期结果" prop="expectedResult">
                        <el-input type="textarea" rows="2" v-model="form.expectedResult"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                    </el-form-item>
                </el-form>
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
        // 总数
        // 无法抽离出 公共函数 ??? 
        [0].forEach(status => {
            axios.get("/api" + "/iresearch/project/status/" + status).then((response)=>{
                    let data = response.data;
                    data.forEach(element => {
                        this.tableData.push(element)
                    });                    
                    // console.log(this.tableData);
                }).catch((error)=>{
                    console.log(error);
            })
        });

        // 获取 科目
        axios.get("/api" + "/iresearch/college").then((response=>{
            let data = response.data;
            // console.log(data);
            data = data.map(o=>{return{value:o.id, label:o.name,children:o.subjects}});
            data.forEach(element => {
                element.children = element.children.map(o=>{return{value:o.id, label:o.name}});
            });
            this.options = data
            this.form.options = data
        })).catch((error)=>{
            console.log(error);
        })
    },
    setup() {
        const query = reactive({
            name: "",
        });
        const tableData = ref([]);
        const pageTotal = ref(0);

        const options = [];
        const rules = {
            name: [{ required: true, message: "请输入项目名称", trigger: "blur" }],
            inCharge: [{ required: true, message: "请输入主管名称", trigger: "blur" }],
            type: [{ required: true, message: "请选择类型", trigger: "blur" }],
            options: [{ required: true, message: "请选择学科", trigger: "blur" }],
            projectPurpose: [{ required: true, message: "请输入立项目的", trigger: "blur" }],
            economicAnalysis: [{ required: true, message: "请输入效益分析", trigger: "blur" }],
            existingConditions: [{ required: true, message: "请输入现有条件", trigger: "blur" }],
            expectedResult: [{ required: true, message: "请输入预期结果", trigger: "blur" }],
            viableAnalysis: [{ required: true, message: "请输入可行分析", trigger: "blur" }],
        };

        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
        };

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        const form = reactive({
            id :"",
            name: "",
            inCharge: "",
            type: "",
            // 控制 前端 的suject 保存在 options中
            subject: "",
            projectPurpose: "",
            economicAnalysis:"",
            existingConditions:"",
            expectedResult:"",
            viableAnalysis:"",
            options: [],
        });
        const handleEdit = (index, row) => {
            //  row 为 post 请求后 tableData中的数据
            //  form 为前端显示 和 提交表单 的数据 
        
            // 将对象 映射到 表单
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            // select 转换
            form.type = row.projectTypeName == "设计"? '2' : "1"
            // 学科转换
            axios.get("/api" + "/iresearch/subject").then((response)=>{
                let data = response.data
                // 不支持 for each ?? 貌似 与 类型有关
                for (const key in data) {
                    if (Object.hasOwnProperty.call(data, key)) {
                        const element = data[key];
                        if(element.name == row.subjectName){
                            form.options = [element.collegeId,element.id]
                        }
                    }
                }

            }).catch((error)=>{
                console.log(error)
            })
            console.log(form);
            editVisible.value = true;
        };
        // 修改项目
        const onSubmit = () => {
            let url = "/api" + "/iresearch/project/" + form.id + "?name=" + form.name +"&inCharge=" + form.inCharge + "&type=" + 
            form.type + "&subject=" + form.options[1] + "&projectPurpose=" + form.projectPurpose + "&economicAnalysis=" +
            form.economicAnalysis + "&existingConditions=" + form.existingConditions + "&expectedResult=" + 
            form.expectedResult + "&viableAnalysis=" + form.viableAnalysis;
            axios.put(url).then((response)=>{
                if(response.status == "200"){
                    ElMessage.success("修改成功");
                    location.reload();
                } else {
                    throw false;
                }
            }).catch((error)=>{
                console.log(error);
                ElMessage.error("修改失败");
            })
        };


        // 修改 状态的 push (公共函数 )
        const PutStatus = (index ,id, newStatus,msg) => {
            axios.put("/api" + "/iresearch/project/" + id + "/status/" + newStatus).then((response)=>{
                if(response.status == "200"){
                    ElMessage.success(msg + "成功");
                    tableData.value.splice(index, 1);
                } else {
                    throw false;
                }
            }).catch((error)=>{
                console.log(error);
                ElMessage.error(msg + "失败");
            })
        }

        // 申报项目
        const handleApply = (index, row) => {
            // console.log(row);
            PutStatus(index,row.id,1,"申报")
        }
        // 删除
        const handleDelete = (index,row) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            }).then(() => {
                    PutStatus(index,row.id,-1,"删除")
                })
                .catch(() => {});
        };
        // 非 管理员 显示
        const role = localStorage.getItem('user_type')
        const isRole = role == "admin" ? true : false 
        // 通过项目
        const handlePass = (index, row) => {
            // console.log(row);
            PutStatus(index,row.id,2,"通过")
        }
        // 驳回项目
        const handleUnpass = (index, row) => {
            // console.log(row);
            PutStatus(index,row.id,-2,"驳回")
        }

        return {
            options,
            rules,
            query,
            tableData,
            pageTotal,
            editVisible,
            form,
            handlePageChange,
            handleDelete,
            handleEdit,
            onSubmit,
            handleApply,
            isRole,
            handlePass,
            handleUnpass,
        }
    },
    methods:{
        // setup 似乎 无法 与前端实现 双向绑定 。
        // 搜索
        Search(){
            axios.get("/api" + "/iresearch/project/status/0/name?name=" + this.query.name).then((response=>{
                let data = response.data;
                // console.log(data);
                this.tableData = data;
            })).catch((error)=>{
                console.log(error);
            })   
        },
    }
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
