package tank;




import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
/**
 * @Auther:ZhenCF
 * @Date: 2022-01-26-4:07
 * @Description: tank
 * @version: 1.0
 */
public class Audio{
    byte[] bytes = new byte[1024*1024*15];
    private AudioFormat audioFormat=null;
    private SourceDataLine sourceDataLine=null;
    private DataLine.Info dataLine_info=null;
    private AudioInputStream audioInputStream=null;
    //循环播放
    public void loop(){
        try{
            while (true){
                int len=0;
                //打开指定格式的缓冲区 使缓冲区获取任何所需的系统资源
                sourceDataLine.open(audioFormat,1024*1024*15);
                //开始运行
                sourceDataLine.start();
                //输出音频流状态
                System.out.println(audioInputStream.markSupported());
                audioInputStream.mark(12358946);
                //循环输出缓冲区数据
                while ((len=audioInputStream.read(bytes))>0){
                    sourceDataLine.write(bytes,0,len);
                }
                audioInputStream.reset();
                //请空缓冲区数据
                sourceDataLine.drain();
                //关闭缓冲区
                sourceDataLine.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Audio(String fileName){
        try {
            audioInputStream=AudioSystem.getAudioInputStream(Audio.class.getClassLoader().getResource(fileName));
            audioFormat=audioInputStream.getFormat();
            dataLine_info=new DataLine.Info(SourceDataLine.class,audioFormat);
            sourceDataLine=(SourceDataLine)AudioSystem.getLine(dataLine_info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play(){
        byte[] bt=new byte[1024*5];
        int len=0;
        try {
            //打开指定格式的缓冲区 使缓冲区获取任何所需的系统资源
            sourceDataLine.open(audioFormat, 1024*5);
            //开始运行
            sourceDataLine.start();
            System.out.println(audioInputStream.markSupported());
            //循环读取audioInputStream的中的数据到 bt字节数组
            while ((len=audioInputStream.read(bt))>0){
                //将音频数据输出
                sourceDataLine.write(bt,0,len);
            }
            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            audioInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[]args){
        Audio audio = new Audio("audio/薛之谦 - 演员.mp3");
        audio.loop();
    }
}
