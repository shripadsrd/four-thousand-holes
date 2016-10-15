package TestPipe;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;

import com.google.common.base.Splitter;

public class Tokenizer extends DoFn<String, String > {

    private static final Splitter SPLITTER = Splitter.onPattern("\\s+").omitEmptyStrings();

    @Override
    public void process(final String line, final Emitter<String> emitter) {

        for(String word: SPLITTER.split(line)) {
            emitter.emit(word);
        }
    }
}
