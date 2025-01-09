package klerer.citibike.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitiBikeRequestHandlerTest {
    @Test
    void handleRequest() throws IOException {
        // given
        String body = Files.readString(Path.of("request.json"));

        Context context = mock(Context.class);
        APIGatewayProxyRequestEvent event = mock(APIGatewayProxyRequestEvent.class);
        when(event.getBody()).thenReturn(body);
        CitiBikeRequestHandler handler = new CitiBikeRequestHandler();

        // when
        CitiBikeResponse response = handler.handleRequest(event, context);

        // then
        assertEquals(response.start.name, "Lenox Ave & W 146 St");
        assertEquals(response.end.name, "Berry St & N 8 St");

    }
}