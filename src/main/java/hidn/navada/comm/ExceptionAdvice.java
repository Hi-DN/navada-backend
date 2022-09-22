package hidn.navada.comm;

import hidn.navada.comm.exception.*;
import hidn.navada.comm.response.CommonResponse;
import hidn.navada.comm.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse defaultException(HttpServletRequest request, Exception e) {
        if (log.isDebugEnabled() || log.isTraceEnabled()) e.printStackTrace();
        return responseService.getErrorResponse(Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("userNotFound.code")),
                getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse userExistException(HttpServletRequest request, UserExistException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("userExistException.code")),
                getMessage("userExistException.msg"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse productNotFoundException(HttpServletRequest request, ProductNotFoundException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("productNotFound.code")),
                getMessage("productNotFound.msg"));
    }

    @ExceptionHandler(ProductExchangeStatusCdDiscrepancyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResponse productExchangeStatusCdDiscrepancyException(HttpServletRequest request, ProductExchangeStatusCdDiscrepancyException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("productExchangeStatusCdDiscrepancy.code")),
                getMessage("productExchangeStatusCdDiscrepancy.msg"));
    }

    @ExceptionHandler(RequestStatusCdDiscrepancyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResponse requestStatusCdDiscrepancyException(HttpServletRequest request, RequestStatusCdDiscrepancyException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("requestStatusCdDiscrepancy.code")),
                getMessage("requestStatusCdDiscrepancy.msg"));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse imageNotFoundException(HttpServletRequest request, ImageNotFoundException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("imageNotFound.code")),
                getMessage("imageNotFound.msg"));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse categoryNotFoundException(HttpServletRequest request, CategoryNotFoundException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("categoryNotFound.code")),
                getMessage("categoryNotFound.msg"));
    }

    @ExceptionHandler(ExchangeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse exchangeNotFoundException(HttpServletRequest request, ExchangeNotFoundException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("exchangeNotFound.code")),
                getMessage("exchangeNotFound.msg"));
    }

    @ExceptionHandler(HeartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse heartNotFoundException(HttpServletRequest request, HeartNotFoundException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("heartNotFound.code")),
                getMessage("heartNotFound.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("accessDenied.code")),
                getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(InputValidationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse inputValidationException(HttpServletRequest request, InputValidationException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("inputValidationException.code")),
                getMessage("inputValidationException.msg"));
    }

    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse businessRuleException(HttpServletRequest request, BusinessRuleException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("businessRuleException.code")),
                getMessage("businessRuleException.msg"));
    }

    @ExceptionHandler(TokenValidationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse tokenValidationException(HttpServletResponse request, TokenValidationException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("tokenValidationException.code")),
                getMessage("tokenValidationException.msg"));
    }

    @ExceptionHandler(DuplicatedRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse DuplicatedRequestException(HttpServletResponse request, DuplicatedRequestException e) {
        return responseService.getErrorResponse(Integer.parseInt(getMessage("duplicatedRequestException.code")),
                getMessage("duplicatedRequestException.msg"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse MethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e){
        return responseService.getErrorResponse(400,e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    // code 정보에 해당하는 메시지 조회
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code 정보와 함께 추가 argument로 현재 locale에 맞는 메시지 조회
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
