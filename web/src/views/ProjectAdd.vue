<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 表单
                </el-breadcrumb-item>
                <el-breadcrumb-item>基本表单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
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
                        <el-button type="primary" @click="onSubmit">表单提交</el-button>
                        <el-button @click="onReset">重置表单</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import axios from 'axios'


export default {
    name: "projectadd",
    mounted(){
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
        // 获取类型

    },
    setup() {
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
        const formRef = ref(null);
        const form = reactive({
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
        // 提交
        const onSubmit = () => {
            // 表单校验
            formRef.value.validate((valid) => {
                let url = "/api" + "/iresearch/project?name="+ form.name +"&inCharge=" + form.inCharge + "&type=" + 
                form.type + "&subject=" + form.options[1] + "&projectPurpose=" + form.projectPurpose + "&economicAnalysis=" +
                form.economicAnalysis + "&existingConditions=" + form.existingConditions + "&expectedResult=" + 
                form.expectedResult + "&viableAnalysis=" + form.viableAnalysis;
                axios.post(url).then((response)=>{
                    if(response.status == "200"){
                        ElMessage.success("添加成功");
                    } else {
                        throw false;
                    }
                }).catch((error)=>{
                    console.log(error);
                    ElMessage.error("修改失败");
                })
            });
        };
        // 重置
        const onReset = () => {
            formRef.value.resetFields();
        };

        return {
            options,
            rules,
            formRef,
            form,
            onSubmit,
            onReset,
        };
    },
};
</script>