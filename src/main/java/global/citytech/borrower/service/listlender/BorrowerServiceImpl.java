package global.citytech.borrower.service.listlender;

import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adaptor.ApiResponse;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class BorrowerServiceImpl implements BorrowerService {
    @Inject
    UserRepository userRepository;
    @Override
    public ApiResponse<List<?>> getLenderList() {
        List<User> lenderList = userRepository.findByUserType("LENDER");
        List<LenderRequestInfo> listOfLenderRequestInfo = mapLenderListToLenderInfo(lenderList);
        return new ApiResponse<>(200, "Lender List", listOfLenderRequestInfo);
    }
    private List<LenderRequestInfo> mapLenderListToLenderInfo(List<User> lenderList) {
        return lenderList.stream()
                .map(lender -> {
                            LenderRequestInfo lenderRequestInfo = new LenderRequestInfo();
                            lenderRequestInfo.setFirstName(lender.getFirstName());
                            lenderRequestInfo.setLastName(lender.getLastName());
                            lenderRequestInfo.setUserName(lender.getUserName());
                            return lenderRequestInfo;
                        }
                ).collect(Collectors.toList());
    }
}
