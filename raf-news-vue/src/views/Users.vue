<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <div class="categories">
        <h1 class="mt-4">Users</h1>
        <div class="row">
        <div class="col-4">
          <b-button @click="addUser()">Add User</b-button>
        </div>
        </div>
        <div class="row">
          <div class="col-4">
            <table class="table">
              <thead>
              <tr>
                <th scope="col">Email</th>
                <th scope="col">Name</th>
                <th scope="col">User Type</th>
                <th scope="col">Active</th>
                <th scope="col"></th>
                <th scope="col"></th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="user in users" :key="user.email">
                <th scope="row" >{{user.email}}</th>
                <td>{{user.firstName}} {{user.lastName}}</td>
                <td>{{user.userType}}</td>
                <td>{{user.active}}</td>
                <td><b-button @click="editUser(user.email)">Edit</b-button></td>
                <td><b-button v-if="user.userType !== 'admin'" @click="setActivity(user.email, !user.active)">Set Activity</b-button></td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="row">
          <div class="col-4">
            <b-button
                @click="onClickPreviousPage"
                :disabled="onFirstPage"
            >
              Previous
            </b-button>
          </div>
          <div class="col-4">
            <b-button
                @click="onClickNextPage"
                :disabled="onLastPage"
            >
              Next
            </b-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Users",
  data() {
    return {
      users: [],
      currentPage: 1,
      onFirstPage: true,
      onLastPage: false
    }
  },
  methods: {
    addUser() {
      this.$router.push({name:'AddUser'});
    },
    editUser(email) {
      this.$router.push(`/users/edit/${email}`);
    },
    setActivity(email, isActive) {
      this.$axios.post(`/api/users/set/${email}/active/${isActive}`).then(() => {
        window.location.reload()
      }).catch(err => {
        if (err.response.status === 422) {
          alert(`Error: ${err.response.data.message}`);
        }
      });
    },
    onClickPreviousPage() {
      this.currentPage -= 1;
      this.onLastPage = false;
      if (this.currentPage === 1) {
        this.onFirstPage = true;
      }
      this.$axios.get(`/api/users/page/${this.currentPage}`).then((response) => {
        this.users = response.data;
      });
    },
    onClickNextPage() {
      this.currentPage += 1;
      this.onFirstPage = false;
      this.$axios.get(`/api/users/page/${this.currentPage}`).then((response) => {
        this.users = response.data;
        this.onLastPage = this.users.length < 5;
      });
    }
  },
  created() {
    this.$axios.get('/api/users/page/1').then((response) => {
      this.users = response.data;
      this.onLastPage = this.users.length < 5;
    });
  },
  components: {Navbar},
}
</script>

<style scoped>

</style>