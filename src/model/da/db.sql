create table Customer
(
    id         int primary key,
    fname      nvarchar2(30),
    lname      nvarchar2(30),
    nid        nvarchar2(10),
    gender     varchar2(6),
    birth_date date,
    city       varchar2(10),
    email      nvarchar2(30),
    phone      nvarchar2(30),
    address    nvarchar2(30),
    username   nvarchar2(10),
    password   nvarchar2(10)
);

create table Admin
(
    id         number primary key,
    fname      nvarchar2(30),
    lname      nvarchar2(30),
    nid        nvarchar2(10),
    gender     varchar2(6),
    birth_date date,
    email      nvarchar2(30),
    phone      nvarchar2(30),
    address    nvarchar2(30),
    username   nvarchar2(10),
    password   nvarchar2(10)
);

create table Account
(
    accountNumber number primary key,
    balance       number,
    customer_id   number references Customer,
    accountType   nvarchar2(10)
);

create table Transaction
(
    id                  number primary key,
    amount              nvarchar2(30),
    transactionDateTime timestamp,
    account_src         number references Account,
    account_dst         number,
    transactionType     nvarchar2(10)
);

create table Bill
(
    id          number primary key,
    customer_id number references Customer,
    billNumber  nvarchar2(10),
    amount      number
);

create table Card
(
    id            number primary key,
    accountNumber number(16) references Account,
    pin           number(4)
);

create table Loan
(
    id           number primary key,
    interest     nvarchar2(30),
    amount       nvarchar2(30),
    loanType     nvarchar2(12),
    startDate    timestamp
);

create table Receipt
(
    id                  number references Transaction,
    amount              nvarchar2(30) references Transaction,
    transactionDateTime timestamp references Transaction,
    transactionType     nvarchar2(10) references Transaction,
    account_src         number references Account,
    account_dst         number references Account,
    fname               nvarchar2(30) references Customer,
    lname               nvarchar2(30) references Customer
);

create sequence customer_seq start with 1 increment by 1;
create sequence admin_seq start with 1 increment by 1;
create sequence account_seq start with 1 increment by 1;
create sequence transaction_seq start with 1 increment by 1;
create sequence bill_seq start with 1 increment by 1;
create sequence loan_seq start with 1 increment by 1;
create sequence receipt_seq start with 1 increment by 1;
