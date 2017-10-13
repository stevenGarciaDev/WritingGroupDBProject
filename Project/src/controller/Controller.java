package controller;



import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
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
    static final String displayFormat2="%-30s%-30s%-30s%-30s%-30s\n";
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
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Database successfully connected.");
            
            int choice = 0;
            
            System.out.println("Please choose the following options\n"
                + "1. List all Writing Group\n"
                + "2. List all Data of a Group (User's input required)\n"
                + "3. List all Publishers\n"
                + "4. List all Data of a Publisher (User's input required)\n"
                + "5. List all Book Titles (Titles Only)\n"
                + "6. List all Data of a Book (User's input required)\n"
                + "7. Insert a new Book\n"
                + "8. Insert a new Publisher (Followed by a replacing of an old Publisher)\n"
                + "9. Remove a Book\n");
            while (choice < 1 || choice > 9){
                System.out.print("Enter numbers 1 - 10: ");
                choice = in.nextInt();
            }
            
            switch(choice){
                case 1:{
                    System.out.println("Creating statement...");
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
                case 2:{
                    break;
                }
                case 3:{
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT PublisherName, PublisherAddress, "
                            + "PublisherPhone, PublisherEmail FROM Publisher";
                    ResultSet rs = stmt.executeQuery(sql);

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
                case 4:{
                    break;
                }
                case 5:{
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT GroupName, BookTitle, PublisherName, YearPublished, "
                            + "NumberPages FROM Book";
                    ResultSet rs = stmt.executeQuery(sql);

                    //STEP 5: Extract data from result set
                    System.out.printf(displayFormat2, "Group Name", "Book Title", 
                            "Publisher Name", "Year Published", "Number Pages");
                    while (rs.next()) {
                        //Retrieve by column name
                        String cGroupName = rs.getString("GroupName");
                        String cBookTitle = rs.getString("BookTitle");
                        String cPublisherName = rs.getString("PublisherName");
                        String cYearPublished = rs.getString("YearPublished");
                        String cNumberPages = rs.getString("NumberPages");

                            //Display values
                        System.out.printf(displayFormat2,
                                dispNull(cGroupName), dispNull(cBookTitle), 
                                dispNull(cPublisherName), dispNull(cYearPublished),
                                dispNull(cNumberPages));
                    }
                    break;
                }
                case 6:{
                    break;
                }
                case 7:{
                    break;
                }
                case 8:{
                    break;
                }
                default:{
                    break;
                }
            };
            
            
            //STEP 6: Clean-up environment
            
            stmt.close();
            conn.close();
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
