# multi-datasource-springboot-start
	统一事务的多数据源动态切换脚手架
	
	自定义注解实现数据源的动态切换
	
	atomikos实现多数据源事务控制
	
	坑: Transactional限制的方法不能动态切换数据源,那就只能通过动态切换SqlSessionFactory来实现,就重写SqlSessionTemplate,动态切换SqlSessionFactory,事务统一到自定义SqlSessionTemplate
	
	TODO 代码中存在很多硬编码,下一步将所有的硬编码都移到application.yml中去
	TODO 所有的数据源的配置类都需要手动实现,下一步将这些需要手动实现的重复内容改为自动化
	
	基于 https://github.com/halink/spring-boot-multi-datasource-springboot-start 修改

