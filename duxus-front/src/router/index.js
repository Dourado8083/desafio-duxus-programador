import { createRouter, createWebHistory } from 'vue-router'
import IntegrantesView from '@/views/IntegrantesView.vue'
import TimesView from '@/views/TimesView.vue'
import DashboardView from '@/views/DashboardView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/integrantes' },
    { path: '/integrantes', component: IntegrantesView },
    { path: '/times', component: TimesView },
     { path: '/dashboard', component: DashboardView }
  ]
})

export default router