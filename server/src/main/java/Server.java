import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static DataInputStream inS;
    private static DataOutputStream outS;
    private static FileOutputStream fOutS;
    private static Socket socket;

    private static final String path = "server/src/main/resources/client_dir/";

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(8189)) {
            socket = server.accept();
            inS = new DataInputStream(socket.getInputStream());
            while (true){
                String fileName = inS.readUTF();
                if (fileName.equals("./getFilesList")) {
                    giveList();
                } else {
                    uploadFile(fileName);
                Thread.sleep(1000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                inS.close();
                fOutS.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void uploadFile(String fileName) throws IOException {
        File file = new File(path + fileName);
        fOutS = new FileOutputStream(file);
        long fileLength = inS.readLong();
        byte[] buffer = new byte[8192];
        for (int i = 0; i < (fileLength + 8191) / 8192; i++) {
            int cnt = inS.read(buffer);
            fOutS.write(buffer, 0, cnt);
        }
    }

    private static void giveList() throws IOException {
        outS = new DataOutputStream(socket.getOutputStream());
            File dir = new File(path);
            String [] files = dir.list();
            if (files != null) {
                outS.writeInt(files.length);
                for (String file : files) {
                    outS.writeUTF(file);
                }
            } else {
                outS.writeInt(0);
            }
            outS.flush();
    }
}
