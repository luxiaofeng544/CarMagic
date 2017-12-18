package com.lemon.event;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/9 15:10]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/9 15:10]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class StartLocationEvent {

    private boolean downloadOfflineMap = false;
    private boolean findAddress = false;

    public StartLocationEvent() {
    }

    public StartLocationEvent(boolean downloadOfflineMap) {
        this.downloadOfflineMap = downloadOfflineMap;
    }

    public boolean isFindAddress() {
        return findAddress;
    }

    public void setFindAddress(boolean findAddress) {
        this.findAddress = findAddress;
    }

    public boolean isDownloadOfflineMap() {
        return downloadOfflineMap;
    }

    public void setDownloadOfflineMap(boolean downloadOfflineMap) {
        downloadOfflineMap = downloadOfflineMap;
    }
}
