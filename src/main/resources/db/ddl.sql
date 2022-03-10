CREATE TABLE ACCOUNT_USER
(
    ID                      BIGSERIAL    NOT NULL PRIMARY KEY,
    USERNAME                VARCHAR(255) NOT NULL,
    PASSWORD                VARCHAR(255) NOT NULL,
    FIRSTNAME               VARCHAR(64)  NOT NULL,
    LASTNAME                VARCHAR(64)  NOT NULL,
    EMAIL                   VARCHAR(64)  NOT NULL,
    ACCOUNT_NON_EXPIRED     BOOLEAN      NOT NULL,
    ACCOUNT_NON_LOCKED      BOOLEAN      NOT NULL,
    CREDENTIALS_NON_EXPIRED BOOLEAN      NOT NULL,
    ENABLED                 BOOLEAN      NOT NULL,

    UNIQUE (USERNAME)
);

CREATE TABLE AUTHORITY
(
    ID         BIGSERIAL    NOT NULL PRIMARY KEY,
    PERMISSION VARCHAR(255) NOT NULL
);

CREATE TABLE ACCOUNT_ROLE
(
    ID   BIGSERIAL    NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,

    UNIQUE (NAME)
);

CREATE TABLE USER_ROLE
(
    USER_ID BIGINT NOT NULL,
    ROLE_ID BIGINT NOT NULL,

    PRIMARY KEY (USER_ID, ROLE_ID),

    CONSTRAINT FK_USER_ROLE_ACCOUNT_USER
        FOREIGN KEY (USER_ID)
            REFERENCES ACCOUNT_USER (ID),

    CONSTRAINT FK_USER_ROLE_ACCOUNT_ROLE
        FOREIGN KEY (ROLE_ID)
            REFERENCES ACCOUNT_ROLE (ID)
);

CREATE TABLE ROLE_AUTHORITY
(
    AUTHORITY_ID BIGINT NOT NULL,
    ROLE_ID      BIGINT NOT NULL,

    PRIMARY KEY (AUTHORITY_ID, ROLE_ID),

    CONSTRAINT FK_ROLE_AUTHORITY_AUTHORITY
        FOREIGN KEY (AUTHORITY_ID)
            REFERENCES AUTHORITY (ID),

    CONSTRAINT FK_ROLE_AUTHORITY_ACCOUNT_ROLE
        FOREIGN KEY (ROLE_ID)
            REFERENCES ACCOUNT_ROLE (ID)
);

CREATE TABLE PRODUCT
(
    ID                 BIGSERIAL      NOT NULL PRIMARY KEY,
    TITLE              VARCHAR(255)   NOT NULL,
    COST               DECIMAL(10, 2) NOT NULL,
    MANUFACTURE_DATE   DATE           NOT NULL,
    MANUFACTURE_ID     BIGINT         NOT NULL REFERENCES MANUFACTURE,
    VERSION            INT            NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20)    NOT NULL DEFAULT 'ACTIVE',

    UNIQUE (TITLE)
);

-- Мне кажется что от этой таблицы можно избавиться, напрямую соеденив пользователя и товары тк сама таблица не несет в себе никакой полезной информции
-- а у пользователя как правило только одна корзина
CREATE TABLE CART
(
    ID                 BIGSERIAL   NOT NULL PRIMARY KEY,
    USER_ID            BIGINT      NOT NULL REFERENCES ACCOUNT_USER,
    VERSION            INT         NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE CART_PRODUCT
(
    CART_ID       BIGINT NOT NULL REFERENCES CART,
    PRODUCT_ID    BIGINT NOT NULL REFERENCES PRODUCT,
    COUNT_PRODUCT INT    NOT NULL DEFAULT 1,

    PRIMARY KEY (CART_ID, PRODUCT_ID)
);

CREATE TABLE MANUFACTURE
(
    ID                 BIGSERIAL    NOT NULL PRIMARY KEY,
    TITLE              VARCHAR(255) NOT NULL,
    VERSION            INT          NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',

    UNIQUE (TITLE)
);

CREATE TABLE ORDERS
(
    ID                 BIGSERIAL   NOT NULL PRIMARY KEY,
    USER_ID            BIGINT      NOT NULL REFERENCES ACCOUNT_USER,
    VERSION            INT         NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    UNIQUE (TITLE)
);

-- Добавил поля из таблицы продуктов, для храниния информации о них на момент заказа
CREATE TABLE ORDERS_PRODUCT
(
    ORDERS_ID        BIGINT         NOT NULL REFERENCES ORDERS,
    PRODUCT_ID       BIGINT         NOT NULL REFERENCES PRODUCT,
    PRODUCT_TITLE    VARCHAR(255)   NOT NULL,
    COST             DECIMAL(10, 2) NOT NULL,
    MANUFACTURE_DATE DATE           NOT NULL,
    COUNT_PRODUCT    INT            NOT NULL DEFAULT 1,

    PRIMARY KEY (ORDERS_ID, PRODUCT_ID)
);

CREATE TABLE CATEGORIES
(
    ID                 BIGSERIAL    NOT NULL PRIMARY KEY,
    TITLE              VARCHAR(255) NOT NULL,
    VERSION            INT          NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',

    UNIQUE (TITLE)
);

CREATE TABLE SUBCATEGORIES
(
    ID                 BIGSERIAL    NOT NULL PRIMARY KEY,
    TITLE              VARCHAR(255) NOT NULL,
    CATEGORIES_ID      BIGINT       NOT NULL REFERENCES CATEGORIES,
    VERSION            INT          NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',

    UNIQUE (TITLE)
);

CREATE TABLE SUBCATEGORIES_PRODUCT
(
    SUBCATEGORIES_ID BIGINT NOT NULL REFERENCES SUBCATEGORIES,
    PRODUCT_ID       BIGINT NOT NULL REFERENCES PRODUCT,

    PRIMARY KEY (SUBCATEGORIES_ID, PRODUCT_ID)
);


