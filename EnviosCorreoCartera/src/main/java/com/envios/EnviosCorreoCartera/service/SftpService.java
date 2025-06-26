package com.envios.EnviosCorreoCartera.service;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class SftpService {
    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private int port;

    @Value("${sftp.user}")
    private String user;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.remote.directory.path}")
    private String remoteDirectory;

    public byte[] descargarArchivo(String nombreArchivo) throws JSchException, SftpException {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try{
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            String rutaCompleta = remoteDirectory + nombreArchivo.trim();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            channelSftp.get(rutaCompleta, outputStream);
            return outputStream.toByteArray();
        }finally{
            if(channelSftp != null){
                channelSftp.disconnect();
            }
            if(session != null){
                session.disconnect();
            }
        }
    }
}
