package passwordmanager.data;

public class Password {
    private String source;
    private String username;
    private String value;

    public Password(){
        this.source = "";
        this.username = "";
        this.value = "";
    }

    public Password(String source, String username, String value){
        this.source = source;
        this.username = username;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
