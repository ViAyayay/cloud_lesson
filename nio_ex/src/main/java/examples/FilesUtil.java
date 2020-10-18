package examples;

import java.io.IOException;
import java.nio.file.*;

public class FilesUtil {

    enum FileOption {
        DIRECTORY, FILE
    }

    static void createFile(Path path, FileOption option) throws IOException {
        System.out.println(path.toAbsolutePath());
        System.out.println(Files.exists(path));
        System.out.println(Files.isDirectory(path));
        if (!Files.exists(path)) {
            if (option == FileOption.DIRECTORY) {
                Files.createDirectory(path);
            } else if (option == FileOption.FILE) {
                Files.createFile(path);
            }
        }
    }

    static void copy(Path src, Path dst) throws IOException {
        Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
    }

    static void watchPath(Path directory) throws IOException {
        WatchService service = FileSystems
                .getDefault()
                .newWatchService();
        directory.register(service,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.OVERFLOW);
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Ждем событие");
                    WatchKey watchKey = service.take();
                    if (watchKey.isValid()) {
                        for (WatchEvent<?> event : watchKey.pollEvents()) {
                            System.out.println("Тип события: " + event.kind());
                            System.out.println("Что поменялось: " + event.context());
                        }
                        watchKey.reset();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
//        createFile(Paths.get("nio_ex12.txt"), FileOption.FILE);
//        copy(Paths.get("8.jpg"), Paths.get("8_copy.jpg"));
//        Files.write(Paths.get("test.txt"), "Hello world!".getBytes(), StandardOpenOption.APPEND);
//        BufferedReader br = Files.newBufferedReader(Paths.get("1.txt"));
        watchPath(Paths.get("nio_ex",
                "src", "main", "resources"));
        createFile(Paths.get("nio_ex",
                "src", "main", "resources", "1.txt"), FileOption.FILE);
    }
}
