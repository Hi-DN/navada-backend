package hidn.navada.product.gcp;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class GCPService {
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.host}")
    private String hostUrl;

    public String uploadProductImage(MultipartFile image) throws IOException {
        if(image==null) return null;

        BlobId blobId= BlobId.of(bucketName,image.getOriginalFilename());
        BlobInfo blobInfo=BlobInfo.newBuilder(blobId).build();
        Blob blob=storage.create(blobInfo,image.getBytes());

        String productImageUrl=hostUrl+bucketName+"/"+blob.getName();
        return productImageUrl;
    }

    public String updateProductImage(String prevImageUrl,MultipartFile newImage) throws IOException {
        if(prevImageUrl!=null){
            removeProductImage(urlToFileName(prevImageUrl));
            return uploadProductImage(newImage);
        }
        return uploadProductImage(newImage);
    }

    private void removeProductImage(String fileName){
        BlobId blobId= BlobId.of(bucketName,fileName);
        storage.delete(blobId);
    }

    private String urlToFileName(String imageUrl){
        String fileName=imageUrl.replace(hostUrl+bucketName+"/","");
        return fileName;
    }
}
