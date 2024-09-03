<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <form method="post" v-on:submit.prevent = "editUser()" >
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
        <br>
        <button type="submit" class="btn btn-primary mt-2">Edit User</button>
      </form>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "EditUser",
  data() {
    return {
      email: null,
      firstName: null,
      lastName: null,
      userType: null,
      active: null,
      password: null
    }
  },
  methods: {
    editUser() {
      if (!this.email || !this.firstName || !this.lastName ||!this.userType) {
        alert("No field can be empty")
      }
      this.$axios.post(`/api/users/edit/${this.$route.params.email}`, {"email": this.email, "firstName": this.firstName, "lastName": this.lastName, "userType": this.userType, "active": this.active, "password": this.password}).then(() => {
        this.$router.push({name:'Users'});
      }).catch(err => {
        if (err.response.status === 422) {
          alert(`Error: ${err.response.data.message}`);
        }
      });
    }
  },
    created() {
      this.$axios.get(`/api/users/${this.$route.params.email}`).then((response) => {
        this.email = response.data.email;
        this.firstName = response.data.firstName;
        this.lastName = response.data.lastName;
        this.userType = response.data.userType;
        this.active = response.data.active;
        this.password = response.data.password;
      });
  },
  components: {Navbar},
}
</script>

<style scoped>

</style>