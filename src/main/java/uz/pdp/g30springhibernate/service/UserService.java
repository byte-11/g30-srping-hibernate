package uz.pdp.g30springhibernate.service;

import jakarta.servlet.http.HttpServlet;
import uz.pdp.g30springhibernate.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User register(String username, String email, String password);

    User login(String username, String password);

    List<User> getAll();

    User getById(UUID id);

    User update(User user, UUID id);

    boolean delete(UUID id);

    User getByUsername(String username);

    User getByEmail(String email);
}
