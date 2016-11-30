import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import spark.Spark;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by Leo on 2016/11/11.
 */

public class Test {

    private static ActiveXComponent com = new ActiveXComponent("CLSID:0F55CC69-97EF-42A9-B63D-D1831CB2B3B9");
    private static Dispatch disp = (Dispatch) com.getObject();
    static final Random random = new Random();
    private static int splid;
    private static String srcPathName;
    public String base64 = null;
    private static byte[] b = null;

    public static void main(String[] args) throws SQLException, InterruptedException {

        int id1 = random.nextInt(Integer.MAX_VALUE);
        splid = id1;
        srcPathName = id1 + "";
//         init();
//        com = new ActiveXComponent("CLSID:0F55CC69-97EF-42A9-B63D-D1831CB2B3B9");
//        disp = (Dispatch)com.getObject();
//     while (true) {
//         String srcPathName =Dispatch.call(disp,"name").getString();
//         int ret = Dispatch.call(disp, "getCardInfo", new Variant("E:\\Img\\" + srcPathName + ".bmp")).getInt();
//    if (ret != 0) {

//            System.out.print(Dispatch.call(disp, "Sex").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Nation").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Birthday").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Address").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "ID").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Department").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "StartDate").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "EndDate").getString()+"\n");
//        System.out.println("打开设备失败！");
//    }

//          用户请求返回图片信息
    //       getMessageForImg();
        while (true){
              saveImage();
               Thread.sleep(5000);
          }


//          获取各项单项信息
//        getMessage("Name");
//        getMessage("Sex");
//        getMessage("Nation");
//        getMessage("Birthday");
//        getMessage("Address");
//        getMessage("ID");
//        getMessage("Department");
//         getMessage("StartDate");
//        getMessage("EndDate");

//        getImgDate();  //需要id
//        上传信息到数据库
        //   uploadMessageToSql();
//           删除某个id下的身份证信息
//          deleteMessage("265287990");

//        删除某个身份证号下的所有信息
//        deleteMessageWithPersonId("441424199508272258");

//        获取全部信息并打印成表格xml
//        不需要身份证号
//        getAllMessageWithoutID();
//        getMessageIn("595952606");


//        图片转换
//        ImgToString("E:\\Img\\head.bmp");
//        StringToImg(ImgToString("E:\\Img\\head.bmp"),"E:\\Img\\boom.bmp");
//        网页上传
        //postMessage();
//        getAllMessageWithID();
//          }
    }


    private static void saveImage() {
        init();
    }

    private static void getImgDate() {
        Spark.get("/message/Img/:id", (request, response) -> {

            DbAccess dba = new DbAccess();
            dba.init();
            String[] msg = dba.query1(request.params(":id"));
            String name = msg[0];
            System.out.println(name);
            String personID = msg[5];
            System.out.println(personID);
            String imgDate = msg[9];
            byte[] buff = null;
            InputStream in = new FileInputStream(imgDate);
            buff = new byte[in.available()];
            in.read(buff);
            response.header("Content-Type", "image/bmp");
            response.header("Content-disposition", "attachment;fileName=" + URLEncoder.encode(name, "UTF-8") + "-" + personID + ".bmp");
            return buff;
        });
    }


    public static void getMessageForImg() {
        Spark.get("/message/Img", (request, response) -> {
            init();
            String name = Dispatch.call(disp, "name").getString().trim();
            String personID = Dispatch.call(disp, "ID").getString().trim();
//            String path = "E:\\Img\\" + srcPathName + ".bmp";
            String path = "E:\\Img\\849090852.bmp";
            byte[] buff = null;
            InputStream in = new FileInputStream(path);
            buff = new byte[in.available()];
            in.read(buff);

//            response.header("Content-Type", "image/bmp");
            //   response.header("Content-disposition","attachment;fileName="+"aqi.bmp");
            response.header("Content-disposition","attachment;fileName="+srcPathName+".bmp");//自动下载
            // response.header("Content-disposition","attachment;fileName="+ URLEncoder.encode(name, "UTF-8")+"-"+personID+".bmp");

            return buff;

        });

    }


    private static void init() {

        int ret = Dispatch.call(disp, "getCardInfo", new Variant("E:\\Img\\" + srcPathName + ".bmp")).getInt();
        if (ret != 0) {
            System.out.println("打开设备失败");
        } else {
            System.out.println("打开设备成功");
        }
    }

    private static void deleteMessageWithPersonId(String personId) throws SQLException {
        DbAccess dba = new DbAccess();
        dba.init();
        dba.deleteWithID(personId);
    }

    private static void deleteMessage(String id) throws SQLException {
        DbAccess dba = new DbAccess();
        dba.init();
        dba.delete(id);
    }

