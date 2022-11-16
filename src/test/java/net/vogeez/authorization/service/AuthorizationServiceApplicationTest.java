package net.vogeez.authorization.service;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = "/sql/oauth2-registered-client-schema.sql")
@Sql(scripts = "/sql/oauth2-authorization-schema.sql")
class AuthorizationServiceApplicationTest {


    /*
    private final String REDIRECT_URI = "http://127.0.0.1/login/oauth2/code/tat";
    private final String AUTHORIZATION_REQUEST = UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", "tat")
            .queryParam("scope", "openid user.read")
            .queryParam("state", "state")
            .queryParam("redirect_uri", REDIRECT_URI)
            .toUriString();

    @Autowired
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        this.webClient.getOptions().setRedirectEnabled(true);
        this.webClient.getCookieManager().clearCookies();
    }

    @Test
    @Order(1)
    void whenRegisterSuccessFull_thenRedirectToLogin() throws IOException {
        HtmlPage signUpPage = this.webClient.getPage("/?signUp");

        HtmlInput usernameInput = signUpPage.getHtmlElementById("username_register");
        HtmlInput emailInput = signUpPage.getHtmlElementById("email_register");
        HtmlInput passwordInput = signUpPage.getHtmlElementById("password_register");
        HtmlSubmitInput signUpButton = signUpPage.getHtmlElementById("submit_register");

        assertThat(usernameInput).isNotNull();
        assertThat(emailInput).isNotNull();
        assertThat(passwordInput).isNotNull();
        assertThat(signUpButton.getValueAttribute()).isEqualTo("Sign Up");

        usernameInput.type("user");
        emailInput.type("user@tat.systems");
        passwordInput.type("difficultPassword");

        WebResponse signUpResponse = signUpButton.click().getWebResponse();
        assertThat(signUpResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Order(2)
    void whenLoginFailed_thenRedirectToLogin() throws IOException {
        HtmlPage page = this.webClient.getPage("/");

        assertLoginPage(page);
        WebResponse signInResponse = signIn(page, "user", "wrongPassword").getWebResponse();
        assertThat(signInResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Order(3)
    void whenLoginSuccessFull_thenRedirectToRedirectUri() throws IOException {
        HtmlPage page = this.webClient.getPage("/");

        assertLoginPage(page);
        WebResponse signInResponse = signIn(page, "user", "difficultPassword").getWebResponse();
        assertThat(signInResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenNotLoggedInAndRequestToken_thenRedirectToLogin() throws IOException {
        this.webClient.getCookieManager().clearCookies();
        this.webClient.getPage("/logout");
        //WebResponse page = this.webClient.getPage(AUTHORIZATION_REQUEST);
        System.out.println("***********************");
        System.out.println(AUTHORIZATION_REQUEST);
        System.out.println(this.webClient.getPage(AUTHORIZATION_REQUEST).getUrl());
        System.out.println(this.webClient.getPage(AUTHORIZATION_REQUEST).getWebResponse().getWebRequest().getRequestBody());
        //assertLoginPage(page);
    }

    @Test
    void whenLoggedInAndRequestToken_thenRedirectToRedirectUri() throws IOException {
        // login
        HtmlPage page = this.webClient.getPage("/");
        signIn(page, "user", "difficultPassword");

        // Request token
        WebResponse response = this.webClient.getPage(AUTHORIZATION_REQUEST).getWebResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.MOVED_PERMANENTLY.value());

        String location = response.getResponseHeaderValue("location");
        assertThat(location).startsWith(REDIRECT_URI);
        assertThat(location).contains("code=");
    }

    private static <P extends Page> P signIn(HtmlPage page, String username, String password) throws IOException {
        HtmlInput usernameInput = page.getHtmlElementById("username_login");
        HtmlInput passwordInput = page.getHtmlElementById("password_login");
        HtmlSubmitInput signInButton = page.getHtmlElementById("submit_login");

        usernameInput.type(username);
        passwordInput.type(password);

        return signInButton.click();
    }

    private static void assertLoginPage(HtmlPage htmlPage) {
        assertThat(htmlPage.getElementById("username_login")).isNotNull();
        assertThat(htmlPage.getElementById("password_login")).isNotNull();
        assertThat(htmlPage.getElementById("submit_login")).isNotNull();
    }
     */
}