import java.util.Scanner;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    /* The server port to which
    the client socket is going to connect */
    public final static int SERVICE_PORT = 50001;

    public static void main(String[] args) throws IOException {
        try {
      /* Instantiate client socket.
      No need to bind to a specific port */
            DatagramSocket clientSocket = new DatagramSocket();

            // Get the IP address of the server
            InetAddress IPAddress = InetAddress.getByName("10.107.20.101");

            // Creating corresponding buffers
            byte[] sendingDataBuffer = new byte[1024];
            byte[] receivingDataBuffer = new byte[1024];

      /* Converting data to bytes and
      storing them in the sending buffer */
            Scanner scan = new Scanner(System.in);
            while (true) {
                String sentence = scan.nextLine() + "eof";
                sendingDataBuffer = sentence.getBytes();

                // Creating a UDP packet
                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, SERVICE_PORT);

                // sending UDP packet to the server
                clientSocket.send(sendingPacket);
                if (sentence.equals("exit")){
                    break;
                }

                // Get the server response .i.e. capitalized sentence
                DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                clientSocket.receive(receivingPacket);

                // Printing the received data
                String receivedData = new String(receivingPacket.getData());
                receivedData = receivedData.split("eof")[0];
                if (receivedData.equals("exit")){
                    break;
                }
                System.out.println(receivedData);


                // Closing the socket connection with the server
            }
            clientSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
