CREATE TABLE WritingGroup (
	GroupName VARCHAR(30) NOT NULL,
	HeadWriter VARCHAR(30),
	YearFormed YEAR,
	Subject VARCHAR(30),
	CONSTRAINT writinggroup_pk PRIMARY KEY (GroupName)
);

CREATE TABLE Book (
	BookTitle VARCHAR(60) NOT NULL,
	YearPublished YEAR,
	NumberPages INTEGER,
	GroupName VARCHAR(30) NOT NULL,
	PublisherName VARCHAR(60) NOT NULL,
	CONSTRAINT book_pk PRIMARY KEY (GroupName, BookTitle, PublisherName),
	CONSTRAINT writing_groups_fk FOREIGN KEY (groupName)
	REFERENCES WritingGroup (GroupName),
	CONSTRAINT publishers_fk FOREIGN KEY (publisherName)
	REFERENCES Publisher (publisherName)
);

CREATE TABLE Publisher (
	PublisherName VARCHAR(60) NOT NULL,
	PublisherAddress VARCHAR(80),
	PublisherPhone VARCHAR(10),
	PublisherEmail VARCHAR(50),
	CONSTRAINT pk_publisher PRIMARY KEY (PublisherName)
);
