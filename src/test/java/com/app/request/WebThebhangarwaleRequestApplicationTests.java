package com.app.request;

import com.app.request.datasource.network.google.GoogleMediaDataSourceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.UserCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import com.google.photos.library.v1.proto.BatchRemoveMediaItemsFromAlbumResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

@SpringBootTest
class WebThebhangarwaleRequestApplicationTests {

	@Autowired
	GoogleMediaDataSourceImpl googleMediaDataSource;

	@Autowired
	private Environment environment;

	/**
	 [{"id_":"AOqewoKuERW2pfuBoPvG4RUfQiAHZ99pV7cXtPHcCO65UeSegapq0IXr-uQSAYr-RawNiN1mSj5s3Pt_-KvIy4ucJUzvx0n4pQ","description_":"dr._A.P.J.Abdul_Kalam.jpeg","productUrl_":"https://photos.google.com/lr/photo/AOqewoKuERW2pfuBoPvG4RUfQiAHZ99pV7cXtPHcCO65UeSegapq0IXr-uQSAYr-RawNiN1mSj5s3Pt_-KvIy4ucJUzvx0n4pQ","baseUrl_":"https://lh3.googleusercontent.com/lr/ANt_8_ZDW8sSVsJJcqmzz9hwXyIqKYfD7E_r_o3pq0aPGDkTGVhA8aAxXCihXAz2sp0ciPvCBHB2MNnF_cU7iSYvF0aB8xtEY1_aLKuP5DS3tgaIruFesAd9xEVbm1qg75cQKUYF4hFvNO9aZ7W0wazkwJD4OOYcnKnhO11piTkdxLMyaQRwSplA7dvPVtjWSdLyTAY2rrNciexWm6CQALEU7pNokDeUOXSzfqUuszBsplo0-pChg6rmzy0WL-boJlF514NF6NEq4WcF4GiAAtP07pcJYhEtPWNuEd6OSb0SbqSFwDpDsyaPsnyGoUB89yto_FuUM3UWpKlQhcwtGivkwrAlJE-CQ5AEGXlNKGDfw2dbPT6d1Nsfq1U6DtFOQafKDqCizIFFsT3BpHtwdCYw-Qp5JFod-UANFjspuCJWy4MMAYlEaxHdOnnYr-LGEGMYhH1TIeitUF3jBpSOL-NlJY4Vima9p-98_PbHj62soyBwxc-Bps7Luf72It-XsAhTUJpNqqVevFOjR6Nv5L5H12L6blFJ2waFL_7m2Qa14h0FQcClSdJ8CG0re-Gr_tXrtXf1S1BUBRLn7__Ab3tLAWiqZKiJ1MrWoDMbhYP_GfbpElSJAEf4lPezfD49c3QJg-ZmlPyfF7i3N9NHGku2IB3Z8Iyo5I2pC-j3t4-A66_4YoZCkETuw3aW61gwJpGiSfN3Fv5FADj0WZWiHY_xOhX3eq1TYjYgDBLNJ4u-DsHLbF4xsb6XGwtAiSwHGo1bCZXQmY5YgZYsGg-FdX7L1Rg-_VvS3LwIjJIBTSte0F28Wo4yoXDjoQNVFDPeUZl-BYFvY_AGWLdvk77c3K1X-2D5yS48oJSctypP9kKilPlOj3WMAOO7C3-WODpfyui4lX2ypwvGuAOasSSoXUbT498ABASbAOxCt72Uj2fV0BB7uMfogav5Z6p5ZbdEZ46f0Hv0_eqkrFIQq7TgETntlSJXPAcqy1pwV9YS1EQF1jeGOWaxhG0","mimeType_":"image/jpeg","mediaMetadata_":{"metadataCase_":6,"metadata_":{"cameraMake_":"","cameraModel_":"","focalLength_":0.0,"apertureFNumber_":0.0,"isoEquivalent_":0,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"creationTime_":{"seconds_":1675480677,"nanos_":0,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"width_":259,"height_":195,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"filename_":"dr._A.P.J.Abdul_Kalam.jpeg","memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},{"id_":"AOqewoIPxvDDdcQIHp8f5ZwYcdqwavXZPSzZITP0TsejVAE3EprQUGhEvbRwG15BUc1_l9wacRCJwkP7eg6JbKIqG5q4_UrK4w","description_":"mahatma_gandhi.jpeg","productUrl_":"https://photos.google.com/lr/photo/AOqewoIPxvDDdcQIHp8f5ZwYcdqwavXZPSzZITP0TsejVAE3EprQUGhEvbRwG15BUc1_l9wacRCJwkP7eg6JbKIqG5q4_UrK4w","baseUrl_":"https://lh3.googleusercontent.com/lr/ANt_8_YO7CIWnaViW9OiL5BwHf0DL3SYkW48UHNAd2M89Y6yqrr5_rhn-7tcj4BFihiIfDPYQ0UIV8S59hbpdxt9JB02GkUWeyZyBH9ge9jAJskfR6Iq9j1HuZWYaPjDvfr8tS2Hw1fbZaQRw8-GvSIRQ-1ivqHW_XZK75ObyM5za7AXaNUi0iGAOP2MBUjPXVdTBk-naGh2N-w5MvKxO2waw3FiYeh2l8COd48wMiuxTKG-HHU_GQ9cVSzRLcV7fPfsA4lH2g3GMwsfDgdge29burT7cKz_5gbPuV28VE5kBGFt5FsTmd7yOBbfx-_65irvHJjwXsVrZiT_6DRgpgIo0L3kG4NYzGZ3t5tru23P2y4E4jCsQ541rWN6arKImQBqrITcbdJpBiuARAHGaxEm8wWd0_T7rDXnYPIPWFYb8xpKILgg8zXZjdola4vCuSo5EH2eAvq54wWYhjToTVyAp9mYAQ31p7hdKNSHG4LkKdmTvFX8qDML-3uJhf2EjGcmbs8wveYVP8LWo7yWBGSoYwWnO1NRJuwix2i7Tl4ssjcPU7BfVn1pdmhULmn8wJ-3AF-ii-NFksgNlLo53dM0QGc_SfX3JrCo0a2ihC_bbAhjMQGaj2Azd82Rav1Wkvmrk_FlSul0ytOkAY7WG97C3sKT-IC1gAKKWL3TxwXHdRdonDa6wtUTq5rZ-JxgnZYpkQXQ7LK-bgek24ulQvulltfD7mbxXCduPGgtCNziXS73lqcYTU47ICpCjH_TC_nuPr3TaHzzAkl3aiz_OJTOT5bAv-TkpCHSKnpYyFLzQgSwdFr_sa-b_m0BpIm-OpDKZADR_PUnVe0SHJ8cauNG8r7ia5sInkbdbiBW9O4lxvzly9VXDOqhT2n8XhDD3k0EODl5nZaUHVHvHf8vYMGElUYGjRMxeirIpIpNnxKjI4EFGAa3ismFJQou_YN2k7JAkduCthOHR04N2kB0kiB6dbMm-FrTTlLc5UQEW70fSOeHA-Owcf8","mimeType_":"image/jpeg","mediaMetadata_":{"metadataCase_":6,"metadata_":{"cameraMake_":"","cameraModel_":"","focalLength_":0.0,"apertureFNumber_":0.0,"isoEquivalent_":0,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"creationTime_":{"seconds_":1675480677,"nanos_":0,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"width_":246,"height_":205,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"filename_":"mahatma_gandhi.jpeg","memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},{"id_":"AOqewoKN9VcLo8xq_wKOb0COJ4fMMGwF-jUEJ-TbEbWZS0sfpn8JI8bVvtWNDazCcBZSJK91Uod4O5BXsvXp1pVoKn6cuqPwrg","description_":"shree_ram.jpeg","productUrl_":"https://photos.google.com/lr/photo/AOqewoKN9VcLo8xq_wKOb0COJ4fMMGwF-jUEJ-TbEbWZS0sfpn8JI8bVvtWNDazCcBZSJK91Uod4O5BXsvXp1pVoKn6cuqPwrg","baseUrl_":"https://lh3.googleusercontent.com/lr/ANt_8_ZtI60NYkfycJNTyH7p-a0NaufZjZhLoZiJk29VXOkWbb_tLii3dqRTjUry120kP87t0o5hKF0nPMge2mlAJZi5J0VVDrgCiJlGQIdeTf6YYIa9t8eXEped91J6JcILdLPxxdsr3cOSAZdtSYYVZjrUCZlpFkwL8DJHvXL0uhHHqR5gt0ic7v7A28vwdjZLfeuVfA1giso1NNC6J6vBqoJ8gYl8R1yeZ0d-OfW8VBl1R0G32AYBwe7iN_WOvn1dr1grIIitqmaVGvSC-9DzKk5wg1hPOcckKAuwOya4c9dzGpvlYoBVZR7x2mRUpNrz4H_a0lyx8qJx5NF0KHvWRAiMfyi-22Ch-ZYsx_yHv7-tEm2WhGE1ihAiUlyO5ow_lhHpC-XgUkRbgaTK3OeJvOB2c2swknvwhfQ8MiVSRJYoXEfgxx24Mp553Cj5WnvgYrcEUXAPwUhF4VLra0RsrTDbwgaIe_ls14UelCFP-czjAMxhWmatKR8bJJqJh76VDaJEw-bygcN5GkxouOyGaRz7bc41Z-mASH5rnxLxxZFpBN08-DJrOAjrcB0yusjElyaEQ5X93QlHqxqoXnZhaYMzNrKqY5mwDnrwej-nrgK53X4hlgy78gfGoWYxRRVvYrcmbemJv_LFAY98c3lee2jyjq3JMu_zpkwsOt2givJ0RIEDqI9OzQyaaVLovQUGvq8mJl52cqlPyqjHyJHQ7CWXaioY6dwKax_wyJMRimmtwYHOLkFtleDK0roL_a8EPKDTl-BS81boz5WMc_CCrjoRDogX1qP0Bxms2cK6UTuEIJdTl4f8NSPR6566cJhbWRmcHr6Yt9uVjTXn1Zr6ypgzZDDisJWqU3S6GWWq-YZ48CVOgbHMaWhLIorVCURrM60wqfkCmtNAdwMtAji3sN7BPLYSkB1p5Zkjd7uSzrLLnX5U-Ap5Uh2q7BsOBran701mYoDy4yORjzsCBcaB-usoNz9m7Wi2kujQIPmXfSmOSdugeQ0","mimeType_":"image/jpeg","mediaMetadata_":{"metadataCase_":6,"metadata_":{"cameraMake_":"","cameraModel_":"","focalLength_":0.0,"apertureFNumber_":0.0,"isoEquivalent_":0,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"creationTime_":{"seconds_":1675480677,"nanos_":0,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"width_":259,"height_":194,"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0},"filename_":"shree_ram.jpeg","memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0}]
	 * **/

