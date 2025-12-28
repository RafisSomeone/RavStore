CREATE TABLE products (
    id uuid primary key default uuidv7(),
    name text not null check (btrim(name) <> ''),

    amount_minor BIGINT not null check (amount_minor >= 0),

    currency_code varchar(3) not null check (currency_code ~ '^[A-Z]{3}$'),

    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),

    constraint products_id_is_v7 check (uuid_extract_version(id) = 7)
)