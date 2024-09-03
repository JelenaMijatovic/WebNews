package raf.vp.jmijatovic11421rn.resources;

import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.entities.User;
import raf.vp.jmijatovic11421rn.requests.LoginRequest;
import raf.vp.jmijatovic11421rn.services.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "No matches to the submitted credentials");
            return Response.status(422).entity(response).build();
        }

        response.put("jwt", jwt);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        return this.userService.getUser(email);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsersByPage(@PathParam("page") Integer page) {
        return this.userService.getUsersByPage(page);
    }

    @POST
    @Path("/edit/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editUser(@PathParam("email") String email, @Valid User user) {
        Map<String, String> response = new HashMap<>();
        List<User> users = this.userService.getAllUsers();

        for (User u : users) {
            if (Objects.equals(u.getEmail(), user.getEmail()) && !Objects.equals(u.getEmail(), email)) {
                response.put("message", "User with email " + user.getEmail() + " already exists");
                return Response.status(422).entity(response).build();
            }
        }
        for (User u : users) {
            if (Objects.equals(u.getEmail(), email)) {
                this.userService.editUser(email, user);
                return Response.ok().build();
            }
        }
        response.put("message", "User with email " + email + " does not exist");
        return Response.status(422).entity(response).build();
    }
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response addUser(@Valid User user) {
        Map<String, String> response = new HashMap<>();
        List<User> users = this.userService.getAllUsers();

        for (User u : users) {
            if (Objects.equals(u.getEmail(), user.getEmail())) {
                response.put("message", "User with email " + user.getEmail() + " already exists");
                return Response.status(422).entity(response).build();
            }
        }
        this.userService.addUser(user);
        return Response.ok().build();
    }

    @POST
    @Path("/set/{email}/active/{active}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response setUserActive(@PathParam("active") Boolean active, @PathParam("email") String email) {
        Map<String, String> response = new HashMap<>();
        List<User> users = this.userService.getAllUsers();

        for (User u : users) {
            if (Objects.equals(u.getEmail(), email)) {
                this.userService.setUserActive(active, email);
                return Response.ok().build();
            }
        }
        response.put("message", "User with email " + email + " does not exist");
        return Response.status(422).entity(response).build();
    }
}

