<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <div class="categories">
        <h1 class="mt-4">News</h1>
        <div class="row">
          <div class="col-4">
            <b-button @click="addArticle()">Write a New Article</b-button>
          </div>
        </div>
        <div class="row">
          <div class="col-4">
            <table class="table">
              <thead>
              <tr>
                <th scope="col">Title</th>
                <th scope="col">Date Created</th>
                <th scope="col">Category</th>
                <th scope="col"></th>
                <th scope="col"></th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="article in articles" :key="article.articleId">
                <th scope="row" @click="gotoArticle(article.articleId)">{{ article.articleTitle}}</th>
                <td>{{ article.creationDate}}</td>
                <td>{{ article.category}}</td>
                <td><b-button @click="editArticle(article.articleId)">Edit</b-button></td>
                <td><b-button @click="deleteArticle(article.articleId)">Delete</b-button></td>
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
  name: "News",
  data() {
    return {
      articles: [],
      currentPage: 1,
      onFirstPage: true,
      onLastPage: false
    }
  },
  methods: {
    gotoArticle(id) {
      this.$router.push(`/news/article/${id}`);
    },
    addArticle() {
      this.$router.push({name:'AddArticle'});
    },
    editArticle(id) {
      this.$router.push(`/news/edit/${id}`);
    },
    deleteArticle(id) {
      this.$axios.delete(`/api/articles/${id}`).then(() => {
        this.articles.splice(id, 1);
        window.location.reload()
      });
    },
    onClickPreviousPage() {
      this.currentPage -= 1;
      this.onLastPage = false;
      if (this.currentPage === 1) {
        this.onFirstPage = true;
      }
      this.$axios.get(`/api/articles/page/${this.currentPage}`).then((response) => {
        this.articles = response.data;
      });
    },
    onClickNextPage() {
      this.currentPage += 1;
      this.onFirstPage = false;
      this.$axios.get(`/api/articles/page/${this.currentPage}`).then((response) => {
        this.articles = response.data;
        this.onLastPage = this.articles.length < 5;
      });
    }
  },
  created() {
    this.$axios.get('/api/articles/page/1').then((response) => {
      this.articles = response.data;
      this.onLastPage = this.articles.length < 5;
    });
  },
  components: {Navbar},
}
</script>

<style scoped>

</style>