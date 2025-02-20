-- Tabla EXCHANGE_RATE
CREATE TABLE EXCHANGE_RATE
(
    ID              BIGINT AUTO_INCREMENT PRIMARY KEY,
    SOURCE_CURRENCY VARCHAR(3)     NOT NULL,
    TARGET_CURRENCY VARCHAR(3)     NOT NULL,
    RATE            DECIMAL(10, 4) NOT NULL,
    CREATED_USER    varchar(50)    NOT NULL,
    CREATED_DATE    datetime       NOT NULL,
    UPDATED_USER    varchar(50),
    UPDATED_DATE    datetime
);

-- Tabla EXCHANGE_TRANSACTION
CREATE TABLE EXCHANGE_TRANSACTION
(
    ID               BIGINT AUTO_INCREMENT PRIMARY KEY,
    SOURCE_CURRENCY  VARCHAR(3)     NOT NULL,
    TARGET_CURRENCY  VARCHAR(3)     NOT NULL,
    ORIGINAL_AMOUNT  DECIMAL(10, 2) NOT NULL,
    CONVERTED_AMOUNT DECIMAL(10, 2) NOT NULL,
    RATE             DECIMAL(10, 4) NOT NULL,
    CREATED_USER     varchar(50)    NOT NULL,
    CREATED_DATE     datetime       NOT NULL,
    UPDATED_USER     varchar(50),
    UPDATED_DATE     datetime
);
