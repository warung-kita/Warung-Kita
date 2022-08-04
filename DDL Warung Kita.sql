
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;


create table users (
   user_id serial NOT NULL,
	address varchar(255) not NULL,
	email varchar(255) not NULL,
	full_name varchar(255) not NULL,
	"password" varchar(255) not NULL,
	phone_num varchar(255) not NULL,
	profil_picture varchar(255) NULL,
	username varchar(255) not NULL,
   active bool default true,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_users primary key (user_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table roles (
   role_id serial not null,
   name varchar(255)  not null,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_roles primary key (role_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON roles
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table ekspedisi(
	ekspedisi_id serial not null,
	name varchar(255) not null,
	inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint pk_ekspedisi primary key (ekspedisi_id)
);
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON ekspedisi
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table categories (
   categories_id serial not null,
   name  varchar(255) not null,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_categories primary key (categories_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON categories
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table product_statuses (
   product_status_id serial not null,
   name varchar(255) not null,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_product_statuses primary key (product_status_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON product_statuses
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table user_roles (
   user_id integer not null,
   role_id integer not null,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_user_roles primary key (user_id,role_id),
    foreign key (user_id)references users (user_id),
    foreign key (role_id)references roles (role_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON user_roles
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table products (
   product_id serial not null,
   sku varchar(255) not null,
   product_name varchar(255) not null,
   description text,
   product_status_id integer not null ,
   regular_price numeric default 0,
   quantity  integer default 0,
   product_picture varchar(255) NULL,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
   constraint pk_products primary key (product_id),
   foreign key (product_status_id) references product_statuses (product_status_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON products
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table orders (
   order_id serial not null,
   order_date date not null,
   ekspedisi_id integer not null,
   total numeric not null,
   user_id integer not null,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_sales_orders primary key (order_id),
    foreign key (ekspedisi_id)references ekspedisi (ekspedisi_id),
    foreign key (user_id) references users (user_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE on orders
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table payment (
   payment_id serial not null,
   order_id integer not null,
   date_pay timestamp with time zone,
   amount numeric not null,
   cc_num varchar(255),
   cc_type varchar(255),
   response text,
    inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_payment primary key (payment_id),
   foreign key (order_id)references orders (order_id) 
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON payment
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table product_categories (
   categories_id integer not null,
   product_id  integer not null,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_product_categories primary key (categories_id,product_id),
    foreign key (product_id) references products (product_id),
    foreign key (categories_id)references categories (categories_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON product_categories
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table order_products (
   order_product_id serial not null,
   order_id integer not null,
   sku varchar(255) not null,
   product_id integer not null,
   product_name varchar(255) not null,
   description text not null,
   price numeric not null,
   quantity integer not null,
   subtotal numeric not null,
   inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   constraint pk_order_products primary key (order_product_id),
    foreign key (product_id) references  products (product_id),
     foreign key (order_id)references orders (order_id)
)   ;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON order_products
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table product_list(
	product_list_id serial not null,
	user_id integer not null,
	product_id integer not null,
	 inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint pk_product_list primary key (product_list_id),
    foreign key (user_id)references users (user_id),
    foreign key (product_id)references products (product_id) 
);
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON product_list
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();
