package global.citytech.common.exception;



import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {CustomResponseException.class, ExceptionHandler.class})
public class CustomExceptionHandler implements ExceptionHandler<CustomResponseException, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, CustomResponseException exception) {
        int code = exception.getCode();
        String message = exception.getMessage();
        String data = exception.getData();
        String jsonResponse = String.format("{\"code\": %d, \"message\": \"%s\", \"data\": \"%s\"}", code, message, data);
        return HttpResponse.badRequest().body(jsonResponse);
    }
}
