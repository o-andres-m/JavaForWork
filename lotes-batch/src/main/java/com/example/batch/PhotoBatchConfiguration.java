package com.example.batch;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.model.PhotoDTO;


@Configuration
public class PhotoBatchConfiguration {
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	public PersonaItemProcessor personaItemProcessor;

	@Autowired 
	private PhotoRestItemReader photoRestItemReader;
	@Autowired 
	private ItemFailureLoggerListener erroresListener;
	
	@Bean
	public Job photoJob() {
		String[] headers = new String[] { "id", "author", "width", "height", "url", "download_url" };
		return new JobBuilder("photoJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(new StepBuilder("photoJobStep1", jobRepository)
				.listener(erroresListener)
				.<PhotoDTO, PhotoDTO>chunk(100, transactionManager)
				.reader(photoRestItemReader)
				.writer(new FlatFileItemWriterBuilder<PhotoDTO>().name("photoCSVItemWriter")
					.resource(new FileSystemResource("output/photoData.csv"))
					.headerCallback(new FlatFileHeaderCallback() {
						public void writeHeader(Writer writer) throws IOException {
						writer.write(String.join(",", headers));
						}})
					.lineAggregator(new DelimitedLineAggregator<PhotoDTO>() { {
						setDelimiter(",");
						setFieldExtractor(new BeanWrapperFieldExtractor<PhotoDTO>() { {
							setNames(headers);
						}});
					}}).build())
				.build())
			.build();
	}

}
