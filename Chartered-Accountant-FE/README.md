# CharteredAccountantFE

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 22.2.0.

## Development server

The frontend talks to the Spring backend (`../Chartered-Accountant-BE`). Start the backend on
port 8080, then run:

```bash
npm start
```

Open `http://localhost:4200/`. Requests to `/api` are proxied to `http://localhost:8080`
(see `proxy.conf.json`), so the browser never makes cross-origin calls.

To work without the backend, serve in-memory sample data instead:

```bash
npm run start:mock
```

Sign in to the back office at `/admin` with admin / Admin@123 (the mock and the backend's
default first user).

### API integration

- `src/app/core/http/api-endpoints.ts` mirrors the backend's `URLConstants.java` name for name.
  Update both together.
- `ApiClient` unwraps the backend's `{ statusCode, message, data }` envelope. A `statusCode` of
  204 means "no data" and resolves to `null` (or `[]` for lists).
- There is one service per backend controller in `src/app/core/services`.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
