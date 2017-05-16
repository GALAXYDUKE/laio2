package com.bofsoft.sdk.widget.listview.sortlistview;

public class SortModel<T> {

    private String key;
    private String CNKey;
    private String ENKey;
    private T data;
    private boolean checked = false;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCNKey() {
        return CNKey;
    }

    public void setCNKey(String cNKey) {
        CNKey = cNKey;
    }

    public String getENKey() {
        return ENKey;
    }

    public void setENKey(String eNKey) {
        ENKey = eNKey;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
