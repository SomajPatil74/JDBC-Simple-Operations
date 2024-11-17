import java.sql.*;
import java.util.Scanner;

public class Main {

//    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String url = "jdbc:mysql://localhost:3306/bank";
    private static final String username = "root";
    private static final String password = "Som@j5803d";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }

        // To fetch data and print on console :
        /*
        try {
            // executeQuery() method is use for the retrieve data, and when you want to perform insert, update, delete operations you have to use executeUpdate() method.
            Connection connection = DriverManager.getConnection(url, username, password);
            // Statement interface is used to execute the sql queries with the help of createStatement() method from Connection Interface.
            Statement statement = connection.createStatement();
            String query = "select * from students";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double marks = resultSet.getDouble("marks");
                System.out.println("ID : " + id);
                System.out.println("Name : " + name);
                System.out.println("Age : " + age);
                System.out.println("Marks : " + marks);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
         */


        // To perform CRUD operations using statement interface :
        /*
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            // Statement interface is used to execute the sql queries with the help of createStatement() method from Connection Interface.
            Statement statement = connection.createStatement();
            String query = String.format("insert into students(name, age, marks) values('%s', %o, %f)", "Sahil", 22, 78.45);
//            String query = String.format("update students set marks = %f where id = %d", 89.56, 5);
//            String query = String.format("delete from students where id = 2");

            int rowsAffected = statement.executeUpdate(query);
            if(rowsAffected > 0){
                System.out.println("Data inserted successfully!!!");
//                System.out.println("Data Updated successfully!!!");
//                System.out.println("Data Deleted successfully!!!");
            }
            else {
                System.out.println("Data not inserted");
//                System.out.println("Data not updated");
//                System.out.println("Data not deleted");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
         */


        // Perform CRUD operations using Prepared statement :
        /*
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

//            String query = "insert into students(name, age, marks) values(?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, "Vikas");
//            preparedStatement.setInt(2, 25);
//            preparedStatement.setDouble(3, 35.45);
//            int rowsAffected = preparedStatement.executeUpdate();
//            if(rowsAffected > 0){
//                System.out.println("Data inserted successfully!!!");
//            }
//            else {
//                System.out.println("Data not inserted");
//            }

//            String query = "select * from students where id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, 4);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                int age = resultSet.getInt("age");
//                double marks = resultSet.getDouble("marks");
//                System.out.println("ID : " + id);
//                System.out.println("Name : " + name);
//                System.out.println("Age : " + age);
//                System.out.println("Marks : " + marks);
//            }
//            else{
//                System.out.println("Data not found!!!");
//            }

//            String query = "update students set marks= ? where id= ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setDouble(1, 80.51);
//            preparedStatement.setInt(2, 7);
//            int rowsAffected = preparedStatement.executeUpdate();
//            if(rowsAffected > 0){
//                System.out.println("Data updated successfully!!!");
//            }
//            else {
//                System.out.println("Data not updated");
//            }

            String query = "delete from students where id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 9);
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Data deleted successfully!!!");
            }
            else {
                System.out.println("Data not deleted");
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        */


        // Batch Processing :
        /*
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "insert into students(name, age, marks) values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("Enter name : ");
                String name = scanner.next();
                System.out.print("Enter age : ");
                int age = scanner.nextInt();
                System.out.print("Enter marks : ");
                double marks = scanner.nextDouble();
                System.out.print("Enter more data(Y/N) : ");
                String choice = scanner.next();

                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setDouble(3, marks);
                preparedStatement.addBatch();

//                String query = String.format("insert into students(name, age, marks) values('%s', %o, %f)", name, age, marks);
//                statement.addBatch(query);

                if(choice.toUpperCase().equals("N")){
                    break;
                }
            }
//          int[] arr = statement.executeBatch();
            int[] arr = preparedStatement.executeBatch();

            for(int i=0; i<arr.length;i++){
                if(arr[i] == 0){
                    System.out.println("Query : " + i + "not executed successfully!!!");
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
         */


        // commit() and rollback() :
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String debit_query = "update accounts set balance = balance - ? where account_no = ?";
            String credit_query = "update accounts set balance = balance + ? where account_no = ?";

            PreparedStatement debitpreparedStatement = connection.prepareStatement(debit_query);
            PreparedStatement creditpreparedStatement = connection.prepareStatement(credit_query);
            Scanner sc= new Scanner(System.in);
            System.out.print("Enter account number : ");
            int account_no = sc.nextInt();
            System.out.print("Enter amount : ");
            double amount = sc.nextDouble();

            debitpreparedStatement.setDouble(1, amount);
            debitpreparedStatement.setInt(2, account_no);

            creditpreparedStatement.setDouble(1, amount);
            creditpreparedStatement.setInt(2, 102);

            int affectedRows1 = debitpreparedStatement.executeUpdate();
            int affectedRows2 = creditpreparedStatement.executeUpdate();

            if(isSufficient(connection, account_no, amount)){
                connection.commit();
                System.out.println("Transaction Success.");
            } else{
                connection.rollback();
                System.out.println("Transaction failed!!!");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    static boolean isSufficient(Connection connection, int account_no, double amount){
        try {
            String query = "select balance from accounts where account_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account_no);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double current_balance = resultSet.getDouble("balance");
                if(amount > current_balance){
                    return false;
                }
                else {
                    return true;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    return true;
    }
}