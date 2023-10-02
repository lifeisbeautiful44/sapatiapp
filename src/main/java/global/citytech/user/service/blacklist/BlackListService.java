package global.citytech.user.service.blacklist;

import global.citytech.common.exception.CustomResponseException;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;
import java.util.Optional;

public class BlackListService {
    @Inject
    private UserRepository userRepository;
    public void isUserBlacklisted(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            if (user.get().getBlackListed()) {
                throw new CustomResponseException(400, "bad request", user.get().getUserName() + " is blacklisted.");

            }
        } else {
            throw new CustomResponseException(400, "bad request", "User doesn't exist.");
        }
    }
}
