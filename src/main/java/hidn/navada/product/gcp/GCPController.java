package hidn.navada.product.gcp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class GCPController {
    private final GCPService gcpService;

    @PostMapping(value = "/upload")
    public String uploadImage(@RequestParam MultipartFile image) throws IOException {
        return gcpService.uploadProductImage(image);
    }
}
