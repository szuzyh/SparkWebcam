import com.github.sarxos.webcam.Webcam;
import spark.Spark;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Leo on 2016/11/30.
 */
public class Main {
    public static void main(String args[]) throws InterruptedException {
        new InitWebcam();
        Webcam webcam= Webcam.getDefault();
     //   catchPic(webcam);
      //  WebcamStreamer s=new WebcamStreamer(554,webcam,10,true);
//
        catchPic(webcam);
//        while (true){
//            catchPic(webcam);
//            Thread.sleep(5);
//        }
      //  pushVideoToWeb(webcam);
    }

    private static void pushVideoToWeb(Webcam webcam) {
        Spark.get("/getVideo",(request, response) -> {
            webcam.open();
         // WebcamStreamer s=new WebcamStreamer(554,webcam,10,true);
//            ByteBuffer buff=webcam.getImageBytes();
//            RtspUrl rtspUrl=new RtspUrl("rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream");
//
            byte[] imageByte=new byte[1024];
            File file=new File("E:\\out\\test.bmp");
            while (true){
                BufferedImage image=webcam.getImage();
                ImageIO.write(image,"BMP",file);
                InputStream in=new FileInputStream(file);
                imageByte=new byte[in.available()];
                in.read(imageByte);
//                response.header("ref");
                response.header("Content-Type", "image/bmp");

                return imageByte;
            }




        });
    }


    private static void catchPic(Webcam webcam) {
        Spark.get("/getImg", (request, response) -> {
            webcam.open();
            BufferedImage image = webcam.getImage();
            File file = new File("E:\\bufferedImg.bmp");
            ImageIO.write(image, "BMP", file);
            InputStream in = new FileInputStream(file);
            byte[] imageByte = new byte[in.available()];
            in.read(imageByte);
            response.header("Content-Type", "image/bmp");

            //    response.header("Content-disposition","attachment;fileName=123.bmp");//自动下载
            return imageByte;
        });
    }



}



