# Database Migration with Flyway

This directory contains database migration scripts for the CubeLedger application using Flyway.

## Migration Naming Convention

Flyway migration scripts follow this naming convention:

```
V{version}__{description}.sql
```

Where:
- `{version}` is a version number (like 1, 1.1, 2, etc.)
- `{description}` is a brief description of what the migration does, with words separated by underscores

Example: `V1__init_schema.sql`

## Current Migrations

- **V1__init_schema.sql**: Initial database schema creation
  - Creates the `accounts` table
  - Creates the `transactions` table
  - Sets up foreign key relationships
  - Creates indexes for better performance

- **V1.1__sample_data.sql**: Sample data for testing
  - Inserts sample accounts
  - Inserts sample transactions (deposit, transfer, withdrawal)
  - Demonstrates how to use SQL to populate the database

## Adding New Migrations

To add a new migration:

1. Create a new SQL file in this directory following the naming convention
2. Ensure the version number is higher than any existing migration
3. Write your SQL statements to modify the database schema
4. The migration will be automatically applied when the application starts

## Configuration

Flyway is configured in `application.properties` with these settings:

```properties
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

## Important Notes

- Never modify existing migration scripts after they've been committed
- Always create new migration scripts for schema changes
- Hibernate's `ddl-auto` is set to `validate` to ensure it doesn't modify the schema
