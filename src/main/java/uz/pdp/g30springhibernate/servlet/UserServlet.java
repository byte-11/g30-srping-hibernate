package uz.pdp.g30springhibernate.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.g30springhibernate.entity.User;
import uz.pdp.g30springhibernate.service.UserService;
import uz.pdp.g30springhibernate.service.impl.UserServiceImpl;
import uz.pdp.g30springhibernate.utils.ResponseManager;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String username = req.getParameter("username");
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final User user = userService.register(username, email, password);
        ResponseManager.sendJsonResponse(resp, user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String id = req.getParameter("id");
        final String username = req.getParameter("username");
        final String email = req.getParameter("email");

        if (id == null && username == null && email == null) {
            final List<User> users = userService.getAll();
            ResponseManager.sendJsonResponse(resp, users);
            return;
        }

        User user = null;
        if (id != null) {
            user = userService.getById(UUID.fromString(id));
        } else if (username != null) {
            user = userService.getByUsername(username);
        } else if (email != null) {
            user = userService.getByEmail(email);
        }
        ResponseManager.sendJsonResponse(resp, user);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID id = UUID.fromString(req.getParameter("id"));
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Integer age = req.getParameter("age") != null ? Integer.parseInt(req.getParameter("age")) : null;
        User user = User.builder()
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
        try {
            User updatedUser = userService.update(user, id);
            ResponseManager.sendJsonResponse(resp, updatedUser);
        } catch (Exception e) {
            ResponseManager.sendBadRequestJsonResponse(resp, e.getMessage());
        }
    }
}
