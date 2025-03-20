-- Remove version column from Address table
ALTER TABLE address DROP COLUMN version;

-- Remove version column from Location table
ALTER TABLE location DROP COLUMN version;

-- Remove version column from OrderDetail table
ALTER TABLE order_detail DROP COLUMN version;

-- Remove version column from PlacedOrder table
ALTER TABLE placed_order DROP COLUMN version;

-- Remove version column from Product table
ALTER TABLE product DROP COLUMN version;

-- Remove version column from ProductCategory table
ALTER TABLE product_category DROP COLUMN version;

-- Remove version column from Stock table
ALTER TABLE stock DROP COLUMN version;

-- Remove version column from Supplier table
ALTER TABLE supplier DROP COLUMN version;

-- Remove version column from UserAccount table
ALTER TABLE user_account DROP COLUMN version;
