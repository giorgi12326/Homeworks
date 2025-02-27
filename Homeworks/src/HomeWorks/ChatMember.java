//package chat;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.Random;
//import java.util.Scanner;
//
//import lesson20240820.WorkerThread;
//
//public class ChatMember {
//
//    static Random r = new Random();
//
//    private String name;
//    private ChatServer server;
//    private PrintWriter printer;
//
//    private WorkerThread sendingWorker;
//
//    public ChatMember(ChatServer server) {
//        this.server = server;
//    }
//
//    void handleConnection(Socket socket) {
//        initializeOutputProcessing(socket);
//        processInput(socket);
//    }
//
//    private void initializeOutputProcessing(Socket socket) {
//        try {
//            printer = new PrintWriter(socket.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sendingWorker = new WorkerThread();
//    }
//
//    private void processInput(Socket socket) {
//        System.out.println("got a connection! thread: " + Thread.currentThread());
//        try (Scanner scanner = new Scanner(socket.getInputStream())) {
//            while (scanner.hasNextLine()) {
//                String command = scanner.nextLine();
//                ChatServer.service.submit(() -> {
//                    process(command);
//                });
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    void process(String command) {
//        String[] tokens = command.split(":");
//        String keyword = tokens[0];
//        switch (keyword) {
//            case "name": {
//                this.name = tokens[1];
//                server.publish(name + " just joined the chat");
//            }
//            break;
//            case "msg": {
//                if (name == null) {
//                    send("set name fist:  name:<you-name>");
//                    return;
//                }
//                server.publish(name + ": " + tokens[1]);
//            }
//            break;
//            case "exit": {
//                // TODO: homework
//                System.exit(1); <----
//            }
//            break;
//            default: {
//                System.err.println("unknown command " + command);
//            }
//        }
//    }
//
//    public void send(String message) {
//        sendingWorker.execute(() -> {
//            actualSend(message);
//        });
//    }
//
//    private void actualSend(String message) {
//        try {
//            Thread.sleep(r.nextInt(5000));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        printer.println(message);
//        printer.flush();
//    }
//
//}