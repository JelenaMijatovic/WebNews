package raf.vp.jmijatovic11421rn;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import raf.vp.jmijatovic11421rn.repositories.category.CategoryRepository;
import raf.vp.jmijatovic11421rn.repositories.category.MySQLCategoryRepository;
import raf.vp.jmijatovic11421rn.repositories.news.MySQLArticleRepository;
import raf.vp.jmijatovic11421rn.repositories.news.ArticleRepository;
import raf.vp.jmijatovic11421rn.repositories.user.MySQLUserRepository;
import raf.vp.jmijatovic11421rn.repositories.user.UserRepository;
import raf.vp.jmijatovic11421rn.services.CategoryService;
import raf.vp.jmijatovic11421rn.services.ArticleService;
import raf.vp.jmijatovic11421rn.services.UserService;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySQLUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySQLArticleRepository.class).to(ArticleRepository.class).in(Singleton.class);
                this.bind(MySQLCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);

                this.bindAsContract(ArticleService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(CategoryService.class);
            }
        };
        register(binder);

        packages("raf.vp.jmijatovic11421rn");
    }
}
