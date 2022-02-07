<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover" style="height: 410px;">
                    <template #header>
                        <div class="clearfix">
                            <span>基础信息</span>
                        </div>
                    </template>
                    <div class="info">
                        <div class="info-image">
                            <img :src="avatarImg" />
                        </div>
                        <div class="info-name">{{ name }}</div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover">
                    <template #header>
                        <div class="clearfix">
                            <span>账户编辑</span>
                        </div>
                    </template>
                    <el-form label-width="90px">
                        <el-form-item label="用户名："> {{ name }} </el-form-item>
                        <el-form-item label="旧密码：">
                            <el-input type="password" v-model="form.old"></el-input>
                        </el-form-item>
                        <el-form-item label="新密码：">
                            <el-input type="password" v-model="form.new"></el-input>
                        </el-form-item>
                        <el-form-item label="确认密码：">
                            <el-input type="password" v-model="form.again"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">保存</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { reactive, ref } from "vue";
import avatar from "../assets/img/user.png";
import axios from 'axios'
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";


export default {
    name: "user",

    setup() {
        const name = localStorage.getItem("ms_username");
        const router = useRouter();
        const form = reactive({
            old: "",
            new: "",
            again: "",
        });
        const onSubmit = () => {
            if (form.old == form.new){
                ElMessage.error("新密码不得相同");
                return false;
            } else if (form.new != form.again){
                ElMessage.error("确认密码不同");
                return false;
            } else{
                axios.post("/api" + "/iresearch/user/" + form.old + "/" + form.new).then((response)=>{
                if (response.status == "200"){
                        ElMessage.success("修改成功");
                        router.push("/login");
                } else {
                        throw false;
                }
                }).catch((err)=>{
                    console.log(err);
                    ElMessage.error("修改失败");
                    return false;
                })
            }
        };

        const avatarImg = ref(avatar);

        return {
            name,
            form,
            onSubmit,
            avatarImg,
        };
    },
};
</script>

<style scoped>
.info {
    text-align: center;
    padding: 35px 0;
}
.info-image {
    position: relative;
    margin: auto;
    width: 100px;
    height: 100px;
    background: #f8f8f8;
    border: 1px solid #eee;
    border-radius: 50px;
    overflow: hidden;
}
.info-image img {
    width: 100%;
    height: 100%;
}
.info-edit {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    opacity: 0;
    transition: opacity 0.3s ease;
}
.info-edit i {
    color: #eee;
    font-size: 25px;
}
.info-image:hover .info-edit {
    opacity: 1;
}
.info-name {
    margin: 15px 0 10px;
    font-size: 24px;
    font-weight: 500;
    color: #262626;
}
.crop-demo-btn {
    position: relative;
}
.crop-input {
    position: absolute;
    width: 100px;
    height: 40px;
    left: 0;
    top: 0;
    opacity: 0;
    cursor: pointer;
}
</style>