package hidn.navada.product.gcp;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class GCPService {
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.host}")
    private String hostUrl;

    public String uploadProductImage(MultipartFile image) throws IOException {
        String uuid= UUID.randomUUID().toString();
        String fileName=uuid+"_"+image.getOriginalFilename();

        BlobId blobId= BlobId.of(bucketName,fileName);
        BlobInfo blobInfo=BlobInfo.newBuilder(blobId).build();

        Blob blob=storage.create(blobInfo,image.getBytes());

        String fileUrl=hostUrl+bucketName+"/"+blob.getName();
        return fileUrl;
    }
}
