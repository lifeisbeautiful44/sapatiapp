package global.citytech.borrower.service.listlender;

import global.citytech.user.service.adaptor.ApiResponse;

import java.util.List;

public interface BorrowerService {
    ApiResponse<List<LenderResponse>> getLenderList();
}
