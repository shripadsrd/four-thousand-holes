import org.apache.crunch.PCollection;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.shripad.fourThousandHoles.model.YoutubeData;

/**
 *
 */
public class TestPipe extends Configured implements Tool{

    public static void main(String[] args) throws Exception {
        System.out.println("Hey Jude, dont let me down");
        ToolRunner.run(new Configuration(), new TestPipe(), args);
    }

    public int run(final String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: hadoop jar <jar>"
                    + " [generic options] input output");
            System.err.println();
            GenericOptionsParser.printGenericCommandUsage(System.err);
            return 1;
        }

        String inputPath = args[0];
        String outputPath = args[1];

        // Create an object to coordinate pipeline creation and execution.
        Pipeline pipeline = new MRPipeline(TestPipe.class, getConf());

        // Reference a given text file as a collection of Strings.
        PCollection<String> lines = pipeline.readTextFile(inputPath);

        Iterable<String> allLines = lines.materialize();

        for(String line : allLines) {
            System.out.println(line);
            YoutubeData.Builder builder = YoutubeData.newBuilder();
            //you have a model builder and a line from csv file,
            //figure out if you want to add to hdfs or hbase from here
            //if hbase, then schema and stuff
        }

        YoutubeData.Builder builder = YoutubeData.newBuilder();
//        pipeline.writeTextFile(lines, outputPath);

        // Execute the pipeline as a MapReduce.
        PipelineResult result = pipeline.done();

        return result.succeeded() ? 0 : 1;
    }
}
