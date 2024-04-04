import org.postgresql.Driver;

import java.sql.*;


public class DemoJDBC {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {



        /*
        * import the packgage -> import java.sql.*;
        * load and register (optional) -> Class.forName("org.postgresql.Driver");
        * create connection
        * create statement
        * execute statement
        * process the resultys
        * close
        *
        * */

        String url = "jdbc:postgresql://localhost:5432/demo";
        String uName = "postgres";
        String pass ="0000";

       // Class.forName("org.postgresql.Driver"); // loading driver step OPTONAL CAUSE WE DONT NEED TO DO İT ANYMORE THİS İS DONE AUTOMATİCALLY

        Connection con = DriverManager.getConnection(url,uName,pass);

        System.out.println("Connection established.");

        // FETCHING

        Statement st = con.createStatement();

        String sql = "select sname from student where sid = 2;";
        // to fetch data we use  executeQuery(sql) method, to manipulate data we can use execute()
        ResultSet resultSet = st.executeQuery(sql);
        //System.out.println(resultSet.next());
        resultSet.next(); // we need to do this cause the pointer points before the actual data
        String name = resultSet.getString("sname");
        System.out.println("name of the student is " + name);


       // con.close();
       // System.out.println("Connection closed.");

        // FETCHING ALL ROWS

        String sql2 = "select * from student";
        ResultSet  resultSet2 = st.executeQuery(sql2);

        while (resultSet2.next()){
            System.out.print(resultSet2.getInt(1)+ " - ");
            System.out.print(resultSet2.getString(2)+ " - ");
            System.out.print(resultSet2.getInt(3));
            System.out.println();

        }

        // Crud operations
        // create - read - update - delete
        // insert
    //  String sql3 = "insert into student values (5, 'John',33)";
    //  boolean status = st.execute(sql3); // if it is select query: it returns count; it returns false if you are passing insert update or delete query, but it inserts nothing is wrong with that
    //
    //  System.out.println(status);

        //UPDATE
        String sql4 = "update student set sname= 'Max' where sid=5";
        boolean status2 = st.execute(sql4); // if it is select query: it returns count; it returns false if you are passing insert update or delete query, but it inserts nothing is wrong with that

        System.out.println(status2);

        // DELETE
        String sql5 = "delete from student where sid=5";
        st.execute(sql5);

        //PREPARED STATEMENT : what if we want to get entries from the user
        // improve the performance
        // same query shouldnt execute many times
        // so we cache the query
        // we also need to solve the double quota syntax its confusing
        // we also need to solve entrying/updating unintended data
        // also be careful about sql injection

        int sid = 101;
        String sname = "max";
        int marks = 48;

     //   String sql6  = "insert into student values ("  + sid + ",'" + sname + "'," +marks + ")";
        String sql6  = "insert into student values (?,?,?)";

        // if we want to anything updating, structre, dropping table -> statement
        // otherwise select (where) insert  ->  use prepared stateöemt

        PreparedStatement preparedStatement = con.prepareStatement(sql6);
        preparedStatement.setInt(1,sid);
        preparedStatement.setString(2,sname);
        preparedStatement.setInt(3,marks);

        preparedStatement.execute();
    }
}
