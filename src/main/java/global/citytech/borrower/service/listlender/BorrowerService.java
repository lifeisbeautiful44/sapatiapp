package global.citytech.borrower.service.listlender;

import global.citytech.common.apiresponse.ApiResponse;

import java.util.List;

public interface BorrowerService {
    ApiResponse<List<LenderResponse>> getLenderList();
}
