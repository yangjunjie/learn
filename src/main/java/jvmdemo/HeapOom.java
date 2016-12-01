package jvmdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yance on 2016/10/17.
 */
public class HeapOom {
    /**
     * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     */
        static class OOMObject {}

        public static void main(String[] args) {
            List<OOMObject> list = new ArrayList<OOMObject>();
            while (true) {
                list.add(new OOMObject());
            }
        }

}
