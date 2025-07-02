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

    public byte[] descargarArchivo(String nombreArchivo, String remotePath) throws JSchException, SftpException {
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

            String rutaCompleta = remotePath + nombreArchivo.trim();
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
