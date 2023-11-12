package uz.pdp.g30springhibernate.repo;

import uz.pdp.g30springhibernate.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CRUDRepository<User, UUID> {
    User getByUsername(String username);

    List<User> getUserList();

    User getByEmail(final String email);
}
