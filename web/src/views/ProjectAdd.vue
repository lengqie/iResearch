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
export default {
    name: "baseform",
    setup() {
        const options = [
            {
                value: "guangdong",
                label: "广东省",
                children: [
                    {
                        value: "guangzhou",
                        label: "广州市",
                        children: [
                            {
                                value: "tianhe",
                                label: "天河区",
                            },
                            {
                                value: "haizhu",
                                label: "海珠区",
                            },
                        ],
                    },
                    {
                        value: "dongguan",
                        label: "东莞市",
                        children: [
                            {
                                value: "changan",
                                label: "长安镇",
                            },
                            {
                                value: "humen",
                                label: "虎门镇",
                            },
                        ],
                    },
                ],
            },
            {
                value: "hunan",
                label: "湖南省",
                children: [
                    {
                        value: "changsha",
                        label: "长沙市",
                        children: [
                            {
                                value: "yuelu",
                                label: "岳麓区",
                            },
                        ],
                    },
                ],
            },
        ];
        const rules = {
            name: [
                { required: true, message: "请输入表单名称", trigger: "blur" },
            ],
        };
        const formRef = ref(null);
        const form = reactive({
            name: "",
            region: "",
            date1: "",
            date2: "",
            delivery: true,
            type: ["步步高"],
            resource: "小天才",
            desc: "",
            options: [],
        });
        // 提交
        const onSubmit = () => {
            // 表单校验
            formRef.value.validate((valid) => {
                if (valid) {
                    console.log(form);
                    ElMessage.success("提交成功！");
                } else {
                    return false;
                }
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