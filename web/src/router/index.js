import {createRouter, createWebHashHistory} from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Dashboard.vue")
            }, {
                path: "/users",
                name: "users",
                meta: {
                    title: '用户管理'
                },
                component: () => import ( /* webpackChunkName: "table" */ "../views/Users.vue")
            }, {
                path: "/projectadd",
                name: "projectadd",
                meta: {
                    title: '项目添加'
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/ProjectAdd.vue")
            }, {
                path: "/project",
                name: "project",
                meta: {
                    title: '项目管理'
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/Project.vue")
            }, {
                path: "/apply",
                name: "apply",
                meta: {
                    title: '申报管理'
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/Apply.vue")
            }, {
                path: "/end",
                name: "end",
                meta: {
                    title: '结课管理'
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/End.vue")
            }, {
                path: "/permission",
                name: "permission",
                meta: {
                    title: '权限管理',
                    permission: true
                },
                component: () => import ( /* webpackChunkName: "permission" */ "../views/Permission.vue")
            }, {
                path: "/icon",
                name: "icon",
                meta: {
                    title: '自定义图标'
                },
                component: () => import ( /* webpackChunkName: "icon" */ "../views/Icon.vue")
            }, {
                path: '/404',
                name: '404',
                meta: {
                    title: '找不到页面'
                },
                component: () => import (/* webpackChunkName: "404" */ '../views/404.vue')
            }, {
                path: '/403',
                name: '403',
                meta: {
                    title: '没有权限'
                },
                component: () => import (/* webpackChunkName: "403" */ '../views/403.vue')
            }, {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心'
                },
                component: () => import (/* webpackChunkName: "user" */ '../views/User.vue')
            }
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Login.vue")
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | iResearch - 科研管理系统`;
    const role = localStorage.getItem('user_type');
    if (!role && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;