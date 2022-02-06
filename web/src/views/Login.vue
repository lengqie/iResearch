<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">iResearch - 科研管理系统</div>
            <el-form :model="user" :rules="rules" ref="validate" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="user.username" placeholder="username">
                        <template #prepend>
                            <el-button icon="el-icon-user"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="user.password"
                        @keyup.enter="Login()">
                        <template #prepend>
                            <el-button icon="el-icon-lock"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="Login()">登录</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import axios from 'axios'

export default {
    setup() {
        const router = useRouter();
        const user = reactive({
            username: "",
            password: "",
        });

        const rules = {
            username: [
                {
                    required: true,
                    message: "请输入用户名",
                    trigger: "blur",
                },
            ],
            password: [
                { required: true, message: "请输入密码", trigger: "blur" },
            ],
        };
        const validate = ref(null);

        const Login = ()=>{
            validate.value.validate()
            axios.get(HOST + '/login?name='+ user.username + '&password=' + user.password).then((response)=>{
               if (response.status == "200"){
                    ElMessage.success("登录成功");
                    localStorage.setItem("ms_username", response.data.type);
                    router.push("/");
               } else {
                    throw false;
               }
            }).catch((err)=>{
                console.log(err);
                ElMessage.error("登录失败");
                return false;
            })
        }

        const store = useStore();
        store.commit("clearTags");

        return {
            user,
            rules,
            validate,
            Login,
        };
    },
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../assets/img/bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #000;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
</style>