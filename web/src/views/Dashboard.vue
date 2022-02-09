<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:200px;">
                    <div class="user-info">
                        <img src="../assets/img/user.png" class="user-avabommouttor" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{ name }}</div>
                            <div>{{ role }}</div>
                        </div>
                    </div>
                </el-card>
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="24" style="margin-bottom: 10px;">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-s-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{project}}</div>
                                    <div>项目总数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="24" style="margin-bottom: 10px;">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-s-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{pending}}</div>
                                    <div>待处理</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="24" style="margin-bottom: 10px;">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-s-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{end}}</div>
                                    <div>结课总数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="16">
                <el-card shadow="hover" style="height:545px;">
                    <template #header>
                        <div class="clearfix">
                            <span>待办</span>
                        </div>
                    </template>
                <el-table :data="pendingList" style="width: 100%">
                    <el-table-column prop="title" label="项目名称" width="800px" />
                    <el-table-column
                        prop="status"
                        label="状态"
                        width="100"
                        >
                        <template #default="scope">
                            <el-tag
                            :type="scope.row.status === '申报中' ? '' : 'success'"
                            disable-transitions
                            >{{ scope.row.status }}</el-tag
                            >
                        </template>
                    </el-table-column>
                </el-table>
                
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { reactive , ref } from "vue";
import axios from 'axios'

export default {
    name: "dashboard",
    mounted(){
        // 总数
        axios.get("/api" + "/iresearch/project").then((response)=>{
                let data = response.data;
                this.project = data.length
            }).catch((error)=>{
                console.log(error);
            })
        // 待处理
        axios.get("/api" + "/iresearch/project/status/1").then((response)=>{
                let data = response.data;
                this.pending += data.length
                data.forEach(pro => {
                    this.pendingList.push({
                        'title': pro.name,
                        'status': "申报中",
                    })
                });
            }).catch((error)=>{
                console.log(error);
            })
        axios.get("/api" + "/iresearch/project/status/3").then((response)=>{
                let data = response.data;
                this.pending += data.length
                data.forEach(pro => {
                    this.pendingList.push({
                        'title': pro.name,
                        'status': "结课中",
                    })
                });
            }).catch((error)=>{
                console.log(error);
            })
        // 结课
        axios.get("/api" + "/iresearch/project/status/4").then((response)=>{
                let data = response.data;
                this.end = data.length
            }).catch((error)=>{
                console.log(error);
            })
        },
    setup() {
        const name = localStorage.getItem("ms_username");
        const role = localStorage.getItem("user_type") === "admin" ? "超级管理员" : "普通用户";
        let project = ref(0);
        let pending = ref(0);
        let end = ref(0);
        const pendingList = reactive([]);

        return {
            name,
            pendingList,
            role,
            project,
            pending,
            end,
        };
    },
};
</script>

<style scoped>
.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}
.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}
.grid-num {
    font-size: 30px;
    font-weight: bold;
}
.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}
.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}
.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}
.mgb20 {
    margin-bottom: 20px;
}
</style>
