package dev.guilhermebpnr.springbootlearning.controller;

import dev.guilhermebpnr.springbootlearning.model.User;
import dev.guilhermebpnr.springbootlearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Path("api/v1/users")
public class UserControllerRestEasy {

    UserService userService;

    @Autowired
    public UserControllerRestEasy(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    public Response fetchUser(@PathParam("userUid") UUID userUid) {
        Optional<User> userOptional = userService.getUser(userUid);
        if (userOptional.isPresent()) {
            return Response.ok(userOptional.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Error("User could not be found."))
                    .build();
        }
    }

    class Error {
        private String errorMessage;

        public Error(String message) {
            this.errorMessage = message;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}