<script setup>

</script>

<template>
  <div class="container">
  <div class="pt-5">
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="text" class="form-control" id="email" placeholder="Email">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input v-model="password" type="password" class="form-control" placeholder="Password">
      </div>
      <button type="submit" class="btn btn-primary mt-2">Submit</button>
    </form>
  </div>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    login() {
      this.$axios.post('/api/users/login', {
        email: this.email,
        password: this.password,
      }).then(response => {
        sessionStorage.setItem("jwt", response.data.jwt);
        sessionStorage.setItem("user", this.email);
        this.$router.push({name:'Categories'});
      }).catch(err => {
        if (err.response.status === 422) {
            alert(`Error: ${err.response.data.message}`);
        }
      });
    }
  }
}
</script>

<style scoped>

</style>