<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <form method="post" v-on:submit.prevent = "addUser()" >
        <div class="form-group">
          <label for="email">Email</label>
          <input style="margin-top: 10px;" required  v-model="email" type="text" class="form-control" id="email" placeholder="Email">
        </div>
        <div class="form-group">
          <label for="firstName" style="margin-top: 10px;">First Name</label>
          <input style="margin-top: 10px;" required  v-model="firstName" type="text" class="form-control" id="firstName" placeholder="First Name">
        </div>
        <div class="form-group">
          <label for="lastName" style="margin-top: 10px;">Last Name</label>
          <input style="margin-top: 10px;" required  v-model="lastName" type="text" class="form-control" id="lastName" placeholder="Last Name">
        </div>
        <div class="form-group">
          <label for="userType" style="margin-top: 10px;">User Type</label>
          <input style="margin-top: 10px;" required  v-model="userType" type="text" class="form-control" id="userType" placeholder="User Type">
        </div>
        <div class="form-group">
          <label for="password" style="margin-top: 10px;">Password</label>
          <input style="margin-top: 10px;" required  v-model="password" type="password" class="form-control" id="password" placeholder="Password">
        </div>
        <div class="form-group">
          <label for="passwordC" style="margin-top: 10px;">Confirm Password</label>
          <input style="margin-top: 10px;" required  v-model="passwordC" type="password" class="form-control" id="passwordC" placeholder="Confirm Password">
        </div>
        <br>
        <button type="submit" class="btn btn-primary mt-2">Confirm New User</button>
      </form>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "AddUser",
  data() {
    return {
      email: null,
      firstName: null,
      lastName: null,
      userType: null,
      password: null,
      passwordC: null
    }
  },
  methods: {
    addUser() {
      if (!this.email || !this.firstName || !this.lastName ||!this.userType || !this.password ||!this.passwordC) {
        alert("No field can be empty")
      }
      if (this.password !== this.passwordC) {
        alert("Password confirmation is incorrect")
      }
      this.$axios.post(`/api/users`, {"email": this.email, "firstName": this.firstName, "lastName": this.lastName, "userType": this.userType, "active": 1, "password": this.password}).then(() => {
        this.$router.push({name:'Users'});
      }).catch(err => {
        if (err.response.status === 422) {
          alert(`Error: ${err.response.data.message}`);
        }
      });
    }
  },
  components: {Navbar},
}
</script>

<style scoped>

</style>