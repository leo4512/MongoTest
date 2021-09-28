import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.File;
import java.io.IOException;

public class Insert implements Runnable {

    DB db;

    Insert(DB db) {

        this.db = db;

    }

    @Override
    public void run() {

        int i = 0;
        int insertRounds = 100; //Number of data batches
        int dataNumber; //Number of data in each batch
        String collection;
        XYSeries xySeries = new XYSeries("XY Chart"); //Instantiate line chart drawing

        while (i < insertRounds) {
            collection = String.valueOf(i); //Create new collection for each batch
            DBCollection coll = db.getCollection(collection);
            dataNumber = 1000 + 1000 * i;
            System.out.println("Data Volume: " + dataNumber);
            long startTime = System.nanoTime(); //Record the start time of insert operation of this data batch
            for (int j = 0; j < dataNumber; j++) {

                BasicDBObject doc = new BasicDBObject("name", 999) //Data document content
                        .append("type", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
                        .append("kind", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
                        .append("count", 9999)
                        .append("info", new BasicDBObject("x", 203).append("y", 102));
                coll.insert(doc);

            }
            long endTime   = System.nanoTime(); //Record the end time of insert operation of this data batch
            long totalTime = endTime - startTime;
            double averageTime = totalTime / dataNumber;
            System.out.println("Time Taken: " + totalTime);
            System.out.println("Collection: " + i);
            System.out.println("------------------------------------------");

            xySeries.add(dataNumber, averageTime);

            i++;
        }

        //Draw line chart
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(xySeries);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "MongoDB Insert Performance",
                "Data Volume",
                "Average Time (Nanosecond)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        //Chart output
        try {
            ChartUtils.saveChartAsPNG(new File("/Users/haiyang/Desktop/MongoTest/Results.PNG"), chart, 1920, 1080);
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }

}