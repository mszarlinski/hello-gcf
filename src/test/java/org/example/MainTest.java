package org.example;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class MainTest {
  @Mock private HttpRequest request;
  @Mock private HttpResponse response;

  private BufferedWriter writerOut;
  private StringWriter responseOut;

  @Before
  public void beforeTest() throws IOException {
    MockitoAnnotations.initMocks(this);

    responseOut = new StringWriter();
    writerOut = new BufferedWriter(responseOut);
    when(response.getWriter()).thenReturn(writerOut);
  }

  @Test
  public void functionsHttpMethod_shouldAcceptGet() throws IOException {
    when(request.getMethod()).thenReturn("GET");

    new Main().service(request, response);

    writerOut.flush();
    verify(response, times(1)).setStatusCode(HttpURLConnection.HTTP_OK);
    assertThat(responseOut.toString()).isEqualTo("Hello world!");
  }

  @Test
  public void functionsHttpMethod_shouldForbidPut() throws IOException {
    when(request.getMethod()).thenReturn("PUT");

    new Main().service(request, response);

    writerOut.flush();
    verify(response, times(1)).setStatusCode(HttpURLConnection.HTTP_FORBIDDEN);
    assertThat(responseOut.toString()).isEqualTo("Forbidden!");
  }

  @Test
  public void functionsHttpMethod_shouldErrorOnPost() throws IOException {
    when(request.getMethod()).thenReturn("POST");

    new Main().service(request, response);

    writerOut.flush();
    verify(response, times(1)).setStatusCode(HttpURLConnection.HTTP_BAD_METHOD);
    assertThat(responseOut.toString()).isEqualTo("Something blew up!");
  }
}