<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link to="/" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Categories'}">Categories</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{name: 'News'}" class="nav-link" :class="{active: this.$router.currentRoute.name === 'News'}">News</router-link>
            </li>
            <li class="nav-item">
              <input type="text" v-model="input" placeholder="Search articles..." />
              <form class="d-flex" @submit.prevent="search">
              <button class="btn btn-outline-secondary" type="submit">Search</button>
              </form>
            </li>
            <li v-if="isAdmin" class="nav-item">
              <router-link to="/users" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Users'}">Users</router-link>
            </li>
            <li class="nav-item">
              <p>Logged in as {{fullName}} </p>
            </li>
          </ul>
          <form class="d-flex" @submit.prevent="logout">
            <button class="btn btn-outline-secondary" type="submit">Logout</button>
          </form>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Navbar",
  data() {
    return {
      fullName: '',
      isAdmin: false,
      user: '',
      input: null
    }
  },
  methods: {
    logout() {
      sessionStorage.removeItem('jwt');
      sessionStorage.removeItem('user');
      this.$router.push({name: 'Login'});
    },
    search() {
      this.$router.push(`/search/${this.input}`);
    }
  },
  mounted() {
    this.user = sessionStorage.getItem("user");
    this.$axios.get(`/api/users/${this.user}`).then((response) => {
      this.fullName = response.data.firstName + " " + response.data.lastName;
      if (response.data.userType === "admin") {
        this.isAdmin = true;
      }
    });
  }
}
</script>

<style scoped>

</style>