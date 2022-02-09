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
                        <el-form-item label="昵称">
                            <el-input type="text" v-model="form.nick"></el-input>
                        </el-form-item>
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
            nick:name,
            old: "",
            new: "",
            again: "",
        });
        const onSubmit = () => {
            // 修改昵称
            if(form.nick != ""){
                axios.post("/api" + "/iresearch/user/nickname?nickname=" + form.nick).then((response)=>{
                if (response.status == "200"){
                        localStorage.setItem("ms_username", form.nick);
                        location.reload();
                        ElMessage.success("昵称修改成功");
                } else {
                        throw false;
                }
                }).catch((err)=>{
                    console.log(err);
                    ElMessage.error("修改失败");
                    return false;
                })
            }
            // 修改密码
            if(form.new != "" && form.old != "" && form.again!=""){
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
.info-image:hover .info-edit {
    opacity: 1;
}
.info-name {
    margin: 15px 0 10px;
    font-size: 24px;
    font-weight: 500;
    color: #262626;
}
</style>