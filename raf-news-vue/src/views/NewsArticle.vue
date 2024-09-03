<script setup>

</script>

<template>
  <div>
    <Navbar/>
    <div class="container">
      <div class="categories">
        <br>
        <p>
          {{ article.category }}
        </p>
        <h2>
          {{ article.articleTitle }}
        </h2>
        <br>
        <p>
          By: {{ fullName }}
          <br>
          Date: {{ article.creationDate }}
        </p>
        <br>
        <p>
          {{ article.articleText }}
        </p>
        <p>Tags: </p>
        <p v-for="tag in tags" :key="tag.tagName">
          {{ tag.tagName }}
        </p>
        <br>
        <p>Comments: </p>
        <p v-for="comment in comments" :key="comment.commentId">
          {{ comment.commentTitle }}
          User: {{ comment.commentAuthor.firstName }} {{ comment.commentAuthor.lastName }}
          <br>
          Date: {{ comment.commentDate }}
          <br>
          {{ comment.commentText }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "NewsArticle",
  data() {
    return {
      article: null,
      tags: [],
      comments: [],
      fullName: ""
    }
  },
  methods: {

  },
  created() {
    this.$axios.get(`/api/articles/${this.$route.params.id}`).then((response) => {
      this.article = response.data;
      this.tags = this.article.tags;
      this.fullName = this.article.articleAuthor.firstName + " " + this.article.articleAuthor.lastName;
      this.comments = this.article.comments;
    });
  },
  components: {Navbar},
}
</script>

<style scoped>

</style>