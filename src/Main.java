import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        User user = Input.getInformation();
        BoardingPass boardingPass = new BoardingPass(user);
        WriteToFile.saveRecord(boardingPass,user);
        Formatting.format();
    }
}
