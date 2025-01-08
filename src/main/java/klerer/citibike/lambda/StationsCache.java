package klerer.citibike.lambda;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import klerer.citibike.CitiBikeService;
import klerer.citibike.CitiBikeServiceFactory;
import klerer.citibike.json.Stations;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;

public class StationsCache {
    //CHECKSTYLE:OFF
    private final String BUCKET_NAME = "klerer.citibike";
    private final String KEY_NAME = "station_information.json";
    //CHECKSTYLE:ON
    private Instant lastModified;
    private Stations stations;
    private Gson gson = new Gson();
    CitiBikeService service;
    S3Client s3Client;

    public StationsCache() {
        Region region = Region.US_EAST_2;
        this.s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        this.service = new CitiBikeServiceFactory().getService();
    }

    public Stations getStations() {
        boolean age = s3Age();
        if (stations != null && lastModified != null && Duration.between(lastModified, Instant.now()).toHours() <= 1) {
            return stations;
        } else if ((stations != null && lastModified != null
                && Duration.between(lastModified, Instant.now()).toHours() > 1)
                || (stations == null && !age)) {
            writeToS3();

        } else {
            readFromS3();
        }
        return stations;
    }

    public void writeToS3() {
        try {
            stations = service.stationInfo().blockingGet();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(KEY_NAME)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromString(gson.toJson(stations)));
            lastModified = Instant.now();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromS3() {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(BUCKET_NAME)
                .key(KEY_NAME)
                .build();

        InputStream in = s3Client.getObject(getObjectRequest);
        stations = gson.fromJson(new InputStreamReader(in), Stations.class);
        lastModified = Instant.now();
    }

    public boolean s3Age() {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(KEY_NAME)
                .build();

        try {
            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);
            lastModified = headObjectResponse.lastModified();
            return Duration.between(lastModified, Instant.now()).toHours() >= 1;
        } catch (Exception e) {
            return false;
        }
    }
}
