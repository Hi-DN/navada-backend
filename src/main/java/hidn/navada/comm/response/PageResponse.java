package hidn.navada.comm.response;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class PageResponse<T> extends CommonResponse{
    List<T> content;
    Pageable pageable;
    int totalPages;
    long totalElements;
    boolean empty;
    boolean first;
    boolean last;
    int numberOfElements;
    int size;
}
