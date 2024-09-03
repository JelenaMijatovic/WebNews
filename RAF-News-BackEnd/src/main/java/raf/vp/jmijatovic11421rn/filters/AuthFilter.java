package raf.vp.jmijatovic11421rn.filters;

import raf.vp.jmijatovic11421rn.resources.ArticleResource;
import raf.vp.jmijatovic11421rn.resources.CategoryResource;
import raf.vp.jmijatovic11421rn.resources.UserResource;
import raf.vp.jmijatovic11421rn.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {

        if (!this.isAuthRequired(containerRequestContext)) {
            return;
        }

        try {
            String token = containerRequestContext.getHeaderString("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token)) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private Boolean isAuthRequired(ContainerRequestContext requestContext) {
        if (requestContext.getUriInfo().getPath().contains("login")) {
            return false;
        }

        List<Object> matchedResources = requestContext.getUriInfo().getMatchedResources();
        for (Object matchedResource: matchedResources) {
            if (matchedResource instanceof ArticleResource || matchedResource instanceof CategoryResource) {
                return true;
            }
        }

        return false;
    }
}
