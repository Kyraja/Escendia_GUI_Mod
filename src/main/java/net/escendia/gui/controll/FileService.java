package net.escendia.gui.controll;

import net.escendia.ioc.Singleton;
import org.apache.commons.io.FilenameUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class FileService {

    private final ArrayList<String> mimeTypesAllowed;
    protected final ArrayList<String> urlLoaded;

    public FileService(){
        this.mimeTypesAllowed = new ArrayList<>();
        this.urlLoaded = new ArrayList<>();
    }

    public FileService(ArrayList<String> mimeTypesAllowed) {
        this.mimeTypesAllowed = mimeTypesAllowed;
        this.urlLoaded = new ArrayList<>();
    }

    private File getFileAsFile(String fileName) throws IOException {
        String fileDefault = new File(".").getAbsolutePath().substring(0,new File(".").getAbsolutePath().length()-1);
        new File(fileDefault+ "mods/Escendia_GUI_Mod/resources").mkdirs();
        File file = new File(fileDefault+ "mods/Escendia_GUI_Mod/resources/"+fileName);
        if(!file.exists())file.createNewFile();
        return file;
    }

    private void writeDataToFile(String fileName, String content) throws IOException {
        File file = getFileAsFile(fileName);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), true), "UTF-8"));
        writer.write(content);
        writer.newLine();
        writer.flush();
        writer.close();
    }

    public void writeStringToFile(String filename, String content){
        try {
            writeDataToFile(filename, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getFile(String url) throws Exception {
        String fileName = generateFileName(url);
        String fileDefault = new File(".").getAbsolutePath().substring(0,new File(".").getAbsolutePath().length()-1);
        new File(fileDefault+ "mods/Escendia_GUI_Mod/resources").mkdirs();
        File file = new File(fileDefault+ "mods/Escendia_GUI_Mod/resources/"+fileName);
        if(file.exists() && getFileLength(url) == file.length())
            return new FileInputStream(file);
        else{
            ByteArrayInputStream bais = downloadFileByUrl(url);

            FileOutputStream fos;


            file.createNewFile();

            fos = new FileOutputStream(file);
            int i;

            while ((i = bais.read()) != -1)
                fos.write(i);

            fos.close();

            bais.reset();
            return bais;
        }
    }

    private long getFileLength(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        long length = 0;
        URLConnection urlConnection = (HttpURLConnection) url.openConnection();;

        length = urlConnection.getContentLengthLong();
        ((HttpURLConnection) urlConnection).disconnect();
        return length;
    }

    public ByteArrayInputStream downloadFileByUrl(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        URLConnection urlConnection = url.openConnection();
        InputStream is = url.openStream();
        OutputStream os = new ByteArrayOutputStream(urlConnection.getContentLength());

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
        return new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
    }

    private String generateFileName(String url) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(url.getBytes());
        byte array[] = messageDigest.digest();
        String name = "";

        for(int i = 0; i < array.length; i++)
            name += array[i]+"";

        return name;
    }
}
