package com.bofsoft.sdk.exception;

public class BizException extends Exception {

    private static final long serialVersionUID = 1L;

    private int id = 0;

    private String name = "";

    public BizException() {
        super();
    }

    public BizException(int id) {
        super();
        this.id = id;
    }

    public BizException(int id, String name) {
        super(name);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
