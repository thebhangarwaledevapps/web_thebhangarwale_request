package com.app.request.datasource.network.aws;

import com.app.request.datasource.network.MediaUploadDataSource;
import com.google.photos.types.proto.MediaItem;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@Profile("prod")
public class AWSMediaUploadDataSourceImpl implements MediaUploadDataSource {
    @Override
    public List<MediaItem> uploadMedia(List<MultipartFile> multipartFiles) {
        return null;
    }

    @Override
    public Boolean isMediaDeletedByMediaId(List<String> mediaIds) {
        return null;
    }
}
