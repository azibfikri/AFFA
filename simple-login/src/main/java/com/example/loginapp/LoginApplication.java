package com.example.loginapp;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.template.mustache.MustacheTemplateEngine;

public class LoginApplication {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFiles.location("/public");

        get("/", (req, res) -> {
            res.redirect("/login");
            return null;
        });

        get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String error = req.queryParams("error");
            if (error != null) {
                model.put("error", "Invalid username or password");
            }
            return render(model, "login.mustache");
        });

        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");

            if (USERNAME.equals(username) && PASSWORD.equals(password)) {
                req.session(true).attribute("user", username);
                res.redirect("/home");
            } else {
                res.redirect("/login?error=true");
            }
            return null;
        });

        get("/home", (req, res) -> {
            String user = req.session().attribute("user");
            if (user == null) {
                res.redirect("/login");
                return null;
            }
            Map<String, Object> model = new HashMap<>();
            model.put("username", user);
            return render(model, "home.mustache");
        });

        post("/logout", (req, res) -> {
            req.session().removeAttribute("user");
            res.redirect("/login");
            return null;
        });
    }

    private static int getHerokuAssignedPort() {
        String port = System.getenv("PORT");
        return port != null ? Integer.parseInt(port) : 4567;
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new MustacheTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
