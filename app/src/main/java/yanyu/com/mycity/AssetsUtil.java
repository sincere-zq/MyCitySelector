package yanyu.com.mycity;

import android.content.Context;
import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class AssetsUtil {
    public static List<Province> getProvince(Context context, String fileName) {
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            //获取输入流的长度
            int length = is.available();
            byte[] bytes = new byte[length];
            //把输入流写入到字节数组中
            is.read(bytes);
            String result = new String(bytes, "gbk");//把字节数组转换成utf-8格式的字符串
            List<Province> provinces = JSON.parseArray(result, Province.class);
            return provinces;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Province> parse(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            String configString = baos.toString();
            JSONArray jsonArray = JSON.parseArray(configString);
//            JSONObject jsonObject = JSONObject.parseObject(configString);
            List<Province> provinces = JSON.parseArray(jsonArray.toString(), Province.class);
            baos.close();
            is.close();
            return provinces;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
