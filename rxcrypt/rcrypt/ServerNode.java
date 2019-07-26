package rcrypt;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

final public class ServerNode implements Serializable 
{
    //Instance Variables
        private String name;
        private int bandwidth;
        private String address;
        private int port;
        
        
    
    //Constructor
        public ServerNode(String n, String hostname, int port) throws UnknownHostException
        {
            setName(n);
            setAddress(hostname);
            setPort(port);

        }    
    
    //Methods
        String getName(){
            return name;
        }

        void setName(String n){
            name = n;
        }
        
        String getAddress() {
            return address;
        }
    
        void setAddress(String a) throws UnknownHostException{
            InetAddress ip = InetAddress.getByName(a);
            address = ip.getHostAddress();
        }
        
        int getPort(){
            return port;
        }

        void setPort(int p){
            port = p;
        }
        
        int getBandwidth(){
            return bandwidth;
        }

        void setBandwidth(int b){
            bandwidth = b;
        }
}