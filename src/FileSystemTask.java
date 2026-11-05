import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileSystemTask {

    public static void main(String[] args) throws IOException {
        Path root = Paths.get("Shilov");
        Path nameFile = root.resolve("Nikita");
        Path dir1 = root.resolve("dir1");
        Path dir2 = dir1.resolve("dir2");
        Path dir3 = dir2.resolve("dir3");

        if (Files.exists(root)) {
            deleteRecursively(root);
        }

        Files.createDirectories(root);
        Files.createFile(nameFile);

        Files.createDirectories(dir3);
        Files.copy(nameFile, dir3.resolve(nameFile.getFileName()),
                StandardCopyOption.REPLACE_EXISTING);

        Files.createFile(dir1.resolve("file1"));
        Files.createFile(dir2.resolve("file2"));

        System.out.println("Структура до удаления:");
        try (Stream<Path> paths = Files.walk(root)) {
            paths.forEach(p -> System.out.println(
                    (Files.isDirectory(p) ? "D: " : "F: ") + p.getFileName()));
        }

        deleteRecursively(dir1);
        System.out.println("dir1 удалена, существует: " + Files.exists(dir1));
    }

    private static void deleteRecursively(Path start) throws IOException {
        try (Stream<Path> paths = Files.walk(start)) {
            paths.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    }
}