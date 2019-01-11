package red.clf.app.afinal.thread;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WifiThread extends Thread {
    private static final String INFO_URL="http://XXXXXX/sign/info.php";
    private String key;
    private String cid;
    private Context context;
    private Handler handler;
    private List<ScanResult> wifilist;
    private static final int RC_LOC = 100;

    public WifiThread(String cid,Context context, Handler handler){
        this.cid=cid;
        this.context=context;
        this.handler=handler;
    }

    @Override
    public void run() {
       checkWifiPermissions();
    }

    private List<ScanResult> getWifiScanResult() {
        WifiManager manager=(WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return ((WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getScanResults();
    }

    private boolean isOpenWifi(){
        WifiManager wifimanager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifimanager.isWifiEnabled();
    }

    @AfterPermissionGranted(RC_LOC)
    private void checkWifiPermissions(){
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(context, perms)) {
            if (!isOpenWifi()){
                handler.sendEmptyMessage(0);
                return;
            }
            wifilist=getWifiScanResult();
            if (wifilist==null||wifilist.size()==0){
                handler.sendEmptyMessage(-1);
                return;
            }
            HttpParams p=new HttpParams();
            p.put("detailid",cid);
            RxVolley.post(INFO_URL, p, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    JSONObject json=JSONObject.parseObject(t);
                    if (!"-1".equals(json.getString("signid"))&&!"0".equals(json.getString("signid"))){
                        key=json.getString("bssid");
                        Log.i("info",key);
                        if (key==null||key.length()==0){
                            handler.sendEmptyMessage(-1);
                            return;
                        }
                        for (ScanResult result : wifilist)
                        {
                            if (key.equals(result.BSSID)){
                                handler.sendEmptyMessage(1);
                                return;
                            }
                        }
                        handler.sendEmptyMessage(-1);
                    }
                }
            });

        } else {
            EasyPermissions.requestPermissions((Activity) context,"请授予应用定位权限",RC_LOC,perms);
        }
    }
}
