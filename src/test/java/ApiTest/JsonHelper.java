package ApiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.core.IsNot;

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class JsonHelper {

    private final JsonPath jsonPath;
    private final String isbn;


    public JsonHelper(ValidatableResponse response, String isbn) {
        this.jsonPath = response.extract().body().jsonPath();
        this.isbn = isbn;
    }

    public static void assertFieldNotNullOrEmpty(String fieldName, Object value) {
        assertThat("Field " + fieldName + " should not be null", value, notNullValue());
        assertThat("Field " + fieldName + " should not be empty", value, IsNot.not(empty()));
    }

    public String getField(String field) {
        return jsonPath.getString("'ISBN:" + isbn + "'." + field);
    }

    public static void verifyField(String fieldName, Object actualValue, Object expectedValue) {
        assertThat(fieldName + " is incorrect. Expected: " + expectedValue + ", but got: " + actualValue,
                actualValue,
                equalTo(expectedValue));
    }

}

