
import java.util.LinkedList;
import java.util.List;

public class Contact {
    String name;
    String phoneNumber;
    List<String> callLog;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callLog = new LinkedList<>();
    }

    public void addToCallLog(String callDetails) {
        callLog.add(0, callDetails);  
    }

    public List<String> getCallLog() {
        return callLog;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber;
    }
}
