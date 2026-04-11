package guilda;

import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import java.lang.reflect.Method;

public class DebugAgg {
    public static void main(String[] args) {
        System.out.println("Methods of ElasticsearchAggregation:");
        for (Method m : ElasticsearchAggregation.class.getDeclaredMethods()) {
            System.out.println(m.getName() + " -> " + m.getReturnType().getName());
        }
    }
}
