package hidn.navada.faq;

import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class FAQController {
    private final ResponseService responseService;
    private final FAQService faqService;

    @GetMapping("/faqs")
    public ListResponse<FAQ> getAllFaqs(){
        return responseService.getListResponse(faqService.getAllFAQs());
    }
}
