package TestPipe;

import org.apache.crunch.FilterFn;

public class StopFilterFn extends FilterFn<String> {


    public boolean accept(final String s) {
        return true;
    }
}
