import com.github.sarxos.webcam.Webcam;
import uk.co.caprica.vlcj.medialist.MediaListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/11/28.
 */
public class InitWebcam {
    public InitWebcam() {
        String name1 = "ace";
        //String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
        List<MediaListItem> mediaListItemList=new ArrayList<MediaListItem>();
        mediaListItemList.add(new MediaListItem(name1,rtsp1,new ArrayList<MediaListItem>()));
        Webcam.setDriver(new VlcjDriver(mediaListItemList));
    }
}
