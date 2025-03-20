-- Add version column to Address table
ALTER TABLE address ADD COLUMN version BIGINT DEFAULT 0;

-- Add version column to Location table
ALTER TABLE location ADD COLUMN version BIGINT DEFAULT 0;

-- Add version column to OrderDetail table
ALTER TABLE order_detail ADD COLUMN version BIGINT DEFAULT 0;

-- Add version column to PlacedOrder table
ALTER TABLE placed_order ADD COLUMN version BIGINT DEFAULT 0;

-- Add version column to Product table
ALTER TABLE product ADD COLUMN version BIGINT DEFAULT 0;

-- Add version column to ProductCategory table
ALTER TABLE product_category ADD COLUMN version BIGINT DEFAULT 0;

-- Add version column to Stock table
ALTER TABLE stock ADD COLUMN version BIGINT DEFAULT 0;

-- Add version column to Supplier table
ALTER TABLE supplier ADD COLUMN version BIGINT DEFAULT 0;

--Add version column to UserAccount table
ALTER TABLE user_account ADD COLUMN version BIGINT DEFAULT 0;