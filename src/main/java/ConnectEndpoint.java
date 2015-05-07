import javax.imageio.ImageIO;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Base64;


@ServerEndpoint(value = "/echo")
public class ConnectEndpoint {


    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText(getImage());
    }

    @OnMessage
    public String echo(String message) {
        return message + " (from your server)";
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {

    }

    String getImage(){
        try
        {
            RandomAccessFile aFile = new RandomAccessFile(
                    "/tmp/images.png","r");
            FileChannel inChannel = aFile.getChannel();
            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            //buffer.rewind();
            buffer.flip();
            inChannel.close();
            aFile.close();
            return new String(Base64.getEncoder().encode(buffer).array());
        }
        catch (IOException exc)
        {
            System.out.println(exc);
            System.exit(1);
        }

        return "";

    }

}
