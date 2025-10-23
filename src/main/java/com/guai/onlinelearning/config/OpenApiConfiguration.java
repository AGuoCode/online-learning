package com.guai.onlinelearning.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@OpenAPIDefinition(
        info = @Info(
                title = "Online Learning Management API",
                description = """
                        APIs for managing Online Learning:
                        \n\t- User Domain
                        \n\t\t- User
                        \n\t\t- Admin
                        \n\t\t- Staff
                        \n\t\t- Learner
                        \n\t- Course Domain
                        \n\t\t- Course
                        \n\t\t- Course Level
                        \n\t\t- Course TopCategory
                        \n\t\t- Course Category
                        \n\t\t- Feedback
                        """,
                version = "1.0.0",
                contact = @Contact(
                        name = "Myo Wunna Kyaw",
                        email = "mwunnakyaw@gmail.com",
                        url = "https://myowunnakyaw-e1cad.web.app"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080/api/v1",
                        description = "Local Development Server"
                )
        },
        security = @SecurityRequirement(
                name = "Bearer Token"
        )
)
@SecurityScheme(
        name = "Bearer Token",
        description = "JWT Bearer Authentication Token",
        bearerFormat = "JWT",
        scheme = "bearer",
        type = HTTP,
        in = HEADER
)
public class OpenApiConfiguration {
}
