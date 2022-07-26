package hidn.navada.comm.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResponseService{

    //단일 데이터
    public<T> SingleResponse<T> getSingleResponse(T data){
        SingleResponse<T> singleResponse=new SingleResponse();
        singleResponse.data=data;
        setSuccessResponse(singleResponse);
        return singleResponse;
    }

    //리스트 데이터
    public<T> ListResponse<T> getListResponse(List<T> dataList){
        ListResponse<T> listResponse = new ListResponse();
        listResponse.dataList=dataList;
        setSuccessResponse(listResponse);
        return listResponse;
    }

    //페이지 데이터
    public<T> PageResponse<T> getPageResponse(Page<T> pageData){
        PageResponse<T> pageResponse=new PageResponse<>();
        pageResponse.content=pageData.getContent();
        pageResponse.pageable=pageData.getPageable();
        pageResponse. totalPages=pageData.getTotalPages();
        pageResponse. totalElements=pageData.getTotalElements();
        pageResponse.empty=pageData.isEmpty();
        pageResponse. first=pageData.isFirst();
        pageResponse. last=pageData.isLast();
        pageResponse. numberOfElements=pageData.getNumberOfElements();
        pageResponse. size=pageData.getSize();
        setSuccessResponse(pageResponse);
        return pageResponse;
    }

    //에러 응답
    public CommonResponse getErrorResponse(int code, String message){
        CommonResponse response= new CommonResponse();
        response.success=false;
        response.code=code;
        response.message=message;
        return response;
    }

    //성공결과 응답
    public CommonResponse getSuccessResponse(){
        CommonResponse response=new CommonResponse();
        setSuccessResponse(response);
        return response;
    }

   private void setSuccessResponse(CommonResponse response){
        response.code=0;
        response.success=true;
        response.message="SUCCESS";
    }
}
