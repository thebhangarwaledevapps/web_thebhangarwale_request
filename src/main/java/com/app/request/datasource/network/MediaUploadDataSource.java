package com.app.request.datasource.network;

import com.google.photos.types.proto.MediaItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public interface MediaUploadDataSource {
    List<MediaItem> uploadMedia(List<MultipartFile> multipartFiles);

    Boolean isMediaDeletedByMediaId(List<String> mediaIds);
}
