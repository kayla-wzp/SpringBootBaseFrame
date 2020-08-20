package frame.base.config.security;

import frame.base.service.account.UserService;
import frame.base.vo.account.AdminUserDetails;
import frame.base.vo.account.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.security.Permission;
import java.util.List;

/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑 Created by macro on 2019/11/5.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource
	private UserService userService;
	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
		// 去除 ROLE_ 前缀
		return new GrantedAuthorityDefaults("");
	}

	// 用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// 由于使用的是JWT，我们这里不需要csrf 禁用 CSRF
				.csrf().disable()
				// 授权异常
				.exceptionHandling()
				// 防止iframe 造成跨域
				.and().headers().frameOptions().disable()

				// 基于token，所以不创建会话
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests()
				// 允许对于网站静态资源的无授权访问
				.antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/webSocket/**")
				.permitAll()
				// 对登录注册要允许匿名访问
				.antMatchers("/admin/login", "/admin/register").permitAll()
				// swagger 文档
				.antMatchers("/swagger-ui.html").permitAll().antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/webjars/**").permitAll().antMatchers("/*/api-docs").permitAll()
				// 文件
				.antMatchers("/avatar/**").permitAll().antMatchers("/file/**").permitAll()
				// 阿里巴巴 druid
				.antMatchers("/druid/**").permitAll()
				// 放行OPTIONS请求,跨域请求会先进行一次options请求
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// 自定义匿名访问所有url放行 ： 允许匿名和带权限以及登录用户访问
				// 除上面外的所有请求都需要认证
				.anyRequest().authenticated();
		// 禁用缓存
		http.headers().cacheControl();
		// 添加JWT filter
		http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//添加自定义未授权和未登录结果返回
		http.exceptionHandling()
				.accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthenticationEntryPoint);
	}

	// 用于配置UserDetailsService及PasswordEncoder；
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	// SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder；
	@Bean
	public PasswordEncoder passwordEncoder() {
		// 密码加密方式
		return new BCryptPasswordEncoder();
	}

	// SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现；
	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		// 获取登录用户信息
		return username -> {
			User user = userService.getUserByLoginNo(username).get(0);
			if (user != null) {
				// 查询当前用户拥有的权限,返回包含权限的用户
				List<Permission> permissionList = null;
				return new AdminUserDetails(user,permissionList);
			}
			throw new UsernameNotFoundException("用户名或密码错误");
		};
	}

	// 在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。
	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
