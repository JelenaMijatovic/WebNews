<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <form method="post" v-on:submit.prevent = "editCategory()" >
        <div class="form-group">
          <label for="title">Title</label>
          <input style="margin-top: 10px;" required  v-model="title" type="text" class="form-control" id="title" :placeholder="title">
        </div>
        <div class="form-group">
          <label for="description" style="margin-top: 10px;">Description</label>
          <input style="margin-top: 10px;" required  v-model="description" type="text" class="form-control" id="description" :placeholder="description">
        </div>
        <br>
        <button type="submit" class="btn btn-primary mt-2">Edit Category</button>
      </form>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "EditCategory",
  data() {
    return {
      title: null,
      description: null,
    }
  },
  methods: {
    editCategory() {
      if (!this.title || !this.description) {
        alert("The title and description must not be empty")
      }
      this.$axios.post(`/api/categories/edit/${this.$route.params.title}`, {"categoryTitle": this.title, "description": this.description}).then(() => {
        this.$router.push(`/`);
      }).catch(err => {
        if (err.response.status === 422) {
          alert(`Error: ${err.response.data.message}`);
        }
      });
    }
  },
  created() {
    this.$axios.get(`/api/categories/${this.$route.params.title}`).then((response) => {
      this.title = response.data.categoryTitle;
      this.description = response.data.description;
    });
  },
  components: {Navbar},
}
</script>

<style scoped>

</style>