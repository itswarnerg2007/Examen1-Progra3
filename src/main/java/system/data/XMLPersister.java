package system.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class XMLPersister {
    private static XMLPersister instance;
    private final String path;

    public static XMLPersister instance() {
        if (instance == null) instance = new XMLPersister("data.xml");
        return instance;
    }

    private XMLPersister(String path) {
        this.path = path;
    }

    public Data load() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Data.class);
        try (InputStream in = new FileInputStream(path)) {
            return (Data) context.createUnmarshaller().unmarshal(in);
        }
    }

    public void store(Data data) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Data.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");


        File target = new File(path);
        File temp = new File(path + ".tmp");
        try (OutputStream out = new FileOutputStream(temp)) {
            marshaller.marshal(data, out);
        }

        try {
            Files.move(temp.toPath(), target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception ignored) {
            Files.move(temp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
