<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <form method="post" v-on:submit.prevent = "addArticle()" >
        <div class="form-group">
          <label for="title">Title</label>
          <input style="margin-top: 10px;" required  v-model="title" type="text" class="form-control" id="title" placeholder="Title">
        </div>
        <div class="form-group">
          <label for="category">Category</label>
          <input style="margin-top: 10px;" required  v-model="category" type="text" class="form-control" id="category" placeholder="Category">
        </div>
        <div class="form-group">
          <label for="contents" style="margin-top: 10px;">Contents</label>
          <input style="margin-top: 10px;" required  v-model="contents" type="text" class="form-control" id="contents" placeholder="Text">
        </div>
        <div class="form-group">
          <label for="tags" style="margin-top: 10px;">Tags</label>
          <input style="margin-top: 10px;" required  v-model="tags" type="text" class="form-control" id="tags" placeholder="Tags">
        </div>
        <br>
        <button type="submit" class="btn btn-primary mt-2">Submit Article</button>
      </form>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "AddArticle",
  data() {
    return {
      title: null,
      category: null,
      contents: null,
      tags: null,
      user: {}
    }
  },
  methods: {
    addArticle() {
      if (!this.title || !this.contents) {
        alert("The title and description must not be empty")
      }

      const email = sessionStorage.getItem("user");
      this.$axios.get(`/api/users/${email}`).then((response) => {
        this.user = response.data;
        console.log(this.user)
      });
      console.log(this.user)
      const tags = this.tags.split(',');

      this.$axios.post(`/api/articles`, {"articleId": 0, "articleTitle": this.title, "articleText": this.contents, "creationDate": new Date(), "visitCount": 0,
        "articleAuthor": this.user,
        "comments": [], "tags": tags,
        "category": this.category}).then(() => {
        this.$router.push(`/news`);
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