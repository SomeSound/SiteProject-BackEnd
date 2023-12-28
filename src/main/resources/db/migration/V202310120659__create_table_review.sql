CREATE TABLE REVIEW (
	TRACK_ID BIGINT NOT NULL,
    CUSTOMER_ID VARCHAR(20) NOT NULL,
    SCORE FLOAT NOT NULL,
    CREATED_DATE TIMESTAMP,
    LAST_MODIFIED_DATE TIMESTAMP
);

ALTER TABLE REVIEW ADD CONSTRAINT REVIEW_CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID);
ALTER TABLE REVIEW ADD CONSTRAINT REVIEW_TRACK_ID_FK FOREIGN KEY (TRACK_ID) REFERENCES TRACK(ID);
