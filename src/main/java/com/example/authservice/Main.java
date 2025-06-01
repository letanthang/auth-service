package com.example.authservice;

import com.example.authservice.exception.NotFoundTokenException;
import com.example.authservice.exception.NotFoundUserException;
import com.example.authservice.exception.UnauthorizedUserException;
import com.example.authservice.repository.MySQLTokenRepository;
import com.example.authservice.repository.TokenRepository;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.http.HttpResponseException;
import jakarta.persistence.EntityManagerFactory;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import com.example.authservice.config.Config;
import com.example.authservice.config.PersistenceConfig;
import com.example.authservice.controller.AuthController;
import com.example.authservice.dto.ErrorResponse;
import com.example.authservice.repository.MySQLAuthUserRepository;
import com.example.authservice.usecase.impl.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.json.JavalinJackson;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class Main {
    private static final Logger appLog = Logger.getLogger(Main.class.getName());
    private static EntityManagerFactory emf;

    public static void main(String[] args) throws Exception {
        Config.load();
        migrate();
        
        emf = PersistenceConfig.createEntityManagerFactory();
        var authController = getAuthController();

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
            config.jsonMapper(new JavalinJackson(JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build(), true));
        });

        // Add custom error handler for HttpResponseException
        app.exception(HttpResponseException.class, (e, ctx) -> {
            ctx.status(e.getStatus());
            ctx.json(new ErrorResponse(getStatusMessage(e.getStatus()), e.getMessage()));
        });

        app.exception(RuntimeException.class, (e, ctx) -> {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorResponse("SERVER_ERROR", e.getMessage()));
        });

        app.exception(NotFoundUserException.class, (e, ctx) -> {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(new ErrorResponse("NOT_FOUND_USER", e.getMessage()));
        });

        app.exception(UnauthorizedUserException.class, (e, ctx) -> {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.json(new ErrorResponse("UNAUTHORIZED_USER", e.getMessage()));
        });

        app.exception(NotFoundTokenException.class, (e, ctx) -> {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(new ErrorResponse("NOT_FOUND_TOKEN", e.getMessage()));
        });

        // Auth routes
        app.post("/auth/login", authController.login);
        app.post("/auth/logout", authController.logout);
        app.post("/auth/register", authController.register);
        app.get("/auth/verify", authController.verify);

        // Start the server
        app.start(Config.PORT);
        System.out.println("Server started on port " + Config.PORT);

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (emf != null) {
                emf.close();
            }
            app.stop();
        }));
    }

    @NotNull
    private static AuthController getAuthController() {
        var authUserRepository = new MySQLAuthUserRepository(emf);
        var tokenRepository = new MySQLTokenRepository(emf);
        removeExpireSession(tokenRepository);

        LogoutUseCaseImpl logoutUseCase = new LogoutUseCaseImpl(tokenRepository);
        LoginUseCaseImpl loginUseCase = new LoginUseCaseImpl(authUserRepository, tokenRepository);
        RegisterUseCaseImpl registerUseCase = new RegisterUseCaseImpl(authUserRepository);
        VerifyTokenUseCaseImpl verifyTokenUseCase = new VerifyTokenUseCaseImpl(tokenRepository);

        return new AuthController(
            loginUseCase,
            logoutUseCase,
            registerUseCase,
            verifyTokenUseCase
        );
    }

    static void migrate() {
        try (Connection connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASS)) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml", new ClassLoaderResourceAccessor(), database)) {
                liquibase.update(new Contexts(), new LabelExpression());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Migration failed", e);
        }
    }

    static void removeExpireSession(TokenRepository tokenRepository) {
        int deletedRows = tokenRepository.deleteExpiredTokens();
        appLog.info("Deleted " + deletedRows + " expired tokens");  
    }

     public static String getStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "BAD_REQUEST";
            case 401 -> "UNAUTHORIZED";
            case 403 -> "FORBIDDEN";
            case 404 -> "NOT_FOUND";
            case 409 -> "CONFLICT_RESOURCE";
            case 500 -> "INTERNAL_SERVER_ERROR";
            default -> "UNKNOWN";
        };
    }
}
