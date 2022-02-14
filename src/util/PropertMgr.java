package util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-26-9:40
 * @Description: util
 * @version: 1.0
 */

/**
 * 配置文件中 的属性管理
 */
public class PropertMgr {
    private static PropertMgr propertMgr=null;
    private static Properties props=new Properties();
    static {
        try {
            props.load(PropertMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static PropertMgr getPropertMgr(){
        if(propertMgr==null){
            propertMgr=new PropertMgr();
        }
        return propertMgr;
    }



    public static String getString(String key){
        if(props==null)return null;
        return (String) props.get(key);
    }
    public static int getInt(String key){
        if(props==null)return -1;
        return Integer.parseInt((String)props.get(key));
    }

    public static void main(String[] args) {
        System.out.println(PropertMgr.getString("initialization"));
    }
}
