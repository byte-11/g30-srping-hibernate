package uz.pdp.g30springhibernate.service.impl;

import uz.pdp.g30springhibernate.entity.User;
import uz.pdp.g30springhibernate.repo.UserRepository;
import uz.pdp.g30springhibernate.repo.impl.UserRepoImpl;
import uz.pdp.g30springhibernate.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserServiceImpl() {
    }

    private static UserService instance = null;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private final UserRepository userRepository = UserRepoImpl.getInstance();

    @Override
    public User register(String username, String email, String password) {
        final User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getUserList();
    }

    @Override
    public User getById(UUID id) {
        return userRepository.getById(id);
    }

    @Override
    public User update(User user, UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("'id' parameter is required");
        }
        final User originUser = getById(id);
        originUser.setUsername(Objects.requireNonNullElse(user.getUsername(), originUser.getUsername()));
        originUser.setEmail(Objects.requireNonNullElse(user.getEmail(), originUser.getEmail()));
        originUser.setFirstName(Objects.requireNonNullElse(user.getFirstName(), originUser.getFirstName()));
        originUser.setLastName(Objects.requireNonNullElse(user.getLastName(), originUser.getLastName()));
        originUser.setAge(user.getAge() == null ? originUser.getAge() : user.getAge());
        return userRepository.update(originUser);
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
