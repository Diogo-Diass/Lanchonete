package br.senai.sp.cpf138.Lanchonete.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.senai.sp.cpf138.Lanchonete.interceptor.AppInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer{
	
	/*
	 * @Autowired private AppInterceptor interceptor;
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**"); }
	 * 
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(interceptor); }
	 */
	
	//configura a conexão da aplicação ao Banco de dados Mysql
	
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost:3306/lanchonete");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			
			return dataSource;
		}
		//cofigura o Hibernate (ORM - Mapeamento objeto Relacional)
		
		@Bean
		public JpaVendorAdapter jpaVendorAdapter() {
			HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
			adapter.setDatabase(Database.MYSQL);
			adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
			//mostra no console os comandos que estão sendo feitos
			adapter.setShowSql(true);
			adapter.setPrepareConnection(true);
			//permite criar os comandos do banco de dados
			adapter.setGenerateDdl(true);
			
			return adapter; 
		}
		
		
}
