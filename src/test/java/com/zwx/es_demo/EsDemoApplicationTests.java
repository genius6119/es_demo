package com.zwx.es_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwx.es_demo.dao.BookRepository;
import com.zwx.es_demo.dao.MegacorpRepository;
import com.zwx.es_demo.model.Book;
import com.zwx.es_demo.model.IndexMessage;
import com.zwx.es_demo.model.Megacorp;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDemoApplicationTests {

    @Autowired(required=false)
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private MegacorpRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(topics = "demo")
    private void handleMessage(String content) throws IOException {
        objectMapper.readValue(content, IndexMessage.class);
    }


    /**
     * 新建索引
     */
    @Test
    public void createIndex() {
        esTemplate.createIndex(Book.class);
    }

    @Test
    public void deleteIndex(){
        esTemplate.deleteIndex(Book.class);
    }

    /**
     * 新增 & 修改 （按ID决定）
     */
    @Test
    public void save() {
//        Megacorp megacorp = new Megacorp(1L,"1","1","1",10L,"1");
//        repository.save(megacorp);
        Book book =new Book(1L,"java实战开发经典");
        bookRepository.save(book);
    }

    /**
     * 批量新增
     */
    @Test
    public void saveall() {
        List<Megacorp> megacorps = new ArrayList<>();
        megacorps.add(new Megacorp(2L,"2","1","1",1L,"1"));
        megacorps.add(new Megacorp(3L,"3","1","在实现一个字段模糊查询的时候",1L,"1"));
        megacorps.add(new Megacorp(4L,"2","1","在实现两个字段模糊查询的时候",1L,"1"));
        megacorps.add(new Megacorp(5L,"3","1","在实现三个字段模糊查询的时候",1L,"1"));
        megacorps.add(new Megacorp(6L,"2","1","在实现四个字段模糊查询的时候",1L,"1"));
        megacorps.add(new Megacorp(7L,"3","1","在实现一五个字段模糊查询的时候",1L,"1"));
        repository.saveAll(megacorps);
    }

    /**
     * 基本查询
     * 注意这里查询时候对应的实体类中要有一个无参的构造函数
     */
    @Test
    public void query() {
        Iterable<Megacorp> megacorps = repository.findAll();
        for (Megacorp c:megacorps){
            System.out.println(c);
        }
    }

    /**
     * Jpa自定义函数查询
     * 自定义方法具体命名规则见: http://www.zwxzzz.top/2019/07/23/SpringDataJPA%E8%87%AA%E5%AE%9A%E4%B9%89%E6%96%B9%E6%B3%95%E7%BA%A6%E5%AE%9A/
     */
    @Test
    public void queryBerween() {
        Iterable<Megacorp> megacorps = repository.findByIdBetween(2L,4L);
        for (Megacorp c:megacorps){
            System.out.println(c);
        }
    }

    @Test
    public void matchQuery(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("last_name", "2"));
//        termQuery:功能更强大，除了匹配字符串以外，还可以匹配
//        int/long/double/float/....
//        queryBuilder.withQuery(QueryBuilders.termQuery("last_name", "2"));
        // 搜索，获取结果
        Page<Megacorp> items = repository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("total = " + total);
        for (Megacorp item : items) {
            System.out.println(item);
        }

    }

    /**
     * 布尔查询
     */
    @Test
    public void booleanQuery(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("age",10))
        );

        Page<Megacorp> megacorps = repository.search(queryBuilder.build());
        for (Megacorp m:megacorps){
            System.out.println(m);
        }
    }

    /**
     * 模糊查询  要注意 分词分完的结果
     */
    @Test
    public void fuzzyQuery(){
//        方法1
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.fuzzyQuery("name","java"));
        Page<Book> page = bookRepository.search(builder.build());
//        方法2
//        Iterable<Megacorp> page = repository.findByInterestsLike("模糊");
        for (Book m :page){
            System.out.println(m);
        }
    }

    /**
     * @Description: 分页查询
     * @Author:
     */
    @Test
    public void searchByPage(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.fuzzyQuery("interests", "一个"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page,size));

        // 搜索，获取结果
        Page<Megacorp> items = repository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + items.getTotalPages());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());

        for (Megacorp item : items) {
            System.out.println(item);
        }
    }

    /**
     * @Description:排序查询
     */
    @Test
    public void searchAndSort(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery("interests", "一"));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));

        // 搜索，获取结果
        Page<Megacorp> items = repository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);

        for (Megacorp item : items) {
            System.out.println(item);
        }
    }

    /**
     * @Description: 按照 字段 进行分组
     */
    @Test
    public void testAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为Megacorp，聚合名称为last_names，聚合字段为last_name
        queryBuilder.addAggregation(
                AggregationBuilders.terms("last_names").field("last_name"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Megacorp> aggPage = (AggregatedPage<Megacorp>) repository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的Megacorp聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("last_names");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即last_name
            System.out.println("Key:"+bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println("数量："+bucket.getDocCount());
        }

    }



}
