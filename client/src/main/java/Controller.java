import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Controller implements Initializable{
    public ListView<String> clientListView;
    public ListView<String> serverListView;

    private String pathToDir = "client/src/main/resources/client_dir";
    private DataInputStream inS;
    private DataOutputStream outS;

    public void initialize(URL location, ResourceBundle resources) {
        try{
            Socket socket = new Socket("localhost", 8189);
            inS = new DataInputStream(socket.getInputStream());
            outS = new DataOutputStream(socket.getOutputStream());
            refreshList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void upload(ActionEvent actionEvent) {
        String file = clientListView.getSelectionModel().getSelectedItem();
        System.out.println(file);
        try {
            outS.writeUTF(file);
            File current = new File(pathToDir + "/" + file);
            outS.writeLong(current.length());
            FileInputStream is = new FileInputStream(current);
            int tmp;
            byte [] buffer = new byte[8192];
            while ((tmp = is.read(buffer)) != -1) {
                outS.write(buffer, 0, tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        refreshList();
    }


    private void refreshList() {
        File file = new File(pathToDir);
        String[] files = file.list();
        clientListView.getItems().clear();
        serverListView.getItems().clear();

        File path = new File(pathToDir);
        File[] clientFiles = path.listFiles();
        Stream.of(clientFiles).map(File -> File.getName())
                .forEach(String -> clientListView.getItems().add(String));
        serverListView.getItems().addAll(getServerFiles());
    }

    private List<String> getServerFiles() {
        List<String> files = new ArrayList<>();
        try {
            outS.writeUTF("./getFilesList");
            outS.flush();
            int listSize = inS.readInt();
            for (int i = 0; i < listSize; i++) {
                files.add(inS.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public void refresh(ActionEvent actionEvent) {
        refreshList();
    }
}
