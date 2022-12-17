package hidn.navada.faq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FAQService {
    private final FAQJpaRepo faqJpaRepo;

    // 전제 FAQ 조회
    public List<FAQ> getAllFAQs(){
        return faqJpaRepo.findAll();
    }
}
