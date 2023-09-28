package global.citytech.user.service.updateblacklist;

import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.updateuserstatus.UserStatusDto;
import jakarta.inject.Inject;

import java.util.Optional;

public class UpdateBlackListUserStatusServiceImpl implements UpdateBlackListUserStatusService {

    @Inject
    private UserRepository userRepository;

    @Override
    public void updateBlackListUser(UserStatusDto userStatus) {
        Optional<User> user = userRepository.findByUserName(userStatus.getUserName());
        if (user.isPresent()) {
            User updateUserBlackList = user.get();
            updateUserBlackList.setBlackListed(true);
          userRepository.update(updateUserBlackList);
        }
    }
}
