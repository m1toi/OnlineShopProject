-- Address table
ALTER TABLE address
    ALTER COLUMN address_id DROP DEFAULT,
ALTER COLUMN address_id ADD GENERATED ALWAYS AS IDENTITY;

-- Location table
ALTER TABLE location
    ALTER COLUMN location_id DROP DEFAULT,
ALTER COLUMN location_id ADD GENERATED ALWAYS AS IDENTITY;

-- User table
ALTER TABLE user_account
    ALTER COLUMN user_id DROP DEFAULT,
ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY;

-- Product category table
ALTER TABLE product_category
    ALTER COLUMN product_category_id DROP DEFAULT,
ALTER COLUMN product_category_id ADD GENERATED ALWAYS AS IDENTITY;

-- Supplier table
ALTER TABLE supplier
    ALTER COLUMN supplier_id DROP DEFAULT,
ALTER COLUMN supplier_id ADD GENERATED ALWAYS AS IDENTITY;

-- Product table
ALTER TABLE product
    ALTER COLUMN product_id DROP DEFAULT,
ALTER COLUMN product_id ADD GENERATED ALWAYS AS IDENTITY;

-- Placed order table
ALTER TABLE placed_order
    ALTER COLUMN order_id DROP DEFAULT,
ALTER COLUMN order_id ADD GENERATED ALWAYS AS IDENTITY;

