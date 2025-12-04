# Simple Java Login Page

This Maven project spins up a lightweight SparkJava server that shows a login page and a protected home page. The username is `admin` and the password is `password123`.

## Getting Started

```bash
cd /workspaces/ayang/simple-login
mvn package
java -jar target/login-app.jar
```

The app listens on `http://localhost:4567`. Visit `/login` to sign in and then view the home page.

## Notes

- Sessions are kept in memory; restarting the server clears them.
- Update the hard-coded credentials in `LoginApplication` if you want different values.
