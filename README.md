A sample Ktor backend API to support the [Auth0 frontend application](https://github.com/auth0-sample-gallery/spa_react_javascript_hello-world).

# Branches

The code is progressively built up over three branches:

* [starter](https://github.com/mcasperson/Auth0Ktor/tree/starter) - the base API with no authentication or authorization. This branch is discussed in this post.
* [add-authorization](https://github.com/mcasperson/Auth0Ktor/tree/add-authorization) - the API requiring a valid access token for the `protected` and `admin` endpoints.
* [add-rbac](https://github.com/mcasperson/Auth0Ktor/tree/add-rbac) - the API requiring special permissions to access the `admin` endpoint.

# Compiling and running

Set the following environment variables:

```
export ISSUER=https://<yourdomain>.auth0.com
export AUDIENCE=<api audience>
```

Build with `./gradlew installDist`.

Run with `./build/install/com.matthewcasperson.ktor-demo/bin/com.matthewcasperson.ktor-demo`.