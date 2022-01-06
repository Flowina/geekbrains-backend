package com.geekbrains.backend.test.miniMarket.model;

public class DeleteResponse {
    private String error;
    private boolean _ok;

    public boolean ok() {
        return _ok;
    }
    public void setOk(boolean value) {
        _ok = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DeleteResponse{" +
                "ok=" + _ok +
                ", error='" + error + '\'' +
                '}';
    }
}
