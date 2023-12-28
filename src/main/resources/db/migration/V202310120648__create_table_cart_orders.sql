CREATE TABLE CART_ORDERS (
	CUSTOMER_ID VARCHAR(20) NOT NULL,
    ORDER_ID BIGINT NOT NULL,
    CREATED_DATE TIMESTAMP,
    LAST_MODIFIED_DATE TIMESTAMP
);

ALTER TABLE CART_ORDERS ADD CONSTRAINT CART_ORDERS_CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES CART(CUSTOMER_ID);
ALTER TABLE CART_ORDERS ADD CONSTRAINT CART_ORDERS_ORDER_ID_FK FOREIGN KEY (ORDER_ID) REFERENCES _ORDER(ID);