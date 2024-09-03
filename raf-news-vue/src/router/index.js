import VueRouter from 'vue-router'
import Vue from 'vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Categories',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/Categories.vue')
  },
  {
    path: '/add',
    name: 'AddCategory',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/AddCategory.vue')
  },
  {
    path: '/edit/:title',
    name: 'EditCategory',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/EditCategory.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/news',
    name: 'News',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/News.vue')
  },
  {
    path: '/news/:title',
    name: 'NewsByCategory',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/NewsByCategory.vue')
  },
  {
    path: '/news/article/:id',
    name: 'NewsArticle',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/NewsArticle.vue')
  },
  {
    path: '/news/add',
    name: 'AddArticle',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/AddArticle.vue')
  },
  {
    path: '/news/edit/:id',
    name: 'EditArticle',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/EditArticle.vue')
  },
  {
    path: '/search/:query',
    name: 'SearchResults',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/SearchResults.vue')
  },
  {
    path: '/users',
    name: 'Users',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/Users.vue')
  },
  {
    path: '/users/add',
    name: 'AddUser',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/AddUser.vue')
  },
  {
    path: '/users/edit/:email',
    name: 'EditUser',
    meta: {
      authRequired: true,
    },
    component: () => import('../views/EditUser.vue')
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.authRequired) {
    const jwt = sessionStorage.getItem('jwt');

    if (!jwt) {
      next({name: 'Login'});
      return;
    }
  }

  next();
});

export default router
