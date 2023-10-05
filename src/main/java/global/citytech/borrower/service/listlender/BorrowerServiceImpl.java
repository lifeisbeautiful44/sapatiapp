package global.citytech.borrower.service.listlender;

import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.common.apiresponse.ApiResponse;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class BorrowerServiceImpl implements BorrowerService {
    @Inject
   private  UserRepository userRepository;



    @Override
    public ApiResponse<List<LenderResponse>> getLenderList() {
        List<User> lenderList = userRepository.findByUserType("LENDER");
        List<LenderResponse> listOfLenderRequestInfo = mapLenderListToLenderInfo(lenderList);
        return new ApiResponse<>(200, "Lender List", listOfLenderRequestInfo);
    }
    private List<LenderResponse> mapLenderListToLenderInfo(List<User> lenderList) {
        return lenderList.stream()
                .map(lender -> {
                            LenderResponse lenderRequestInfo = new LenderResponse();
                            lenderRequestInfo.setFirstName(lender.getFirstName());
                            lenderRequestInfo.setLastName(lender.getLastName());
                            lenderRequestInfo.setUserName(lender.getUserName());
                            return lenderRequestInfo;
                        }
                ).collect(Collectors.toList());
    }
}
