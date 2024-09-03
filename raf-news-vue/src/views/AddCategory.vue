<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <form method="post" v-on:submit.prevent = "addCategory()" >
        <div class="form-group">
          <label for="title">Title</label>
          <input style="margin-top: 10px;" required  v-model="title" type="text" class="form-control" id="title" placeholder="Title">
        </div>
        <div class="form-group">
          <label for="description" style="margin-top: 10px;">Description</label>
          <input style="margin-top: 10px;" required  v-model="description" type="text" class="form-control" id="description" placeholder="Description">
        </div>
        <br>
        <button type="submit" class="btn btn-primary mt-2">Confirm New Category</button>
      </form>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "AddCategory",
  data() {
    return {
      title: null,
      description: null,
    }
  },
  methods: {
    addCategory() {
      if (!this.title || !this.description) {
        alert("The title and description must not be empty")
      }
      this.$axios.post(`/api/categories`, {"categoryTitle": this.title, "description": this.description}).then(() => {
        this.$router.push(`/`);
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