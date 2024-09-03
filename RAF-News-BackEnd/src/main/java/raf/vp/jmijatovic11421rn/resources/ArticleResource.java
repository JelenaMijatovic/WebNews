package raf.vp.jmijatovic11421rn.resources;

import raf.vp.jmijatovic11421rn.entities.Article;
import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.services.ArticleService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Path("/articles")
public class ArticleResource {

    @Inject
    private ArticleService articleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getAllArticles() {
        return this.articleService.getAllArticles();
    }

    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getArticlesByPage(@PathParam("page") Integer page) {
        return this.articleService.getArticlesByPage(page);
    }

    @GET
    @Path("/{title}/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getArticlesByPageAndCategory(@PathParam("title") String title, @PathParam("page") Integer page) {
        return this.articleService.getArticlesByPageAndCategory(title, page);
    }

    @GET
    @Path("/search/{search}/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getArticlesByString(@PathParam("search") String search, @PathParam("page") Integer page) {
        return this.articleService.getArticlesByString(search, page);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Article getArticle(@PathParam("id") Integer id) {
        return this.articleService.getArticle(id);
    }

    @POST
    @Path("/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editCategory(@PathParam("id") Integer id, @Valid Article article) {
        Map<String, String> response = new HashMap<>();
        List<Article> articles = this.articleService.getAllArticles();

        for (Article art : articles) {
            if (Objects.equals(art.getArticleId(), id)) {
                this.articleService.editArticle(id, article);
                return Response.ok().build();
            }
        }
        response.put("message", "Article with id " + id + " does not exist");
        return Response.status(422).entity(response).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response addArticle(@Valid Article article) {

        this.articleService.addArticle(article);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteCategory(@PathParam("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        List<Article> articles = this.articleService.getAllArticles();

        for (Article art : articles) {
            if (Objects.equals(art.getArticleId(), id)) {
                    this.articleService.deleteArticle(id);
                    return Response.ok().build();
                }
            }

        response.put("message", "Article with id " + id + " does not exist");
        return Response.status(422).entity(response).build();
    }
}