    private static String getMessageIn(String id) throws SQLException {
        DbAccess dba = new DbAccess();
        dba.init();
        String[] msg = dba.query1(id);
        String name = msg[0];
        String sex = msg[1];
        String nation = msg[2];
        String birthday = msg[3];
        String address = msg[4];
        String department = msg[5];
        String personId = msg[6];
        String startDate = msg[7];
        String endDate = msg[8];
        String imgDate = msg[9];
//
        System.out.println(imgDate);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"
                + "姓名：" + name + "<br/>"
                + "性别：" + sex + "<br/>"
                + "民族：" + nation + "<br/>"
                + "出生日期：" + birthday + "<br/>"
                + "住址：" + address + "<br/>"
                + "身份证号：" + personId + "<br/>"
                + "签发机关：" + department + "<br/>"
                + "发证日期：" + startDate + "<br/>"
                + "有效期：" + endDate + "<br/>"
                + "图片路径：" + imgDate + "<br/>"
                + "</news>";
    }

    //网页上传信息
    private static void postMessage() {
        Spark.post("/message/user", (request, response) -> {
            uploadMessageToSql();
            return true;
        });
    }


    //上传信息
    private static void uploadMessageToSql() throws SQLException {
        init();

        String id = splid + "";
        String name = Dispatch.call(disp, "Name").getString().trim();
        String sex = Dispatch.call(disp, "Sex").getString().trim();
        String nation = Dispatch.call(disp, "Nation").getString().trim();
        String birthday = Dispatch.call(disp, "Birthday").getString().trim();
        String address = Dispatch.call(disp, "Address").getString().trim();
        String ID = Dispatch.call(disp, "ID").getString().trim();
        String department = Dispatch.call(disp, "Department").getString().trim();
        String startDate = Dispatch.call(disp, "StartDate").getString().trim();
        String endDate = Dispatch.call(disp, "EndDate").getString().trim();

        String imgPath = "E://" + "Img//" + srcPathName + ".bmp";
        DbAccess dba = new DbAccess();
        dba.init();
        dba.insert(id, name, sex, nation, birthday, address, ID, department, startDate, endDate, imgPath);
    }

    //字符串转化为图片并保存
    private static boolean StringToImg(String base64String, String createPath) {
        if (base64String == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(base64String);
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(createPath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //图片转化为字符串并加密保存？？不加密如何
    private static String ImgToString(String path) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data));
        return encoder.encode(data);

    }

    //在此之前是不是应该要有个上传信息的方法？？
    private static void getAllMessageWithID() {
        Spark.get("/message/user/:id", (request, response) -> {
            DbAccess dba = new DbAccess();
            dba.init();
            String[] msg = dba.query1(request.params(":id"));
            String name = msg[0];
            String sex = msg[1];
            String nation = msg[2];
            String birthday = msg[3];
            String address = msg[4];
            String department = msg[5];
            String personId = msg[6];
            String startDate = msg[7];
            String endDate = msg[8];
            String imgDate = msg[9];
//            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"
//                    +"姓名："+name+"<br/>"
//                    +"性别："+sex+"<br/>"
//                    +"民族："+nation+"<br/>"
//                    +"出生日期："+birthday+"<br/>"
//                    +"住址："+address+"<br/>"
//                    +"身份证号："+personId+"<br/>"
//                    +"签发机关："+department+"<br/>"
//                    +"发证日期："+startDate+"<br/>"
//                    +"有效期："+endDate+"<br/>"
//                    +"</news>";
            response.header("Content-Type", "application/json");
            String callback = "{\"UserID\":\"" + request.params(":id") + "\",\"Message\":{\"name\":\"" + name + "\"" +
                    ",\"sex\":\"" + sex + "\",\"natiom\":\"" + nation + "\",\"birthday\":\"" + birthday + "\"" +
                    ",\"address\":\"" + address + "\",\"personId\":\"" + personId + "\",\"department\":\"" + department + "\"" +
                    ",\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate + "\",\"imgDate\":\"" + imgDate + "\"}}";
            return callback;
        });
    }

    private static void getAllMessageWithoutID() {

        Spark.get("/message/user", (request, response) -> {
            init();
            String name = Dispatch.call(disp, "Name").getString().trim();
            String sex = Dispatch.call(disp, "Sex").getString().trim();
            String nation = Dispatch.call(disp, "Nation").getString().trim();
            String birthday = Dispatch.call(disp, "Birthday").getString().trim();
            String address = Dispatch.call(disp, "Address").getString().trim();
            String personId = Dispatch.call(disp, "ID").getString().trim();
            String department = Dispatch.call(disp, "Department").getString().trim();
            String startDate = Dispatch.call(disp, "StartDate").getString().trim();
            String endDate = Dispatch.call(disp, "EndDate").getString().trim();
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"
                    + "姓名：" + name + "<br/>"
                    + "性别：" + sex + "<br/>"
                    + "民族：" + nation + "<br/>"
                    + "出生日期：" + birthday + "<br/>"
                    + "住址：" + address + "<br/>"
                    + "身份证号：" + personId + "<br/>"
                    + "签发机关：" + department + "<br/>"
                    + "发证日期：" + startDate + "<br/>"
                    + "有效期：" + endDate + "<br/>"
                    + "</news>";
        });
    }

    private static void getMessage(String s) {
        Spark.get("/message/" + s, (request, response) -> {
            init();
            return Dispatch.call(disp, s).getString().trim();
        });
    }


}