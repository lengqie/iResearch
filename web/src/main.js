import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'

import axios from 'axios'

// 请求
axios.interceptors.request.use(function (config) {
   // console.log('请求参数：', config);
   return config;
 }, error => {
   return Promise.reject(error);
 });
// 响应
 axios.interceptors.response.use(function (response) {
   // console.log('返回结果：', response);
   return response;
 }, error => {
   // console.log('返回错误：', error.response);
   // session 失效 (不止401 还有 Network Error 待解决)
   if (error.response.status == "401"){
      localStorage.removeItem("ms_username");
      localStorage.removeItem("user_type");
      router.push("/login");
   }
   return Promise.reject(error);
 });

const app = createApp(App)
installElementPlus(app)
app.use(store)
   .use(router)
   .mount('#app')