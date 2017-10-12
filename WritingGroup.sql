create table WritingGroup(
    GroupName   varchar(30) not null,
    Headwriter  varchar(30),
    YearFormed  int,
    Subject     varchar(10),
    constraint writinggroup_pk primary key (GroupName)
);

create table Publisher(
    PublisherName       varchar(30) not null,
    PublisherAddress    varchar(40),
    PublisherPhone      int,
    PublisherEmail      varchar(20),
    constraint publisher_pk primary key (PublisherName)
);

create table Book(
    GroupName       varchar(30) not null,
    BookTitle       varchar(30) not null,
    PublisherName   varchar(30) not null,
    YearPublished   int,
    NumberPages     int,
    constraint book_pk primary key (GroupName, BookTitle, PublisherName),
    constraint writer_book_fk foreign key (GroupName) references WritingGroup
    constraint publisher_book_fk foreign key ()
);
