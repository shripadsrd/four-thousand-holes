package TestPipe;

import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool{

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(), new WordCount(), args);
    }

    public int run(String[] args) throws Exception {

        if (args.length != 3) {
            System.err.println("Usage: hadoop jar DatasetAnalysis-1.0-SNAPSHOT.jar"
                    + " input output");
            System.out.println(args.length + "ARGS LENG");
            System.out.println(args[0]);
            System.out.println(args[1]);
            System.out.println(args[2]);
            return 1;
        }

        String inputPath = args[1];
        String outputPath = args[2];
        System.out.println("INPUTPATH" + inputPath);
        System.out.println("OUTPUTPATH" + outputPath);

        Pipeline pipeline = new MRPipeline(WordCount.class, getConf());

        PCollection<String> lines = pipeline.readTextFile(inputPath);

        PCollection<String> words = lines.parallelDo(new Tokenizer(), Writables.strings());

        PTable<String, Long> counts = words.count();

        pipeline.writeTextFile(counts, outputPath);

        PipelineResult result = pipeline.done();

        return result.succeeded()?0:1;
    }

}
