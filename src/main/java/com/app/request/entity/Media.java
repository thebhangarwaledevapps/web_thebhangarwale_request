package com.app.request.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Media {

    @Id
    private String mediaId;

    @Column(nullable = false,columnDefinition="LONGTEXT")
    private String mediaPath;

    @Column(nullable = false)
    private String mimeType;

    public Media() {
    }

    public Media(String mediaId, String mediaPath, String mimeType) {
        this.mediaId = mediaId;
        this.mediaPath = mediaPath;
        this.mimeType = mimeType;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return mediaId.equals(media.mediaId) &&
                mediaPath.equals(media.mediaPath) &&
                mimeType.equals(media.mimeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaId, mediaPath, mimeType);
    }
}


