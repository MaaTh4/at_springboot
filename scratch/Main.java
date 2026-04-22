import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
public class Main {
    public static void main(String[] args) {
        for (java.lang.reflect.Method m : ElasticsearchAggregation.class.getDeclaredMethods()) {
            System.out.println(m.getReturnType().getName() + " " + m.getName() + "()");
        }
        for (java.lang.reflect.Method m : org.springframework.data.elasticsearch.client.elc.Aggregation.class.getDeclaredMethods()) {
            System.out.println(m.getReturnType().getName() + " " + m.getName() + "()");
        }
    }
}
