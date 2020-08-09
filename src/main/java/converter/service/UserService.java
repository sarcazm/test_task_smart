package converter.service;

import converter.model.User;

public interface UserService {
    User findByUsername(String username);
    void save(User user);
}
