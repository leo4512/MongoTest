import com.mongodb.*;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        //Database connection
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient("localhost", 27017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DB db = mongoClient.getDB("test");

        //Data insert thread
        Insert insert = new Insert(db);
        Thread thread = new Thread(insert);
        thread.setPriority(10);
        thread.start();
/*

        int i = 0;

        while (i < 10000) {
            BasicDBObject query = new BasicDBObject("count", i);
            DBCursor cursor = coll.find();
            cursor = coll.find(query);

            try {
                while(cursor.hasNext()) {
                    System.out.println(cursor.next());
                }
            } finally {
                cursor.close();
            }
            i++;

        }


        int i = 0;

        while (i < 10000) {
            BasicDBObject newDocument =
                    new BasicDBObject().append("$inc",
                            new BasicDBObject().append("count", i));

            coll.update(new BasicDBObject().append("name", 10), newDocument);
            i++;

        }
*/


    }
}


