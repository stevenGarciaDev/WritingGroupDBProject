drop table WritingGroup;
drop table Publisher;
drop table Book;

CREATE TABLE WritingGroup (
	GroupName VARCHAR(30) NOT NULL,
	HeadWriter VARCHAR(30),
	YearFormed VARCHAR(4),
	Subject VARCHAR(30),
	CONSTRAINT writinggroup_pk PRIMARY KEY (GroupName)
);

CREATE TABLE Publisher (
	PublisherName VARCHAR(60) NOT NULL,
	PublisherAddress VARCHAR(80),
	PublisherPhone VARCHAR(10),
	PublisherEmail VARCHAR(50),
	CONSTRAINT pk_publisher PRIMARY KEY (PublisherName)
);

CREATE TABLE Book (
	BookTitle VARCHAR(60) NOT NULL,
	YearPublished VARCHAR(4),
	NumberPages INTEGER,
	GroupName VARCHAR(30) NOT NULL,
	PublisherName VARCHAR(60) NOT NULL,
	CONSTRAINT book_pk PRIMARY KEY (GroupName, BookTitle, PublisherName),
	CONSTRAINT writing_groups_fk FOREIGN KEY (groupName)
	REFERENCES WritingGroup (GroupName),
	CONSTRAINT publishers_fk FOREIGN KEY (PublisherName)
	REFERENCES Publisher (PublisherName)
);

insert into WritingGroup (GroupName, HeadWriter, YearFormed, Subject)
    values ('Wonder Pen', 'James Jon', '1999', 'English');

# List all writing groups
SELECT * FROM WritingGroup;

# List all the data for a group specified by the user
SELECT * FROM WritingGroup WHERE groupName = '?';

# List all publishers
SELECT * FROM Publisher;

# List all the data for a pubisher specified by the user
SELECT * FROM Publisher WHERE publisherName = '?';

# List all book titles
SELECT bookTitle FROM Book;

# List all the data for a book specified by the user. 
SELECT * FROM Book WHERE bookTitle = '?'
	NATURAL JOIN Publisher
	NATURAL JOIN WritingGroup;

# Insert a new book
INSERT INTO BOOK VALUES('?', '?', '?');

# Insert a new publisher and update all 
# book published by one publisher to be published
# by the new pubisher.

	# need to enclose both parts in a transaction
INSERT INTO Publisher VALUES('?', '?', '?', '?');
UPDATE Book SET publisherName = '?' 
	WHERE publisherName = '?';

INSERT INTO Publisher(publisherName, publisherAddress,
	publisherPhone, publisherEmail) VALUES 
	('Prestige Worldwide', '9810 Santa Catalina Island', '1111111111', 'prestigeWorldWide@myspace.com'),
	('Harry Potter', '1092 London, England', '7777777777', 'theChosenOne@myspace.com'),
	('Drake', '6666 Toronto, Canada', '6666666666', 'champagnePapi@yolo.com'),
	('Tupac', '1010 Harlem, New York', '9999999999', '2pac@yolo.com'),
	('Willy Wonka', '7680 Chocolate Street, Vermont', '5555555555', 'willyWonka@myspace.com'),
	('Bernie Sanders', '0000 Bottom 99 Persent, Maryland', '0000000000', 'topOnePercent@myspace.com');
