package com.lemon.carmonitor.old.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Button;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.carmonitor.R;

import java.io.File;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 21:02]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 21:02]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id= R.layout.activity_weixin)
public class WeixinActivity extends LemonActivity {

    @FieldView(id= R.id.btnLogin)
    public Button btnLogin;

    @OnClick(id= R.id.btnLogin)
    public void shareClick() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(Environment.getExternalStorageDirectory(), "car_qrcode.png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "客服二维码"));
    }

}
