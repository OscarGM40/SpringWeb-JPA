package images.s3imagesupload.buckets;

public enum BucketName {
    PROFILE_IMAGE("amigoscode-image-upload-react");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
