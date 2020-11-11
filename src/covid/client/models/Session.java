package covid.client.models;

public class Session<T> {
    private T data;
    private boolean exist;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Session() {
    }

    public Session(T data, boolean exist) {
        this.data = data;
        this.exist = exist;
    }
}
