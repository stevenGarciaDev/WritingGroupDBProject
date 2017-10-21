package controller;



import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 * and more tweaking from Brian Pham and Steven Garcia
 */
public class Controller {
    //  Database credentials
    //static String USER;
    //static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are
    //strings, but that won't always be the case.
    static final String displayFormat="%-30s%-30s%-30s%-30s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    


    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        /*
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        */
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME; // + ";user=" + USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        PreparedStatement preStmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Database successfully connected.");
            boolean end = false;
            
            Scanner reader = new Scanner(System.in);
            while(!end){
                int choice = -1;
                System.out.println("Please choose the following options\n"
                    + "0. Exit\n"
                    + "1. List all Writing Groups\n"
                    + "2. List all Data of a Group (User's input required)\n"
                    + "3. List all Publishers\n"
                    + "4. List all Data of a Publisher (User's input required)\n"
                    + "5. List all Book Titles (Titles Only)\n"
                    + "6. List all Data of a Book (User's input required)\n"
                    + "7. Insert a new Book\n"
                    + "8. Insert a new Publisher (Followed by a replacing of an old Publisher)\n"
                    + "9. Remove a Book\n");   
                while (choice < 0 || choice > 9){
                    System.out.print("Enter a number from 0 - 9: ");

                    try {
                        choice = reader.nextInt();
                        reader.nextLine();
                    } catch (Exception ex) {
                        System.out.println("(Please enter an integer value)");
                        reader.nextLine();
                    }

                }

                switch(choice){
                    //List all writing groups
                    case 0:{
                        end = true;
                        break;
                    }
                    case 1:{
                        System.out.println("Creating statement...\n");
                        stmt = conn.createStatement();
                        String sql;
                        sql = "SELECT GroupName, Headwriter, YearFormed, Subject FROM WritingGroup";
                        ResultSet rs = stmt.executeQuery(sql);

                        //STEP 5: Extract data from result set
                        System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cGroupName = rs.getString("GroupName");
                            String cHeadWriter = rs.getString("Headwriter");
                            String cYearFormed = rs.getString("YearFormed");
                            String cSubject = rs.getString("Subject");

                            //Display values
                            System.out.printf(displayFormat,
                                    dispNull(cGroupName), dispNull(cHeadWriter), 
                                    dispNull(cYearFormed), dispNull(cSubject));
                        }
                        break;
                    }
                    //list all data of a group (User's input required)
                    case 2:{
                        System.out.println("Please enter a group name you want shown: ");
                        String gn = reader.nextLine();

                        System.out.println("Creating statement...\n");

                        String sql;
                        sql = "SELECT GroupName, Headwriter, YearFormed, Subject FROM WritingGroup";
                        sql += " WHERE GroupName = ?";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, gn);
                        ResultSet rs = preStmt.executeQuery();

                        //STEP 5: Extract data from result set
                        System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cGroupName = rs.getString("GroupName");
                            String cHeadWriter = rs.getString("Headwriter");
                            String cYearFormed = rs.getString("YearFormed");
                            String cSubject = rs.getString("Subject");

                                //Display values
                            System.out.printf(displayFormat,
                                    dispNull(cGroupName), dispNull(cHeadWriter), 
                                    dispNull(cYearFormed), dispNull(cSubject));
                        }
                        break;
                    }
                    //List all publishers
                    case 3:{
                        System.out.println("Creating statement...\n");
                        stmt = conn.createStatement();
                        String sql;
                        sql = "SELECT PublisherName, PublisherAddress, "
                                + "PublisherPhone, PublisherEmail FROM Publisher";
                        ResultSet rs = stmt.executeQuery(sql);

                        // display format changed to ensure alignment for columns
                        String publisherDisplayFormat = displayFormat;
                        publisherDisplayFormat = publisherDisplayFormat.replaceAll("30", "35");

                        //STEP 5: Extract data from result set
                        System.out.printf(publisherDisplayFormat, "Publisher Name", "Publisher Address", 
                                    "Publisher Phone", "Publisher Email");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cPublisherName = rs.getString("PublisherName");
                            String cPublisherAddress = rs.getString("PublisherAddress");
                            String cPublisherPhone = rs.getString("PublisherPhone");
                            String cPublisherEmail = rs.getString("PublisherEmail");

                                //Display values
                            System.out.printf(publisherDisplayFormat,
                                    dispNull(cPublisherName), dispNull(cPublisherAddress), 
                                    dispNull(cPublisherPhone), dispNull(cPublisherEmail));
                        }
                        break;
                    }
                    //List all data of a Publisher (user's input required)
                    case 4:{
                        System.out.println("Please enter a publisher name you want shown: ");
                        String publisher = reader.nextLine();

                        System.out.println("Creating statement...\n");

                        String sql;
                        sql = "SELECT PublisherName, PublisherAddress, "
                                + "PublisherPhone, PublisherEmail FROM Publisher";
                        sql += " WHERE PublisherName = ?";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, publisher);
                        ResultSet rs = preStmt.executeQuery();

                        //STEP 5: Extract data from result set
                        System.out.printf(displayFormat, "Publisher Name", "Publisher Address", 
                                    "Publisher Phone", "Publisher Email");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cPublisherName = rs.getString("PublisherName");
                            String cPublisherAddress = rs.getString("PublisherAddress");
                            String cPublisherPhone = rs.getString("PublisherPhone");
                            String cPublisherEmail = rs.getString("PublisherEmail");

                                //Display values
                            System.out.printf(displayFormat,
                                    dispNull(cPublisherName), dispNull(cPublisherAddress), 
                                    dispNull(cPublisherPhone), dispNull(cPublisherEmail));
                        }
                        break;
                    }
                    //List all book titles (Titles Only)
                    case 5:{
                        System.out.println("Creating statement...\n");
                        stmt = conn.createStatement();
                        String sql;
                        sql = "SELECT BookTitle FROM Book";
                        ResultSet rs = stmt.executeQuery(sql);

                        //STEP 5: Extract data from result set
                        System.out.println("Book Title");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cBookTitle = rs.getString("BookTitle");

                                //Display values
                            System.out.println(dispNull(cBookTitle));
                        }
                        break;
                    }
                    //List all data of a book
                    case 6:{
                        System.out.print("Enter a book title: ");
                        String bookTitle = reader.nextLine();

                        System.out.println("Creating statement...\n");

                        String sql;
                        sql = "SELECT BookTitle, GroupName, PublisherName, YearPublished, NumberPages "
                                + "FROM Book "
                                + "NATURAL JOIN WritingGroup "
                                + "NATURAL JOIN Publisher "
                                + "WHERE BookTitle = ?";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, bookTitle);
                        ResultSet rs = preStmt.executeQuery();

                        //STEP 5: Extract data from result set
                        String dsplyFrmt = "%-30s%-30s%-30s%-30s%-30s\n";
                        System.out.printf(dsplyFrmt, "Book Title", "Group Name", "Publisher Name", "Year Published", "Number of Pages");
                        while (rs.next()){
                            String bBookTitle = rs.getString("BookTitle");
                            String bGroupName = rs.getString("GroupName");
                            String bPublisherName = rs.getString("PublisherName");
                            String bYearPublished = rs.getString("YearPublished");
                            String bNumberOfPages = rs.getString("NumberPages");

                            System.out.printf(dsplyFrmt, dispNull(bBookTitle), dispNull(bGroupName), dispNull(bPublisherName), 
                                    dispNull(bYearPublished), dispNull(bNumberOfPages));
                        }
                        break;
                    }
                    //Insert a new Book
                    case 7:{
                        System.out.print("Please input the new Book Title: ");
                        String bookTitle = reader.nextLine();
                        System.out.print("Please input the new Book year of publish: ");
                        String yearPublished = reader.nextLine();
                        System.out.print("Please input the new Book number of pages: ");
                        int numPages = reader.nextInt();

                        //Making the UI List for user to specify
                        stmt = conn.createStatement();
                        ArrayList<String> gName = new ArrayList<String>();
                        String sqlGN = "SELECT GroupName FROM WritingGroup";
                        ResultSet rsGN = stmt.executeQuery(sqlGN);
                        int gnNumList = 1;
                        System.out.println("Group Name List:");
                        while(rsGN.next()){
                            System.out.println(gnNumList + ") " + dispNull(rsGN.getString("GroupName")));
                            gName.add(rsGN.getString("GroupName"));
                            gnNumList++;
                        }


                        System.out.print("Please input the number corresponding to the new Book Group Name from above: ");
                        int gChoiceName = reader.nextInt();
                        while(gChoiceName < 1 || gChoiceName > gName.size()){
                            System.out.print("Please enter a valid number from the list above: ");
                            gChoiceName = reader.nextInt();
                        }
                        String groupName = gName.get(gChoiceName - 1);


                        //Making the List for Publisher Name
                        ArrayList<String> pName = new ArrayList<String>();
                        String sqlPN = "SELECT PublisherName FROM Publisher";
                        ResultSet rsPN = stmt.executeQuery(sqlPN);
                        int pnNumList = 1;
                        System.out.println("Publisher Name List:");
                        while(rsPN.next()){
                            System.out.println(pnNumList + ") " + dispNull(rsPN.getString("PublisherName")));
                            pName.add(rsPN.getString("PublisherName"));
                            pnNumList++;
                        }

                        System.out.print("Please input the number corresponding to the new Book Publisher Name from above: ");
                        int pChoiceName = reader.nextInt();
                        while(pChoiceName < 1 || pChoiceName > pName.size()){
                            System.out.print("Please enter a valid number from the list above: ");
                            pChoiceName = reader.nextInt();
                        }
                        String publisherName = pName.get(pChoiceName - 1);



                        System.out.println("Creating statement...");
                        System.out.println("Adding to database...\n");
                        String sql = "INSERT INTO Book(BookTitle, YearPublished, NumberPages, GroupName, PublisherName) VALUES "
                                + "(?,?,?,?,?)";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, bookTitle);
                        preStmt.setString(2, yearPublished);
                        preStmt.setString(3, Integer.toString(numPages));
                        preStmt.setString(4, groupName);
                        preStmt.setString(5, publisherName);                    
                        preStmt.executeUpdate();

                        System.out.println("Successful add.");
                        System.out.println("Added: [" + bookTitle + ", " +
                                        yearPublished + ", " + 
                                        numPages + ", " + 
                                        groupName + ", " + 
                                        publisherName + "]");

                        System.out.println("Here are the books currently:\n");
                        sql = "SELECT BookTitle FROM Book";
                        ResultSet rs = stmt.executeQuery(sql);

                        //STEP 5: Extract data from result set
                        System.out.println("Book Title");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cBookTitle = rs.getString("BookTitle");

                                //Display values
                            System.out.println(dispNull(cBookTitle));
                        }
                        break;
                    }
                    //Insert a new Publisher (followed by a replacing of an old Publisher)
                    case 8:{

                        break;
                    }
                    //remove a book
                    default:{
                        System.out.println("Here are the books currently:\n");
                        stmt = conn.createStatement();
                        ArrayList<String> bookName = new ArrayList<String>();
                        String sqlBook = "SELECT BookTitle FROM Book";
                        ResultSet rs = stmt.executeQuery(sqlBook);
                        int bookNumList = 1;
                        System.out.println("Book Title List:");
                        while(rs.next()){
                            System.out.println(bookNumList + ") " + dispNull(rs.getString("BookTitle")));
                            bookName.add(rs.getString("BookTitle"));
                            bookNumList++;
                        }

                        System.out.print("\n\nPlease input the Book Title to remove: ");
                        int bookChoice = reader.nextInt();
                        while(bookChoice < 1 || bookChoice > bookNumList - 1){
                            System.out.print("Please enter a valid book number");
                            bookChoice = reader.nextInt();
                        }
                        String bookTitle = bookName.get(bookChoice - 1);
                        String sql = "DELETE FROM Book WHERE BookTitle = ?";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, bookTitle);
                        preStmt.executeUpdate();

                        System.out.println("Here are the books currently after the delete:\n");
                        sqlBook = "SELECT BookTitle FROM Book";
                        rs = stmt.executeQuery(sqlBook);

                        //STEP 5: Extract data from result set
                        System.out.println("Book Title");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cBookTitle = rs.getString("BookTitle");

                                //Display values
                            System.out.println(dispNull(cBookTitle));
                        }
                        break;
                    }
                };
                
                System.out.println("\n\n\n");
            }
            
            //STEP 6: Clean-up environment
            
            //stmt.close();
            //conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}