# these are each to be run individually
drop table Book;
drop table WritingGroup;
drop table Publisher;


/* -------------
Start Here for setting up database
------------- */

CREATE TABLE WritingGroups (
	GroupName VARCHAR(30) NOT NULL,
	HeadWriter VARCHAR(30),
	YearFormed VARCHAR(4),
	Subject VARCHAR(30),
	CONSTRAINT writinggroup_pk PRIMARY KEY (GroupName)
);

CREATE TABLE Publishers (
	PublisherName VARCHAR(60) NOT NULL,
	PublisherAddress VARCHAR(80),
	PublisherPhone VARCHAR(10),
	PublisherEmail VARCHAR(50),
	CONSTRAINT pk_publisher PRIMARY KEY (PublisherName)
);

CREATE TABLE Books (
	BookTitle VARCHAR(60) NOT NULL,
	YearPublished VARCHAR(4),
	NumberPages INTEGER,
	GroupName VARCHAR(30) NOT NULL,
	PublisherName VARCHAR(60) NOT NULL,
	CONSTRAINT book_pk PRIMARY KEY (GroupName, BookTitle, PublisherName),
	CONSTRAINT writing_groups_fk FOREIGN KEY (groupName)
	REFERENCES WritingGroups (GroupName),
	CONSTRAINT publishers_fk FOREIGN KEY (PublisherName)
	REFERENCES Publishers (PublisherName)
);

INSERT INTO Publishers(publisherName, publisherAddress,
	publisherPhone, publisherEmail) VALUES 
	('Prestige Worldwide', '9810 Santa Catalina Island', '1111111111', 'prestigeWorldWide@myspace.com'),
	('Harry Potter', '1092 London, England', '7777777777', 'theChosenOne@myspace.com'),
	('Drake', '6666 Toronto, Canada', '6666666666', 'champagnePapi@yolo.com'),
	('Tupac', '1010 Harlem, New York', '9999999999', '2pac@yolo.com'),
	('Willy Wonka', '7680 Chocolate Street, Vermont', '5555555555', 'willyWonka@myspace.com'),
	('Bernie Sanders', '0000 Bottom 99 Persent, Maryland', '0000000000', 'topOnePercent@myspace.com');

INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES
	('ASAP MOB', 'Asap Rocky', '2011', 'Hip Hop'),
	('Wu Tang Clan', 'RZA', '1990', 'Hip Hop'),
	('Michelin Guide', 'Some French Guy', '1900', 'Restaurants'),
	('Hacker News', 'Paul Graham', '2000', 'Technology');

INSERT INTO Books(GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
	('ASAP MOB', 'So you want to be a rapper', 'Prestige Worldwide', '2011', 111),
	('ASAP MOB', 'So you want to be a gangsta', 'Tupac', '2014', 222),
	('Wu Tang Clan', 'Rapping for Fun and profit', 'Tupac', '1991', 121),
	('Wu Tang Clan', 'Rapping For Dummies', 'Prestige Worldwide', '1990', 131),

	('Wu Tang Clan', 'So you want to run for president', 'Prestige Worldwide', '1996', 131),
	('Michelin Guide', 'The Art of French Cooking', 'Drake', '1980', 421),
	('Michelin Guide', 'How to Make Good BBQ for dummies', 'Drake', '2017', 412),
	('Michelin Guide', 'How to make french toast for dummies', 'Willy Wonka', '2013', 312),

	('Michelin Guide', 'How to flip pancakes for dummies', 'Willy Wonka', '2002', 267),
	('Hacker News', 'How to make an app', 'Bernie Sanders', '2016', 211),
	('Hacker News', 'How to code for dummies', 'Drake', '2015', 298),
	('Hacker News', 'how to flip pizza for dummies', 'Harry Potter', '2009', 165);

/* -------------
Stop Here for setting up database
------------- */

# Queries for each corresponding case in Controller.java file

# case 1: List all writing groups

	"SELECT GroupName, Headwriter, YearFormed, Subject FROM WritingGroups";

# case 2: List all the data for a group specified by the user (include all tables)

	"SELECT GroupName, Headwriter, YearFormed, Subject FROM WritingGroups WHERE GroupName = ?";
	"SELECT * FROM Books WHERE groupName = ?";

# case 3: List all publishers

	"SELECT PublisherName, PublisherAddress, PublisherPhone, PublisherEmail FROM Publishers";

# case 4: List all the data for a pubisher specified by the user (include all tables)

	"SELECT PublisherName, PublisherAddress, PublisherPhone, PublisherEmail FROM Publishers WHERE PublisherName = ? ";

# case 5: List all book titles

	"SELECT BookTitle FROM Books";

# case 6: List all the data for a book specified by the user.

	"SELECT BookTitle, GroupName, PublisherName, YearPublished, NumberPages
	 	FROM Books 
	 	NATURAL JOIN WritingGroups
	 	NATURAL JOIN Publishers
	 	WHERE BookTitle = ?";

# case 7: Insert a new book

	"SELECT GroupName FROM WritingGroups";
	"SELECT PublisherName FROM Publishers";
	"INSERT INTO Books(BookTitle, YearPublished, NumberPages, GroupName, PublisherName) VALUES (?,?,?,?,?)";
	"SELECT BookTitle FROM Books";

# case 8: Insert a new publisher and update all book published by one publisher to be published by the new pubisher.

	"INSERT INTO Publishers(publisherName, publisherAddress, publisherPhone, publisherEmail) VALUES (?, ?, ?, ?)";
	"SELECT PublisherName FROM Publishers";
	"UPDATE Books SET PublisherName = ? WHERE PublisherName = ?";
	"DELETE FROM Publishers WHERE PublisherName = ?";
	"SELECT * FROM Books"
	"SELECT PublisherName, PublisherAddress, PublisherPhone, PublisherEmail FROM Publishers";

# case 9: Remove a book specified by the user

	"SELECT BookTitle FROM Books";
	"DELETE FROM Books WHERE BookTitle = ?";
	"SELECT BookTitle FROM Books";

