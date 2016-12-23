package test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
public class UploadToServerDemo {

    public static void main(String[] args) {
        listFileNames("112.74.197.62", 22, "root", "leoman.cc2015", "/usr/local/img");
    }

    private static List<String> listFileNames(String host, int port, String username, final String password, String dir) {
        List<String> list = new ArrayList<String>();
        ChannelSftp sftp = null;
        Channel channel = null;
        Session sshSession = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected!");
            channel = sshSession.openChannel("sftp");
            channel.connect();
            System.out.println("Channel connected!");
            sftp = (ChannelSftp) channel;

            FileInputStream fis = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\pic\\1.jpg"));
            sftp.put(fis, dir + "/2.jpg");
//            Vector<?> vector = sftp.ls(dir);
//            for (Object item : vector) {
//                LsEntry entry = (LsEntry) item;
//                System.out.println(entry.getFilename());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeChannel(sftp);
            closeChannel(channel);
            closeSession(sshSession);
        }
        return list;
    }

    private static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    private static void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
