# Chartered-Accountant-BE

REST API for the CA firm website and its back office. The Angular 14 frontend runs separately and talks to this API.

- Spring Boot 4.1.1, Java 17, Maven
- MySQL 8. Flyway owns the schema; Hibernate never creates tables.
- Spring Security with JWT for the back office
- Swagger UI for the API docs

## Run locally

1. Start MySQL 8. The app creates the `ca_firm_db` database on first start.
2. Set your MySQL credentials. The defaults are `root` / `Root@123$`; override them with environment variables:

   ```powershell
   $env:DB_USERNAME = "root"
   $env:DB_PASSWORD = "your-password"
   ```

3. Run the app:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. Open http://localhost:8080/swagger-ui.html

On first start, Flyway creates the 15 tables (`V1__create_schema.sql`) and loads the starter content from the prototype (`V2__seed_content.sql`). The first back-office user is also created from `app.admin.*`: `admin` / `Admin@123` unless overridden. Change that password after signing in.

### Configuration

Every setting in `application.properties` can be overridden with an environment variable:

| Variable | Default | Purpose |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/ca_firm_db?...` | JDBC URL |
| `DB_USERNAME` / `DB_PASSWORD` | `root` / `Root@123$` | MySQL login |
| `JWT_SECRET` | dev-only key | Base64 HMAC key, at least 256 bits. **Set this outside local development.** |
| `JWT_EXPIRATION_MINUTES` | `480` | Token lifetime |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:4200` | Angular origin(s), comma-separated |
| `ADMIN_USERNAME` / `ADMIN_PASSWORD` | `admin` / `Admin@123` | First back-office user, created only when `admin_user` is empty |
| `RESUME_UPLOAD_DIR` | `uploads/resumes` | Where job application resumes are stored |

## Project structure

```
com.ca.charteredAccountant
├── common          Constants, URLConstants (every API path), enums
├── config          SecurityConfig, OpenApiConfig, AppProperties, AdminUserInitializer
├── security        JWT provider and filter, login user lookup, 401/403 handler
├── controller      REST controllers (all extend BaseController)
├── service         Service interfaces
│   └── impl        Service implementations
├── repository      Spring Data JPA repositories
├── dao.model       JPA entities (+ LoggedInUserDetails)
├── request         Request DTOs with validation
├── response        Response DTOs, Response envelope, PageResponse
├── exception       CAException + GlobalExceptionHandler
└── util            CommonUtil

src/main/resources/db/migration   Flyway scripts (V1 schema, V2 seed data)
```

To add a feature, add its paths to `URLConstants`, then follow the chain entity → repository → request/response → service → impl → controller. Schema changes go in a new Flyway script (`V3__...sql`); never edit a migration that has already run.

## API conventions (for the Angular app)

**Base URL:** `http://localhost:8080/api`

- `/api/public/**`: open, used by the website.
- `/api/auth/login`: open, returns a JWT.
- `/api/admin/**`: needs the header `Authorization: Bearer <token>`.

**Response envelope.** Every endpoint except the resume download returns:

```json
{ "statusCode": 200, "message": "Data fetched successfully", "data": { } }
```

- `200`: success, with data in `data`.
- `204`: sent inside an HTTP 200 response. The request worked but there is nothing to show (empty list, unknown slug).
- Errors use the matching HTTP status and the same envelope:
  - `400`: validation failed. `data` maps field name to message, e.g. `{"email": "Enter a valid email address, like name@company.com"}`, so each message can go under its form input.
  - `401`: not signed in, bad credentials or expired token. The Angular interceptor should send the user to login.
  - `403`: STAFF user calling an ADMIN-only endpoint.
  - `404`: the resume download found nothing.
  - `409`: duplicate slug or username, or a record that is still in use.
  - `500`: unexpected error.

**Paged lists** return `data` shaped as `{ content, page, size, totalElements, totalPages }`. They take `page` (0-based) and `size` (max 100) query parameters.

**Resume upload** (`POST /api/public/job/application/apply`) is multipart:

```ts
const form = new FormData();
form.append('application', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
form.append('resume', file);
this.http.post(`${api}/public/job/application/apply`, form);
```

### Roles

- **ADMIN**: everything, including managing back-office users.
- **STAFF**: everything except user management.

The last active ADMIN cannot be disabled or demoted.

### Endpoints

Every path is defined in [`URLConstants`](src/main/java/com/ca/charteredAccountant/common/URLConstants.java), and Swagger UI lists them with request and response shapes.

| Area | Public (website) | Admin (back office) |
|---|---|---|
| Auth | `POST /auth/login` | profile, change password |
| Office locations | list, by slug (city pages) | list, get, save, delete |
| Service categories | list | save, delete |
| Firm services | list, by slug (detail + related), dropdown | list, get, save (with highlights), delete |
| Industries | list | save, delete |
| FAQs | list by `locationSlug` / `serviceSlug` | list, save, delete |
| Testimonials | list | list, save, delete |
| Team members | list (optional `locationSlug`) | list, save, delete |
| Newsletters (articles) | paged list, by slug | paged list, get, save, delete |
| Compliance deadlines | upcoming (home page ledger) | list, save, delete |
| Enquiries | submit (returns `ENQ-2026-000042`) | paged list with status/search, status counts, get, update |
| Newsletter subscribers | subscribe, unsubscribe | paged list |
| Job openings | list, by slug | list, get, save, delete |
| Job applications | apply (multipart) | paged list, update status, download resume |
| Admin users | | list, dropdown, save, disable (ADMIN only, except dropdown) |

"Delete" soft-deletes (sets `active = false`) wherever the table has an `active` column.

## Tests

```powershell
.\mvnw.cmd test
```

`CharteredAccountantApplicationTests` starts the whole app, so it needs MySQL running with valid credentials. The service unit tests use Mockito and don't need a database.
