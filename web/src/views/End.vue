<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 申报管理
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
                        <el-button type="text" icon="el-icon-edit" v-show="scope.row.statusName.endsWith('失败')" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-lock"  v-show="scope.row.statusName.endsWith('失败')" @click="handleEnd(scope.$index, scope.row)">结课</el-button>
                        <div class="sudo" v-show="isRole && (scope.row.statusName.endsWith('中'))">
                            <el-button type="text" icon="el-icon-check" @click="handlePass(scope.$index, scope.row)">通过</el-button>
                            <el-button type="text" icon="el-icon-back" class="red" @click="handleUnpass(scope.$index, scope.row)">驳回</el-button>
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
    name: "end",
    // 代码冗余 过多， 注释 信息 与 Apply.vue Project.vue 相同
    mounted(){
        [3,4,-4].forEach(status => {
            axios.get("/api" + "/iresearch/project/status/" + status).then((response)=>{
                    let data = response.data;
                    data.forEach(element => {
                        this.tableData.push(element)
                    });                    
                }).catch((error)=>{
                    console.log(error);
            })
        });
        axios.get("/api" + "/iresearch/college").then((response=>{
            let data = response.data;
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
        const handlePageChange = (val) => {
            query.pageIndex = val;
        };
        const editVisible = ref(false);
        const form = reactive({
            id :"",
            name: "",
            inCharge: "",
            type: "",
            subject: "",
            projectPurpose: "",
            economicAnalysis:"",
            existingConditions:"",
            expectedResult:"",
            viableAnalysis:"",
            options: [],
        });
        const handleEdit = (index, row) => {
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            form.type = row.projectTypeName == "设计"? '2' : "1"
            axios.get("/api" + "/iresearch/subject").then((response)=>{
                let data = response.data
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

        const PutStatus = (index ,id, newStatus,msg) => {
            axios.put("/api" + "/iresearch/project/" + id + "/status/" + newStatus).then((response)=>{
                if(response.status == "200"){
                    ElMessage.success(msg + "成功");
                    location.reload()
                } else {
                    throw false;
                }
            }).catch((error)=>{
                console.log(error);
                ElMessage.error(msg + "失败");
            })
        }

        const role = localStorage.getItem('user_type')
        const isRole = role == "admin" ? true : false 

        const handlePass = (index, row) => {
            PutStatus(index,row.id,4,"通过")
        }
        const handleUnpass = (index, row) => {
            PutStatus(index,row.id,-4,"驳回")
        }
        // 结课
        const handleEnd = (index, row) => {
            PutStatus(index,row.id,3,"结课申请")
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
            handleEdit,
            onSubmit,
            isRole,
            handlePass,
            handleUnpass,
            handleEnd,
        }
    },
    methods:{
        Search(){
            [3,4,-4].forEach(status => {
                this.tableData = []
                axios.get("/api" + "/iresearch/project/status/"+ status +"/name?name=" + this.query.name).then((response=>{
                    let data = response.data;
                    data.forEach(element => {
                        this.tableData.push(element)
                    });  
                })).catch((error)=>{
                    console.log(error);
                })   
            });
        },
    }
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
