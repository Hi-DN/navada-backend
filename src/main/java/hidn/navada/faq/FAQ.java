package hidn.navada.faq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FAQ {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId; //pk

    private String faqQuestion;

    private String faqAnswer;
}
