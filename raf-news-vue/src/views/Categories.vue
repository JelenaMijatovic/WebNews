<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <div class="categories">
        <h1 class="mt-4">Categories</h1>

        <div class="row">
          <div class="col-4">
            <table class="table">
              <thead>
              <tr>
                <th scope="col">Title</th>
                <th scope="col">Description</th>
                <th scope="col"></th>
                <th scope="col"></th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="category in categories" :key="category.categoryTitle">
                <th scope="row" @click="gotoArticles(category.categoryTitle)">{{ category.categoryTitle}}</th>
                <td>{{ category.description}}</td>
                <td><b-button @click="editCategory(category.categoryTitle)">Edit</b-button></td>
                <td><b-button @click="deleteCategory(category.categoryTitle)">Delete</b-button></td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="col-6">
            <b-button @click="addCategory()">Add Category</b-button>
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
  name: "Categories",
  data() {
    return {
      categories: [],
      currentPage: 1,
      onFirstPage: true,
      onLastPage: false
    }
  },
  methods: {
    gotoArticles(title) {
      this.$router.push(`/news/${title}`);
    },
    addCategory() {
      this.$router.push({name:'AddCategory'});
    },
    editCategory(title) {
      this.$router.push(`/edit/${title}`);
    },
    deleteCategory(title) {
      this.$axios.delete(`/api/categories/${title}`).then(() => {
        this.categories.splice(title, 1);
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
      this.$axios.get(`/api/categories/page/${this.currentPage}`).then((response) => {
        this.categories = response.data;
      });
    },
    onClickNextPage() {
      this.currentPage += 1;
      this.onFirstPage = false;
      this.$axios.get(`/api/categories/page/${this.currentPage}`).then((response) => {
        this.categories = response.data;
        this.onLastPage = this.categories.length < 5;
      });
    }
  },
  created() {
    this.$axios.get('/api/categories/page/1').then((response) => {
      this.categories = response.data;
      this.onLastPage = this.categories.length < 5;
    });
  },
  components: {Navbar},
}
</script>

<style scoped>

</style>