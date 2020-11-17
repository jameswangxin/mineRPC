package com.chipizz.rpc.provider.app;

import com.chipizz.rpc.provider.service.Calculator;
import com.chipizz.rpc.provider.service.CalculatorImpl;
import com.chipizz.rpc.request.CalculateRpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ProviderApp {
    private static Logger log = LoggerFactory.getLogger(ProviderApp.class);
    private Calculator calculator = new CalculatorImpl();

    public static void main(String[] args) throws IOException {
        new ProviderApp().run();
    }

    private void run() throws IOException{
        ServerSocket listner = new ServerSocket(8080);
        try {
            while (true) {
                Socket socket = listner.accept();
                try {
                    //将请求反序列化
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();
                    log.info("request is {}", object);

                    //调用服务
                    int result = 0;
                    if (object instanceof CalculateRpcRequest) {
                        CalculateRpcRequest calculateRpcRequest = (CalculateRpcRequest)object;
                        if ("add".equals(calculateRpcRequest.getMethod())) {
                            result = calculator.add(calculateRpcRequest.getA(), calculateRpcRequest.getB());
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(result);
                } catch (Exception e) {
                   log.error("fail");
                } finally {
                    socket.close();
                }
            }
        } finally{
            listner.close();
        }
    }
}