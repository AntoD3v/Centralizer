import com.darthvader.server.Server;
import com.darthvader.support.RedisConnection;
import redis.clients.jedis.Client;

public class ClientAdding extends RedisConnection {

    public static void main(){
        new ClientAdding();
    }

    public ClientAdding() {
        /*getJedis(0).publish("", new Server(

        ));*/
    }
}
