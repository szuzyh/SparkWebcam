import java.sql.*;

/**
 * Created by Leo on 2016/11/15.
 */
public class DbAccess {
    String driver="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/Work";
    String user="root";
    String password="zyh441424";
    Connection connection=null;
    Statement statement=null;

    public void init(){
        try{
            Class.forName(driver);
            connection= DriverManager.getConnection(url,user,password);
            statement=connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String id,String name   //插入数据
            ,String sex,String nation,String birthday
            ,String address,String personId,String department,String startDate,String endDate
            ,String imgDate) throws SQLException {

        String str="insert into usermessage(id,name,sex,nation,birthday,address,personId,department,startDate,endDate,imgDate) values('"+id+"','"+name+"','"+sex+"','"+nation+"','"+birthday+"','"+address+"','"+personId+"','"+department+"','"+startDate+"','"+endDate+"','"+imgDate+"')";
        statement.execute(str);
    }
    //通过身份证查询讯息
    public String[] query1(String id) throws SQLException {
        String[] text={"name","sex","nation","birthday","address",
                "department","personId","startDate","endDate","imgDate"};
        String[] message = new String[10];

        for (int i=0;i<text.length;i++){
            String strName="select "+text[i]+" from usermessage where id='"+id+"'";
            ResultSet resultSet=statement.executeQuery(strName);
            resultSet.next();
            String msg=resultSet.getString(text[i]);
            message[i]=msg;
        }
        return message;
    }
    public void submint() throws SQLException {
        statement.close();
        connection.close();
    }
    //通过id删除身份证信息
    public void delete(String id) throws SQLException {
        String str="delete from usermessage where id='"+id+"'";
        statement.execute(str);
    }
    public  void  deleteWithID(String ID) throws SQLException {
        String str="delete from usermessage where personId='"+ID+"'";
        statement.execute(str);
    }
}