	/*{"memoizedIsInitialized":-1,"unknownFields":{"fields":{},"fieldsDescending":{}},"memoizedSize":-1,"memoizedHashCode":0}*/

	@Test
	void contextLoads() {
		try {
			PhotosLibraryClient photosLibraryClient = googleMediaDataSource.getPhotosLibraryClient();
			BatchRemoveMediaItemsFromAlbumResponse response = photosLibraryClient.batchRemoveMediaItemsFromAlbum(
					environment.getProperty("google.photo.album-id"),
					Arrays.asList("AOqewoIPxvDDdcQIHp8f5ZwYcdqwavXZPSzZITP0TsejVAE3EprQUGhEvbRwG15BUc1_l9wacRCJwkP7eg6JbKIqG5q4_UrK4w")
			);


			System.out.println(response.toString());
		}catch (Exception exception){
			System.out.println(exception);
		}
	}

	@Test
	void test_optional() {
		boolean data = Optional.ofNullable(true)
				.map(new Function<Boolean, Boolean>() {
					@Override
					public Boolean apply(Boolean aBoolean) {
						return true;
					}
				})
				.map(new Function<Boolean, Boolean>() {
					@Override
					public Boolean apply(Boolean aBoolean) {
						return null;
					}
				})
				.orElse(false);

		System.out.println(data);
	}



}
