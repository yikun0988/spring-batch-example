package com.example.Spring_Batch_Example.config;

import com.example.Spring_Batch_Example.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.naming.directory.InvalidSearchFilterException;
import java.util.zip.DataFormatException;

@Configuration
public class SpringBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public ItemReader<User> itemReader;

    @Autowired
    public ItemProcessor<User, User> itemProcessor;

    @Autowired
    public ItemWriter<User> itemWriter;

    /*
    * 利用JobBuilderFactory和StepBuilderFactory來定義一個Spring Batch的job
    * ItemReader、ItemProcessor、ItemWriter則是step中的操作動作
     */
    @Bean
    public Job job() {
        /*Insert-File 為這個 job 的名稱
        *新增incrementer配置使每個job的運行id都唯一
         */
        return jobBuilderFactory.get("Insert-File")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1(){
        /* File-Process 為這個 step 的名稱
         * chunk的size決定於處理多少條數據寫入資料庫一次
         * faultTolerant用來對錯誤作設定，像是skip、retry
         * taskExecutor用來開啟多線程處理，throttleLimit是線程上限(這邊只是範例，因為此Demo沒有必要用到)
         */
        return stepBuilderFactory.get("File-Process")
                .<User, User>chunk(100)
                .faultTolerant().skip(InvalidSearchFilterException.class)
                .retry(DataFormatException.class).retryLimit(2)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .throttleLimit(4)
                .build();
    }

    //最簡單的multi-threaded TaskExecutor是SimpleAsyncTaskExecutor
    @Bean
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

}
