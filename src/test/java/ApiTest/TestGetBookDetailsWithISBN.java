package ApiTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static ApiTest.JsonHelper.verifyField;
import static io.restassured.RestAssured.*;

public class TestGetBookDetailsWithISBN {

    TestData testData;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;


    //9780261103573
    //https://openlibrary.org/api/books?bibkeys=ISBN:{{ISBN}}&format=json&jscmd=data

    @DataProvider
    public Object[][] testDataProvider() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TestData[] testDataArray = objectMapper.readValue(
                new File("src/test/java/resources/test-data.json"),
                TestData[].class
        );
        return Arrays.stream(testDataArray)
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }

    @BeforeTest
    void setUp() throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        TestData[] testDataArray = objectMapper.readValue(new File("src/test/java/resources/test-data.json"), TestData[].class);
        testData = testDataArray[0];


        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://openlibrary.org")
                .setBasePath("/api/books")
                .addQueryParam("format", "json")
                .addQueryParam("jscmd", "data")
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }

    @Test(dataProvider = "testDataProvider")
    public void testGetBookDetails(TestData testData) {

        ValidatableResponse response =
                given()
                        .spec(requestSpecification)
                        .queryParam("bibkeys", "ISBN:" + testData.getIsbn())

                        .when()
                        .get()

                        .then()
                        .spec(responseSpecification);


        Map<String, Object> responseMap = response.extract().jsonPath().getMap("$");

        responseMap.forEach(JsonHelper::assertFieldNotNullOrEmpty);

        JsonHelper helper = new JsonHelper(response, testData.isbn);
        String url = helper.getField("url");
        String key = helper.getField("key");
        String title = helper.getField("title");
        String subtitle = helper.getField("subtitle");
        String authorUrl = helper.getField("authors.url");
        String authorName = helper.getField("authors.name");
        Integer numberOfPages = Integer.valueOf(helper.getField("number_of_pages"));
        String pagination = helper.getField("pagination");
        String identifiers_isbn_13 = helper.getField("identifiers.isbn_13");
        String identifiers_openLibrary = helper.getField("identifiers.openlibrary");
        String classifications = helper.getField("classifications.lc_classifications");
        String publishers = helper.getField("publishers.name");
        String publishDate = helper.getField("publish_date");

        verifyField("URL", url, testData.expectedUrl);
        verifyField("Key", key, testData.expectedKey);
        verifyField("Title", title, testData.expectedTitle);
        verifyField("Subtitle", subtitle, testData.expectedSubtitle);
        verifyField("Author URL", authorUrl, testData.expectedAuthorUrl);
        verifyField("Author Name", authorName, testData.expectedAuthorName);
        verifyField("Number of Pages", numberOfPages, testData.expectedNumberOfPages);
        verifyField("Pagination", pagination, testData.expectedPagination);
        verifyField("Identifiers ISBN-13", identifiers_isbn_13, testData.expectedIdentifiersIsbn13);
        verifyField("Identifiers OpenLibrary", identifiers_openLibrary, testData.expectedIdentifiersOpenLibrary);
        verifyField("Classifications", classifications, testData.expectedClassifications);
        verifyField("Publishers", publishers, testData.expectedPublishers);
        verifyField("Publish Date", publishDate, testData.expectedPublishDate);


    }


}




