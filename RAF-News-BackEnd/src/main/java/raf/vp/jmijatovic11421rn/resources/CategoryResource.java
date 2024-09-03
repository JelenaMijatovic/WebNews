package raf.vp.jmijatovic11421rn.resources;

import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Path("/categories")
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategoriesByPage(@PathParam("page") Integer page) {
        return this.categoryService.getCategoriesByPage(page);
    }

    @GET
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategory(@PathParam("title") String title) {
        return this.categoryService.getCategory(title);
    }

    @POST
    @Path("/edit/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editCategory(@PathParam("title") String title, @Valid Category category) {
        Map<String, String> response = new HashMap<>();
        List<Category> categories = this.categoryService.getAllCategories();

        for (Category cat : categories) {
            if (Objects.equals(cat.getCategoryTitle(), category.getCategoryTitle()) && !Objects.equals(cat.getCategoryTitle(), title)) {
                response.put("message", "Category " + category.getCategoryTitle() + " already exists");
                return Response.status(422).entity(response).build();
            }
        }
        for (Category cat : categories) {
            if (Objects.equals(cat.getCategoryTitle(), title)) {
                this.categoryService.editCategory(title, category);
                return Response.ok().build();
            }
        }
        response.put("message", "Category " + title + " does not exist");
        return Response.status(422).entity(response).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response addCategory(@Valid Category category) {

        Map<String, String> response = new HashMap<>();
        List<Category> categories = this.categoryService.getAllCategories();

        for (Category cat : categories) {
            if (Objects.equals(cat.getCategoryTitle(), category.getCategoryTitle())) {
                response.put("message", "Category " + category.getCategoryTitle() + " already exists");
                return Response.status(422).entity(response).build();
            }
        }
        this.categoryService.addCategory(category);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteCategory(@PathParam("title") String title) {
        Map<String, String> response = new HashMap<>();
        List<Category> categories = this.categoryService.getAllCategories();

        for (Category category : categories) {
            if (Objects.equals(category.getCategoryTitle(), title)) {
                if (category.getArticles().isEmpty()) {
                    this.categoryService.deleteCategory(title);
                    return Response.ok().build();
                } else {
                    response.put("message", "Category " + title + " cannot be deleted because it contains articles");
                    return Response.status(422).entity(response).build();
                }
            }
        }
        response.put("message", "Category " + title + " does not exist");
        return Response.status(422).entity(response).build();
    }
}
