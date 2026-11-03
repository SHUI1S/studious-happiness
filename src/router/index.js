import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import QuestionBank from '../views/QuestionBank.vue'
import WrongBook from '../views/WrongBook.vue'
import Analysis from '../views/Analysis.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/login', component: Login },
  {
    path: '/',
    component: Layout,
    redirect: '/question',
    children: [
      { path: '/question', name: '题库管理', component: QuestionBank },
      { path: '/wrong', name: '错题本', component: WrongBook },
      { path: '/analysis', name: '错题分析', component: Analysis }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
