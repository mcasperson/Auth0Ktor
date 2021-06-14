A sample Ktor backend API to support the [Auth0 frontend application](https://github.com/auth0-sample-gallery/spa_react_javascript_hello-world).

Set the following environment variables:

```
export ISSUER=https://<yourdomain>.auth0.com
export AUDIENCE=<api audience>
```

Build with `./gradlew installDist`.

Run with `./build/install/ktor-demo/bin/ktor-demo`.