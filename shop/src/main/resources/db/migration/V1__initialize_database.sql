-- Creating the address table
CREATE TABLE address
(
    address_id     BIGINT PRIMARY KEY,
    country        VARCHAR(255) NOT NULL,
    city           VARCHAR(255) NOT NULL,
    county         VARCHAR(255) NOT NULL,
    street_address VARCHAR(255) NOT NULL
);

-- Creating the location table
CREATE TABLE location
(
    location_id BIGINT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    address_id  BIGINT,
    CONSTRAINT fk_location_address FOREIGN KEY (address_id) REFERENCES address (address_id)
);

-- Creating the users table
CREATE TABLE user_account
(
    user_id       BIGINT PRIMARY KEY,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL
);

-- Creating the product_category table
CREATE TABLE product_category
(
    product_category_id BIGINT PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    description         VARCHAR(255) NOT NULL
);

-- Creating the supplier table
CREATE TABLE supplier
(
    supplier_id BIGINT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

-- Creating the product table
CREATE TABLE product
(
    product_id  BIGINT PRIMARY KEY,
    name        VARCHAR(255)     NOT NULL,
    description VARCHAR(255)     NOT NULL,
    price       DECIMAL(10, 2)   NOT NULL,
    weight      DOUBLE PRECISION NOT NULL,
    category_id BIGINT,
    supplier_id BIGINT,
    image_url   VARCHAR(255)     NOT NULL,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES product_category (product_category_id),
    CONSTRAINT fk_product_supplier FOREIGN KEY (supplier_id) REFERENCES supplier (supplier_id)
);

-- Creating the orders table
CREATE TABLE placed_order
(
    order_id   BIGINT PRIMARY KEY,
    user_id    BIGINT,
    created_at TIMESTAMP NOT NULL,
    address_id BIGINT,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES user_account (user_id),
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES address (address_id)
);

-- Creating the order_detail table
CREATE TABLE order_detail
(
    order_id     BIGINT  NOT NULL,
    product_id   BIGINT  NOT NULL,
    shipped_from BIGINT  NOT NULL,
    quantity     INTEGER NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES placed_order (order_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT fk_shipped_from FOREIGN KEY (shipped_from) REFERENCES location (location_id)
);


-- Creating the stock table
CREATE TABLE stock
(
    product_id  BIGINT,
    location_id BIGINT,
    quantity    INT NOT NULL,
    PRIMARY KEY (product_id, location_id),
    CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT fk_stock_location FOREIGN KEY (location_id) REFERENCES location (location_id)
);
