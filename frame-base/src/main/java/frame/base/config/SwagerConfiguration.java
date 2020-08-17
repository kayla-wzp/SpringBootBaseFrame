package frame.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwagerConfiguration {
	@Bean
	public Docket haloDefaultApi() {
		return buildApiDocket("frame", "frame.base.controller");
	}

	private Docket buildApiDocket(@NonNull String groupName, @NonNull String basePackage) {
		Assert.hasText(groupName, "Group name must not be blank");
		Assert.hasText(basePackage, "Base package must not be blank");

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName(groupName).select()
				//Ϊ��ǰ����controller����API�ĵ�
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				//Ϊ��@Apiע���Controller����API�ĵ�
				//.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				//Ϊ��@ApiOperationע��ķ�������API�ĵ�
				//.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build()
				.useDefaultResponseMessages(false).pathMapping("/");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("springboot������ܴ").description("Documentation for frame").build();
	}
}
