package guilda.loja.servico;

import guilda.loja.dominio.Produto;
import guilda.loja.dominio.Produto;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AverageAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.RangeAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.RangeBucket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoBuscaService {

    private final ElasticsearchOperations elasticsearchOperations;

    public ProdutoBuscaService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    private List<Produto> extractHits(SearchHits<Produto> searchHits) {
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    // --- PARTE A: Buscas Textuais ---

    public List<Produto> buscarPorNome(String termo) {
        String json = "{\"match\": {\"nome\": {\"query\": \"" + termo + "\"}}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    public List<Produto> buscarPorDescricao(String termo) {
        String json = "{\"match\": {\"descricao\": {\"query\": \"" + termo + "\"}}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    public List<Produto> buscarPorFrase(String termo) {
        String json = "{\"match_phrase\": {\"descricao\": {\"query\": \"" + termo + "\"}}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    public List<Produto> buscarFuzzy(String termo) {
        String json = "{\"match\": {\"nome\": {\"query\": \"" + termo + "\", \"fuzziness\": \"AUTO\"}}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    public List<Produto> buscarMultiCampos(String termo) {
        String json = "{\"multi_match\": {\"query\": \"" + termo + "\", \"fields\": [\"nome\", \"descricao\"]}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    // --- PARTE B: Filtros ---

    public List<Produto> buscarComFiltroCategoria(String termo, String categoria) {
        String json = "{\"bool\": {\"must\": [{\"match\": {\"descricao\": \"" + termo
                + "\"}}], \"filter\": [{\"term\": {\"categoria\": \"" + categoria + "\"}}]}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    public List<Produto> buscarFaixaPreco(Double min, Double max) {
        String json = "{\"range\": {\"preco\": {\"gte\": " + min + ", \"lte\": " + max + "}}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    public List<Produto> buscaAvancada(String categoria, String raridade, Double min, Double max) {
        String json = "{\"bool\": {\"filter\": [{\"term\": {\"categoria\": \"" + categoria
                + "\"}}, {\"term\": {\"raridade\": \"" + raridade + "\"}}, {\"range\": {\"preco\": {\"gte\": " + min
                + ", \"lte\": " + max + "}}}]}}";
        Query query = new StringQuery(json);
        return extractHits(elasticsearchOperations.search(query, Produto.class));
    }

    // --- PARTE C: Agregações (Apenas Estruturas Mock ou NativeQuery segura) ---
    // Since aggregations are strongly typed and version-dependent in Java Client,
    // we use a native query builder from Spring Data that delegates to the ES Java
    // client safely.

    public Map<String, Long> quantidadePorCategoria() {
        Aggregation agg = Aggregation.of(a -> a.terms(TermsAggregation.of(t -> t.field("categoria"))));
        Query query = NativeQuery.builder().withAggregation("agreg_cat", agg).build();

        SearchHits<Produto> hits = elasticsearchOperations.search(query, Produto.class);
        Map<String, Long> result = new HashMap<>();

        if (hits.getAggregations() != null) {
            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations eAgg = (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations) hits
                    .getAggregations();
            
            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation aggregation = 
                    (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation) eAgg.aggregationsAsMap().get("agreg_cat");
                    
            if (aggregation != null) {
                org.springframework.data.elasticsearch.client.elc.Aggregation elcAgg = aggregation.aggregation();
                if (elcAgg != null && elcAgg.getAggregate() != null) {
                    co.elastic.clients.elasticsearch._types.aggregations.Aggregate aggregate = elcAgg.getAggregate();
                    if (aggregate.isSterms()) {
                        for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                            result.put(bucket.key().stringValue(), bucket.docCount());
                        }
                    }
                }
            }
        }
        return result;
    }

    public Map<String, Long> quantidadePorRaridade() {
        Aggregation agg = Aggregation.of(a -> a.terms(TermsAggregation.of(t -> t.field("raridade"))));
        Query query = NativeQuery.builder().withAggregation("agreg_raridade", agg).build();

        SearchHits<Produto> hits = elasticsearchOperations.search(query, Produto.class);
        Map<String, Long> result = new HashMap<>();

        if (hits.getAggregations() != null) {
            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations eAgg = (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations) hits
                    .getAggregations();

            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation aggregation = 
                    (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation) eAgg.aggregationsAsMap().get("agreg_raridade");
                    
            if (aggregation != null) {
                org.springframework.data.elasticsearch.client.elc.Aggregation elcAgg = aggregation.aggregation();
                if (elcAgg != null && elcAgg.getAggregate() != null) {
                    co.elastic.clients.elasticsearch._types.aggregations.Aggregate aggregate = elcAgg.getAggregate();
                    if (aggregate.isSterms()) {
                        for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                            result.put(bucket.key().stringValue(), bucket.docCount());
                        }
                    }
                }
            }
        }
        return result;
    }

    public Double precoMedio() {
        Aggregation agg = Aggregation.of(a -> a.avg(AverageAggregation.of(avg -> avg.field("preco"))));
        Query query = NativeQuery.builder().withAggregation("agreg_avg", agg).build();

        SearchHits<Produto> hits = elasticsearchOperations.search(query, Produto.class);

        if (hits.getAggregations() != null) {
            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations eAgg = (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations) hits
                    .getAggregations();

            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation aggregation = 
                    (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation) eAgg.aggregationsAsMap().get("agreg_avg");
                    
            if (aggregation != null) {
                org.springframework.data.elasticsearch.client.elc.Aggregation elcAgg = aggregation.aggregation();
                if (elcAgg != null && elcAgg.getAggregate() != null) {
                    co.elastic.clients.elasticsearch._types.aggregations.Aggregate aggregate = elcAgg.getAggregate();
                    if (aggregate.isAvg()) {
                        return aggregate.avg().value();
                    }
                }
            }
        }
        return 0.0;
    }

    public Map<String, Long> faixasPreco() {
        Aggregation agg = Aggregation.of(a -> a.range(RangeAggregation.of(r -> r.field("preco")
                .ranges(
                        AggregationRange.of(ar -> ar.key("Abaixo de 100").to(100.0)),
                        AggregationRange.of(ar -> ar.key("De 100 a 300").from(100.0).to(300.0)),
                        AggregationRange.of(ar -> ar.key("De 300 a 700").from(300.0).to(700.0)),
                        AggregationRange.of(ar -> ar.key("Acima de 700").from(700.0))))));
        Query query = NativeQuery.builder().withAggregation("agreg_ranges", agg).build();

        SearchHits<Produto> hits = elasticsearchOperations.search(query, Produto.class);
        Map<String, Long> result = new HashMap<>();

        if (hits.getAggregations() != null) {
            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations eAgg = (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations) hits
                    .getAggregations();

            org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation aggregation = 
                    (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation) eAgg.aggregationsAsMap().get("agreg_ranges");
                    
            if (aggregation != null) {
                org.springframework.data.elasticsearch.client.elc.Aggregation elcAgg = aggregation.aggregation();
                if (elcAgg != null && elcAgg.getAggregate() != null) {
                    co.elastic.clients.elasticsearch._types.aggregations.Aggregate aggregate = elcAgg.getAggregate();
                    if (aggregate.isRange()) {
                        for (RangeBucket bucket : aggregate.range().buckets().array()) {
                            result.put(bucket.key(), bucket.docCount());
                        }
                    }
                }
            }
        }
        return result;
    }
}
